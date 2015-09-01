package sepim.server;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.a.push.Push;

import sepim.server.clients.Ring;
import sepim.server.clients.World;
import sepim.server.net.ServerPipelineFactory;

public class Server {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	public Server()
	{
		NioServerSocketChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ServerPipelineFactory());
		bootstrap.bind(new InetSocketAddress("182.92.67.109", 8082));
		logger.info("Ready and Listening");
		initRingPhoneListDataBase();
		Timer timer = new Timer();
	    timer.schedule(new TimerTask()
	    {
	    	@Override
	        public void run()
	    	{
	    		Date now = new Date();
	    		
	    		SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
	    		int nowTime = Integer.parseInt(dateFormat.format(now));
	    		
	    		HashMap<String, Date> Map = World.getWorld().getRingLkTimeMap();
	    		List<Ring> RingList = null;
        		Ring ring = null;
	    		@SuppressWarnings("rawtypes")
				Iterator iter = Map.entrySet().iterator();//�Ȼ�ȡ���map��set���У��ٻ���������еĵ�����
	            while(iter.hasNext())
	            {
	                @SuppressWarnings("rawtypes")
					Map.Entry entry = (Map.Entry)iter.next();//�õ�������е�ӳ�������set�е����ͣ�HashMap����Map.Entry���ͣ������map�ӿ�������
	                String key = (String)entry.getKey(); //���key,ringID
	                Date value = (Date)entry.getValue();//���value����Ҫǿ��ת��һ��
	                long  between = Math.abs(value.getTime() - now.getTime());
	                if(between > (24* 5*60*1000))//�ֻ�����
	                {
	                	JSONObject jsonObject = new JSONObject();
	    				jsonObject.put("type","off");  
	    				jsonObject.put("shouhuan_id",key);
	    			 	System.out.println(key+"����");//��ʾ ����
	    				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(key),jsonObject.toString());
	                	World.getWorld().getRingLkTimeMap().remove(key);//��ʾ ����
	                }
	                else 
	                {
	                	RingList=World.getWorld().getRingLocationMap().get(key);//��ø������ֱ�ĸ��Ի�����
	                	if(RingList!=null)
	                	{
		                	for(int i=0;i<RingList.size();i++)//�������ֱ���Ի�����
		                	{
		                		ring = RingList.get(i);
		                		if(ring.isSend())//����ѷ���
		                		{
		                			
		                		}
		                		else//���δ����
		                		{
									if(nowTime>=ring.getBeginTime()&&nowTime<=ring.getEndTime())//��ǰʱ�������õ��Զ��嶨λʱ����
									{
										String commandString ="";
										String length ="";
										
										commandString="WORK,"+ring.getBeginTime()+"-"+ring.getEndTime();
										length = String.format("%04x", commandString.length());;
										World.getWorld().WriteMessageToRing(key,"[LJ*"+key+"*"+length+"*"+commandString+"]");//����ʱ�������ָ��,����ʱ���0-24
										
										commandString = "WORKTIME,"+((ring.getEndTime()-ring.getBeginTime())*60);
										length = String.format("%04x", commandString.length());;
										
										World.getWorld().WriteMessageToRing(key,"[LJ*"+key+"*"+length+"*"+commandString+"]");//����ʱ������ָ��,��������
										
										commandString = "UPLOAD,"+ring.getRate();
										length = String.format("%04x", commandString.length());;
										World.getWorld().WriteMessageToRing(key,"[LJ*"+key+"*"+length+"*"+commandString+"]");//��λƵ������
										
										
										World.getWorld().WriteMessageToRing(key,"[LJ*"+key+"*0002*CR]");//��ʼ����
										
										RingList.get(i).setSend(true);
									}
								}
		                	}
						}
	                }
	            }
	        }
	    }, 0, 5*60*1000);
	}
	public static void main(String args[]) {
		new Server();
	}
	
	public void initRingPhoneListDataBase(){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		//�����ݿ��ȡ���ֻ�ID�б�
		ArrayList<String> ringIdList = new ArrayList<String>();
		try 
		{
			SQLQuery sqlQueryRingId = session.createSQLQuery(
					"select distinct shouhuan_id from dbo.[relation]");
			List ringIdListInit = sqlQueryRingId.list();//�ֻ�ID�б�
			for(Object initRingId : ringIdListInit){
					ringIdList.add(initRingId.toString());
			}
			//�����ݿ�ͨ���ֻ�ID��ȡ��֮�󶨵�UserId
			for(String shouhuan_id:ringIdList)
			{
				SQLQuery sqlQueryUserId = session.createSQLQuery(
					"select user_id from dbo.[relation] where shouhuan_id=:shouhuan_id ");
				sqlQueryUserId.setString("shouhuan_id", shouhuan_id);
				@SuppressWarnings("unchecked")
				ArrayList<String> UserIdList = (ArrayList<String>) sqlQueryUserId.list();
				World.getWorld().getRingPhoneListMap().put(shouhuan_id,UserIdList);//�����ֻ���UserId��ӳ��
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		
		
	}
	
}

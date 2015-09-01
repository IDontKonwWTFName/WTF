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
				Iterator iter = Map.entrySet().iterator();//先获取这个map的set序列，再或者这个序列的迭代器
	            while(iter.hasNext())
	            {
	                @SuppressWarnings("rawtypes")
					Map.Entry entry = (Map.Entry)iter.next();//得到这个序列的映射项，就是set中的类型，HashMap都是Map.Entry类型（详情见map接口声明）
	                String key = (String)entry.getKey(); //获得key,ringID
	                Date value = (Date)entry.getValue();//获得value，都要强制转换一下
	                long  between = Math.abs(value.getTime() - now.getTime());
	                if(between > (24* 5*60*1000))//手环下线
	                {
	                	JSONObject jsonObject = new JSONObject();
	    				jsonObject.put("type","off");  
	    				jsonObject.put("shouhuan_id",key);
	    			 	System.out.println(key+"下线");//显示 下线
	    				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(key),jsonObject.toString());
	                	World.getWorld().getRingLkTimeMap().remove(key);//显示 下线
	                }
	                else 
	                {
	                	RingList=World.getWorld().getRingLocationMap().get(key);//获得该在线手表的个性化设置
	                	if(RingList!=null)
	                	{
		                	for(int i=0;i<RingList.size();i++)//遍历该手表个性化设置
		                	{
		                		ring = RingList.get(i);
		                		if(ring.isSend())//如果已发送
		                		{
		                			
		                		}
		                		else//如果未发送
		                		{
									if(nowTime>=ring.getBeginTime()&&nowTime<=ring.getEndTime())//当前时间在设置的自定义定位时间内
									{
										String commandString ="";
										String length ="";
										
										commandString="WORK,"+ring.getBeginTime()+"-"+ring.getEndTime();
										length = String.format("%04x", commandString.length());;
										World.getWorld().WriteMessageToRing(key,"[LJ*"+key+"*"+length+"*"+commandString+"]");//工作时间段设置指令,工作时间段0-24
										
										commandString = "WORKTIME,"+((ring.getEndTime()-ring.getBeginTime())*60);
										length = String.format("%04x", commandString.length());;
										
										World.getWorld().WriteMessageToRing(key,"[LJ*"+key+"*"+length+"*"+commandString+"]");//工作时间设置指令,工作分钟
										
										commandString = "UPLOAD,"+ring.getRate();
										length = String.format("%04x", commandString.length());;
										World.getWorld().WriteMessageToRing(key,"[LJ*"+key+"*"+length+"*"+commandString+"]");//定位频率设置
										
										
										World.getWorld().WriteMessageToRing(key,"[LJ*"+key+"*0002*CR]");//开始工作
										
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
		//从数据库读取出手环ID列表
		ArrayList<String> ringIdList = new ArrayList<String>();
		try 
		{
			SQLQuery sqlQueryRingId = session.createSQLQuery(
					"select distinct shouhuan_id from dbo.[relation]");
			List ringIdListInit = sqlQueryRingId.list();//手环ID列表
			for(Object initRingId : ringIdListInit){
					ringIdList.add(initRingId.toString());
			}
			//从数据库通过手环ID读取与之绑定的UserId
			for(String shouhuan_id:ringIdList)
			{
				SQLQuery sqlQueryUserId = session.createSQLQuery(
					"select user_id from dbo.[relation] where shouhuan_id=:shouhuan_id ");
				sqlQueryUserId.setString("shouhuan_id", shouhuan_id);
				@SuppressWarnings("unchecked")
				ArrayList<String> UserIdList = (ArrayList<String>) sqlQueryUserId.list();
				World.getWorld().getRingPhoneListMap().put(shouhuan_id,UserIdList);//建立手环和UserId的映射
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		
		
	}
	
}

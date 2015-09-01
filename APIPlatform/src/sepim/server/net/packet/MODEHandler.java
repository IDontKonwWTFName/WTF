package sepim.server.net.packet;

import java.util.ArrayList;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import sepim.server.clients.Ring;
import sepim.server.clients.World;
import sepim.server.clients.WriteSql;

public class MODEHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId)
	{
		if(!userId.equals(""))//�ֻ�����
		{
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	��λģʽ����");
			if(contents.split(";").length==1)//���Զ���ģʽ��λ
			{				
				World.getWorld().getRingLocationMap().remove(ringId);//��ϣ����ɾ�����ֻ����Զ��嶨λ	
				
				String mode = contents.split(",")[1];
				String rate = "";//��λƵ��
				String commandString  ="";
				if(mode.equals("0"))
				{
					rate = "30";
					commandString ="*0009*UPLOAD,30]";
				}
				else if(mode.equals("1"))
				{
					rate = "600";	
					commandString ="*000A*UPLOAD,600]";
				}
				else if(mode.equals("2"))
				{
					rate = "1800";			
					commandString ="*000B*UPLOAD,1800]";
				}
				
				@SuppressWarnings("deprecation")
				
				SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
				Session session =sessionFactory.openSession();
				Transaction trans=session.beginTransaction();
				
				SQLQuery sqlQueryChannelId = session.createSQLQuery("update dbo.[shouhuan] set mode=:mode where shouhuan_id=:shouhuan_id");

				sqlQueryChannelId.setString("mode",mode);
		    	sqlQueryChannelId.setString("shouhuan_id",ringId);
				
		    	sqlQueryChannelId.executeUpdate();
		    	
		    	trans.commit();
				session.close();
				sessionFactory.close();

				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*0009*WORK,0-24]");//����ʱ�������ָ��,����ʱ���0-24
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*000D*WORKTIME,1440]");//����ʱ������ָ��,����24Сʱ
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+commandString);//��λƵ������
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*0002*CR]");//��ʼ����
			}
			else//�Զ���ģʽ��λ
			{
				String[] commandString = contents.split(";");
				String[] workTimes = commandString[1].split(",");//����ʱ���
				String[] workRates = commandString[2].split(",");//�ù���ʱ��εĶ�λƵ��
				ArrayList<Ring> ringList = new ArrayList<Ring>();
				int length = workTimes.length;
				for(int i=0;i<length;i++)
				{
					Ring ring = new Ring();
					ring.setBeginTime(Integer.parseInt(workTimes[i].split("-")[0]));
					ring.setEndTime(Integer.parseInt(workTimes[i].split("-")[1]));
					ring.setRate(workRates[i]);
					ring.setSend(false);
					ringList.add(ring);
				}
				
				World.getWorld().getRingLocationMap().put(userId, ringList);//д���ϣ��
				
				
				
				@SuppressWarnings("deprecation")
				
				SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
				Session session =sessionFactory.openSession();
				Transaction trans=session.beginTransaction();
				
				SQLQuery sqlQueryChannelId = session.createSQLQuery("update dbo.[shouhuan] set mode=:mode , rate:rate , time=:time where shouhuan_id=:shouhuan_id");

				sqlQueryChannelId.setString("mode","3");
				sqlQueryChannelId.setString("time",commandString[1]);
				sqlQueryChannelId.setString("rate",commandString[2]);
		    	sqlQueryChannelId.setString("shouhuan_id",ringId);
				
		    	sqlQueryChannelId.executeUpdate();
		    	
		    	trans.commit();
				session.close();
				sessionFactory.close();
			}
		}
		else
		{
		
		}
	}
}
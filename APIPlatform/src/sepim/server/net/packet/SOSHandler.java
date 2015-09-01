package sepim.server.net.packet;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;
import sepim.server.clients.WriteSql;

public class SOSHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId)
	{
		if(contents.contains("SOS1"))
		{
			if(contents.contains("SOS1,"))
			{
				String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
				WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	sos1����");
				System.out.println(ringId+"sos1���ã���");
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}
			else
			{
				System.out.println(ringId+"sos1���óɹ�����");
				String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
				WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	sos1���óɹ�");
				//�����ݷ�װ��json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("type","SOS1");  
				jsonObject.put("shouhuan_id",ringId); 
				//���������͸��ֻ�
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
		if(contents.contains("SOS2"))
		{
			if(contents.contains("SOS2,"))
			{
				String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
				WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	sos2����");
				System.out.println(ringId+"sos2���ã���");
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}
			else
			{
				String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
				WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	sos2���óɹ�");
				System.out.println(ringId+"sos2���óɹ�����");
				//�����ݷ�װ��json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("type","SOS2");  
				jsonObject.put("shouhuan_id",ringId); 
				//���������͸��ֻ�
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
		if(contents.contains("SOS3"))
		{
			if(contents.contains("SOS3,"))
			{
				String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
				WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	sos3����");
				System.out.println(ringId+"sos3���ã���");
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}
			else
			{
				String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
				WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	sos3���óɹ�");
				System.out.println(ringId+"sos3���óɹ�����");
				//�����ݷ�װ��json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("type","SOS3");  
				jsonObject.put("shouhuan_id",ringId); 
				//���������͸��ֻ�
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
		if(contents.contains("SOS")&&contentsLength.equals("0003"))
		{
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	sos���óɹ�");
			System.out.println(ringId+"sosͬʱ���óɹ�����");
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type","SOS");  
			jsonObject.put("shouhuan_id",ringId); 
			//���������͸��ֻ�
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			
			
			@SuppressWarnings("deprecation")
			
			SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
			Session session =sessionFactory.openSession();
			Transaction trans=session.beginTransaction();
			
			SQLQuery sqlQueryChannelId = session.createSQLQuery("update dbo.[shouhuan] set sos=:sos where shouhuan_id=:shouhuan_id");

			sqlQueryChannelId.setString("sos",World.getWorld().getPhoneCommandMap().get(ringId+leixing).split(",",2)[1]);
	    	sqlQueryChannelId.setString("shouhuan_id",ringId);
			
	    	sqlQueryChannelId.executeUpdate();
	    	
	    	trans.commit();
			session.close();
			sessionFactory.close();
			
			World.getWorld().getPhoneCommandMap().remove(ringId+leixing);//ɾ���˼�¼
			
			
		}
		if(contents.contains("SOS,"))
		{
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	sos����");
			System.out.println(ringId+"3��SOS����ͬʱ���ã���");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

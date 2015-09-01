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

public class FLOWERHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents,String userId) {
		if(!userId.equals(""))//�ֻ�����
		{
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	����С�컨");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
		else
		{
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	С�컨���óɹ�");
			@SuppressWarnings("deprecation")
			
			
			SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
			Session session =sessionFactory.openSession();
			Transaction trans=session.beginTransaction();
			
			SQLQuery sqlQueryChannelId = session.createSQLQuery("update dbo.[shouhuan] set flower=:flower where shouhuan_id=:shouhuan_id");

			sqlQueryChannelId.setString("flower",World.getWorld().getPhoneCommandMap().get(ringId+leixing).split(",")[1]);
	    	sqlQueryChannelId.setString("shouhuan_id",ringId);
			
	    	sqlQueryChannelId.executeUpdate();
	    	
	    	trans.commit();
			session.close();
			sessionFactory.close();
			
			World.getWorld().getPhoneCommandMap().remove(ringId+leixing);//ɾ���˼�¼
			
			
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//���������͸��ֻ�
		    new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
	}

}

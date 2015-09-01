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

public class CenterHandler {
	//�������ĺ���
	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) {
		if(!userId.equals(""))//�ֻ�����
		{
			System.out.println(ringId+"���ĺ������ã���");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	���ĺ�������");
			World.getWorld().WriteMessageToRing(ringId, command);
		}
		else
		{
			System.out.println(ringId+"���ĺ������óɹ�����");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	���ĺ������óɹ�");
			@SuppressWarnings("deprecation")
			
			SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
			Session session =sessionFactory.openSession();
			Transaction trans=session.beginTransaction();
			
			SQLQuery sqlQueryChannelId = session.createSQLQuery("update dbo.[shouhuan] set centernumber=:centernumber where shouhuan_id=:shouhuan_id");

			sqlQueryChannelId.setString("centernumber",World.getWorld().getPhoneCommandMap().get(ringId+leixing).split(",")[1]);
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

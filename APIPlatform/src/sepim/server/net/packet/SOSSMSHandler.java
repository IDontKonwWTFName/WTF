package sepim.server.net.packet;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class SOSSMSHandler {
	//��δʹ��
	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents,String userId) {
		System.out.println("----");
		if(!userId.equals(""))//�ֻ�����
		{
			System.out.println(ringId+"SOS���ű����������ã���");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"SOS���ű����������óɹ�����");
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//���������͸��ֻ�
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			
			/*
			 * by luojun  8.28
			 */
			SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
			Session session =sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			
			SQLQuery sqlQuery= session.createSQLQuery("update dbo.[shouhuan] set sossms=:sossms where shouhuan_id=:shouhuan_id");
			sqlQuery.setString("sossms",World.getWorld().getPhoneCommandMap().get(ringId+leixing).split(",")[1] );
			sqlQuery.setString("shouhuan_id",ringId );
			sqlQuery.executeUpdate();
			transaction.commit();
			session.close();
			sessionFactory.close();
		}
	}

}

package sepim.server.net.packet;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

/*
 * by LJ  8.29
 */

public class PEDOHandler {
	//暂无使用
	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents,String userId) {
		if(!userId.equals(""))//手机发送
		{
			System.out.println(ringId+"计步功能开关！！");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"计步功能开关设置成功！！");
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//把数据推送给手机
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		
			
			/*
			 * by luojun  8.29
			 */
			SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
			Session session =sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			
			SQLQuery sqlQuery= session.createSQLQuery("update dbo.[shouhuan] set pedo=:pedo where shouhuan_id=:shouhuan_id");
			sqlQuery.setString("pedo",World.getWorld().getPhoneCommandMap().get(ringId+leixing).split(",")[1] );
			sqlQuery.setString("shouhuan_id",ringId );
			sqlQuery.executeUpdate();
			transaction.commit();
			session.close();
			sessionFactory.close();
		}
	}

}

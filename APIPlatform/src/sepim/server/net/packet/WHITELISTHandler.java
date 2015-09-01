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

public class WHITELISTHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents,String userId) {
		if(contents.contains("WHITELIST1"))
		{
			if(contents.contains("WHITELIST1,"))
			{
				System.out.println(ringId+"WHITELIST1设置！！");
				String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
				WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	WHITELIST1设置");
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}
			else
			{
				System.out.println(ringId+"WHITELIST1设置成功！！");
				String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
				WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	WHITELIST1设置成功");
				@SuppressWarnings("deprecation")
				
				SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
				Session session =sessionFactory.openSession();
				Transaction trans=session.beginTransaction();
				
				SQLQuery sqlQueryChannelId = session.createSQLQuery("update dbo.[shouhuan] set whitelist=:whitelist where shouhuan_id=:shouhuan_id");

				sqlQueryChannelId.setString("whitelist",World.getWorld().getPhoneCommandMap().get(ringId+leixing).split(",",2)[1]);
		    	sqlQueryChannelId.setString("shouhuan_id",ringId);
				System.out.println("测试"+sqlQueryChannelId.toString());
		    	sqlQueryChannelId.executeUpdate();
		    	
		    	trans.commit();
				session.close();
				sessionFactory.close();
				
				World.getWorld().getPhoneCommandMap().remove(ringId+leixing);//删除此记录
				
				
				
				
				//把数据封装进json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("type","WHITELIST1");  
				jsonObject.put("shouhuan_id",ringId); 
				//把数据推送给手机
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
		if(contents.contains("WHITELIST2"))
		{
			if(contents.contains("WHITELIST2,"))
			{
				System.out.println(ringId+"WHITELIST2设置成功！！");
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}
			else
			{
				System.out.println(ringId+"WHITELIST2设置成功！！");
				//把数据封装进json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("type","WHITELIST2");  
				jsonObject.put("shouhuan_id",ringId); 
				//把数据推送给手机
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
	}

}

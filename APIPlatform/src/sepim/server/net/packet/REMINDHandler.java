package sepim.server.net.packet;

import net.sf.json.JSONObject;

import com.a.push.Push;

import sepim.server.clients.World;
import sepim.server.clients.WriteSql;

public class REMINDHandler {
	//设置中心号码
		public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) {
		if(!userId.equals(""))//手机发出
		{
			System.out.println(ringId+"闹钟设置！！");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	闹钟设置");
			World.getWorld().WriteMessageToRing(ringId, command);
		}
		else
		{
			System.out.println(ringId+"闹钟设置成功！！");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	闹钟设置成功");
			
			World.getWorld().getPhoneCommandMap().remove(ringId+leixing);//删除此记录
			
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//把数据推送给手机
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
	}
}

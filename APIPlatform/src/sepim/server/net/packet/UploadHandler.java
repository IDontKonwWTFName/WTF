package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;
import sepim.server.clients.WriteSql;

public class UploadHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) {
		if(!userId.equals(""))//手机发出
		{
			System.out.println(ringId+"发送数据上传间隔设置！！");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	发送数据上传间隔设置");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
		else
		{
			System.out.println(ringId+"数据上传间隔设置成功！！");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	数据上传间隔设置成功");
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//把数据推送给手机
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
	}

}

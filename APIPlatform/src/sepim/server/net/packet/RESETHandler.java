package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class RESETHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) {
		if(!userId.equals(""))//手机发送的
		{
			System.out.println("发送重启指令");
			System.out.println("ringId"+ringId);
			System.out.println("["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
		else
		{
			System.out.println(ringId+"重启成功！！");
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("leixing",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//把数据推送给手机
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
	}

}

package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class PULSEHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) 
	{
		if(userId.equals(""))//手环发送的
		{
			System.out.println(ringId+"把以下数据推送到手机"+contents);
			String[] contentsStrings = contents.split(",",2);
			//版本信息
			String pulse = contentsStrings[1];
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			jsonObject.put("pulse",pulse);  
			//把数据推送给手机
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
		else
		{
			System.out.println("发送获取心跳指令！");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

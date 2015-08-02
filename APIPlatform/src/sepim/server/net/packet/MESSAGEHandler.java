package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class MESSAGEHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>7){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"短语显示设置指令设置成功！！");
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("leixing",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//把数据推送给手机
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
	}

}

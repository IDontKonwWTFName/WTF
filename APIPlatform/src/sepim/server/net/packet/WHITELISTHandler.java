package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class WHITELISTHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents) {
		if(contents.contains("WHITELIST1")){
			if(contents.contains("WHITELIST1,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"WHITELIST1设置成功！！");
				//把数据封装进json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("leixing","WHITELIST1");  
				jsonObject.put("shouhuan_id",ringId); 
				//把数据推送给手机
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
		if(contents.contains("WHITELIST2")){
			if(contents.contains("WHITELIST2,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"WHITELIST2设置成功！！");
				//把数据封装进json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("leixing","WHITELIST2");  
				jsonObject.put("shouhuan_id",ringId); 
				//把数据推送给手机
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
	}

}

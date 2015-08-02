package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class SOSHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents) {
		if(contents.contains("SOS1")){
			if(contents.contains("SOS1,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"sos1设置成功！！");
				//把数据封装进json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("leixing","SOS1");  
				jsonObject.put("shouhuan_id",ringId); 
				//把数据推送给手机
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
		if(contents.contains("SOS2")){
			if(contents.contains("SOS2,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"sos2设置成功！！");
				//把数据封装进json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("leixing","SOS2");  
				jsonObject.put("shouhuan_id",ringId); 
				//把数据推送给手机
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
		if(contents.contains("SOS3")){
			if(contents.contains("SOS3,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"sos3设置成功！！");
				//把数据封装进json
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("leixing","SOS3");  
				jsonObject.put("shouhuan_id",ringId); 
				//把数据推送给手机
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
		}
		if(contents.contains("SOS")&&contentsLength.equals("0003")){
			System.out.println(ringId+"sos同时设置成功！！");
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("leixing","SOS");  
			jsonObject.put("shouhuan_id",ringId); 
			//把数据推送给手机
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
		if(contents.contains("SOS,")){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

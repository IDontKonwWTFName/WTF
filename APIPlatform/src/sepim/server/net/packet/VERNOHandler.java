package sepim.server.net.packet;

import java.util.ArrayList;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class VERNOHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents,String userId) {
		if(userId.equals(""))//手环发出
		{
			System.out.println(ringId+"版本查询成功"+World.getWorld().getRingPhoneMap().get(ringId)+"手机"+contents);
			String[] contentsStrings = contents.split(",",2);
			//版本信息
			String verno = contentsStrings[1];
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			jsonObject.put("verno",verno);  
			//把数据推送给手机
			ArrayList<String> channelIdList = new ArrayList<String>();
			channelIdList.add(World.getWorld().getRingPhoneMap().get(ringId));
			new Push().pushToApp(channelIdList,jsonObject.toString());
		}
		else
		{
			System.out.println(ringId+"版本查询！！");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

package sepim.server.net.packet;

import java.util.ArrayList;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class URLHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents,String userId) {
		if(userId.equals(""))//手环发出
		{
			System.out.println(ringId+"查询 URL谷歌链接成功"+contents);
			String[] contentsStrings = contents.split(",",2);
			String[] parameterStrings = contentsStrings[1].split(";");
			//谷歌URL
			String url = parameterStrings[0].split(":",2)[1];
			//日期
			String data = parameterStrings[1].split(":")[1];
			//时间
			String time = parameterStrings[2].split(":",2)[1];
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("leixing",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			jsonObject.put("url",url);  
			jsonObject.put("data",data);
			jsonObject.put("time",time);
			//把数据推送给手机
			ArrayList<String> channelIdList = new ArrayList<String>();
			channelIdList.add(World.getWorld().getRingPhoneMap().get(ringId));
			new Push().pushToApp(channelIdList,jsonObject.toString());
		}
		else
		{
			System.out.println(ringId+"查询 URL谷歌链接："+contents);
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

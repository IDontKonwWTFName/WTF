package sepim.server.net.packet;

import java.util.Date;

import net.sf.json.JSONObject;

import com.a.push.Push;

import sepim.server.clients.World;
import sepim.server.clients.WriteSql;


public class LinkHandler {
	
 
	public void handle(String leixing,String company, String ringId, String contentsLength, String contents,String userId) 
	{
		if(userId.equals(""))//手环发过来的
		{
			System.out.println("链路保持");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	链路保持");
//			String[] contentsStrings = contents.split(",");
//			if(contentsStrings.length==4)
//			{
//				//步数
//				String steps = contentsStrings[1];
//				//翻滚次数
//				String turning = contentsStrings[2];
//				//电量百分比
//				String elecPec = contentsStrings[3];
//				//把数据封装进json
//				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("type",leixing);  
//				jsonObject.put("shouhuan_id",ringId); 
//				jsonObject.put("steps",steps);  
//				jsonObject.put("turning",turning);  
//				jsonObject.put("elecPower",elecPec);
//				//把数据推送给手机
//				Push push =new Push();
//				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
//			}
			
			if(World.getWorld().getRingLkTimeMap().get(ringId)==null)//上线
			{
	        	JSONObject jsonObject = new JSONObject();
				jsonObject.put("type","on");  
				jsonObject.put("shouhuan_id",ringId);
				jsonObject.put("gsmSignalStr","100");  
				jsonObject.put("elecPower","100");  
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
			Date now = new Date();
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*0002*LK"+"]");
			World.getWorld().getRingLkTimeMap().put(ringId, now);
		}
	}

}

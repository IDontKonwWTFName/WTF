package sepim.server.net.packet;

import java.util.Hashtable;

import net.sf.json.JSONObject;

import org.jboss.netty.channel.Channel;

import com.a.push.Push;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.net.packet.handled.ChatPacketHandler;
import sepim.server.net.packet.handled.CommandPacketHandler;
import sepim.server.net.packet.handled.DefaultPacketHandler;
import sepim.server.net.packet.handled.PackHandler;


public class LinkHandler {
	
 
public void handle(String leixing,String company, String ringId, String contentsLength, String contents) {
	if(contents.length()>2){
		String[] contentsStrings = contents.split(",");
		//步数
		String steps = contentsStrings[1];
		//翻滚次数
		String turning = contentsStrings[2];
		//电量百分比
		String elecPec = contentsStrings[3];
		//把数据封装进json
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("leixing",leixing);  
		jsonObject.put("shouhuan_id",ringId); 
		jsonObject.put("steps",steps);  
		jsonObject.put("turning",turning);  
		jsonObject.put("elecPec",elecPec);
		//把数据推送给手机
		new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
	}else{
		World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
	}
}

}

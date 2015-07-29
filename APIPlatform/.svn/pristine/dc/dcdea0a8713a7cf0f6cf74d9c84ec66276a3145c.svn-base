package sepim.server.net.packet;

import sepim.server.clients.World;

public class SMSHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>3){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"短信指令发送成功！！");
		}
	}

}

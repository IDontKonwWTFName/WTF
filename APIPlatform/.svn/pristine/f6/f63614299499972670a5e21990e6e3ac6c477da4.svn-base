package sepim.server.net.packet;

import sepim.server.clients.World;

public class POWEROFFHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>8){
			contents=contents.substring(0,contents.length()-4);
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"关机指令发送成功！！");
		}
	}

}

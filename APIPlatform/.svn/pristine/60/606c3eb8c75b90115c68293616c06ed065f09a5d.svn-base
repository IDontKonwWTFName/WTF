package sepim.server.net.packet;

import sepim.server.clients.World;

public class WORKHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>4){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"工作时间段设置指令设置成功！！");
		}
	}

}

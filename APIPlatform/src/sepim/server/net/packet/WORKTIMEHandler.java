package sepim.server.net.packet;

import sepim.server.clients.World;

public class WORKTIMEHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>8){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"工作时间设置指令设置成功！！");
		}
	}

}

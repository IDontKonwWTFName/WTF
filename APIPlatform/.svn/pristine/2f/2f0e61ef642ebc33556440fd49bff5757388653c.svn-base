package sepim.server.net.packet;

import sepim.server.clients.World;

public class CALLHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>4){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"拨打电话指令发送成功！！");
		}
	}

}

package sepim.server.net.packet;

import sepim.server.clients.World;

public class SlaveHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>6){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"辅助中心号码设置成功！！");
		}
	}

}

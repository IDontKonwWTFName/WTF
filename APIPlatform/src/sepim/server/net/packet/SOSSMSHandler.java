package sepim.server.net.packet;

import sepim.server.clients.World;

public class SOSSMSHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>6){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"SOS短信报警开关设置成功！！");
		}
	}

}

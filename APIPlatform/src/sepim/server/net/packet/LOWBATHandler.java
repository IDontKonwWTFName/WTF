package sepim.server.net.packet;

import sepim.server.clients.World;

public class LOWBATHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>6){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"低电短信报警开关设置成功！！");
		}
	}

}

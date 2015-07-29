package sepim.server.net.packet;

import sepim.server.clients.World;

public class LZHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>2){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"语言和时区设置成功！！");
		}
	}

}

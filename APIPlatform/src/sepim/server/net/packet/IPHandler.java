package sepim.server.net.packet;

import sepim.server.clients.World;

public class IPHandler {
//暂时未用
	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents) {
			System.out.println("IP端口设置");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
	}

}

package sepim.server.net.packet;

import sepim.server.clients.World;

public class IPHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents) {
			System.out.println("IP¶Ë¿ÚÉèÖÃ");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
	}

}

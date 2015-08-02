package sepim.server.net.packet;

import sepim.server.clients.World;

public class RGHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents) {
		World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}

}

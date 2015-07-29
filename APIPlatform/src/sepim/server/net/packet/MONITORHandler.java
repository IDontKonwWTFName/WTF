package sepim.server.net.packet;

import sepim.server.clients.World;

public class MONITORHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>7){
			contents=contents.substring(0,contents.length()-4);
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"¼àÌıÉèÖÃ³É¹¦£¡£¡");
		}
	}

}

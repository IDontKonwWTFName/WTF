package sepim.server.net.packet;

import sepim.server.clients.World;

public class TSHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>2){
			System.out.println(ringId+"�������������͵��ֻ�"+contents);
		}else{
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

package sepim.server.net.packet;

import sepim.server.clients.World;

public class TSHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>2){
			System.out.println(ringId+"把以下数据推送到手机"+contents);
		}else{
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

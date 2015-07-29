package sepim.server.net.packet;

import sepim.server.clients.World;

public class WHITELISTHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.contains("WHITELIST1")){
			if(contents.contains("WHITELIST1,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"WHITELIST1设置成功！！");
			}
		}
		if(contents.contains("WHITELIST2")){
			if(contents.contains("WHITELIST2,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"WHITELIST2设置成功！！");
			}
		}
	}

}

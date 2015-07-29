package sepim.server.net.packet;

import sepim.server.clients.World;

public class CRHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>2){
			contents=contents.substring(0,contents.length()-4);
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"定位指令发送成功！！");
		}
	}

}

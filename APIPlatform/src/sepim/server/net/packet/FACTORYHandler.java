package sepim.server.net.packet;

import sepim.server.clients.World;

public class FACTORYHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>7){
			contents=contents.substring(0,contents.length()-4);
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"恢复出厂设置成功！！");
		}
	}

}

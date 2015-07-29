package sepim.server.net.packet;

import sepim.server.clients.World;

public class UPGRADEHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>7){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"远程升级设置成功！！");
		}
	}

}

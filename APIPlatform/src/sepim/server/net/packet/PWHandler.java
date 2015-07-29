package sepim.server.net.packet;

import sepim.server.clients.World;

public class PWHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>2){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"控制密码设置设置成功！！");
		}
	}

}

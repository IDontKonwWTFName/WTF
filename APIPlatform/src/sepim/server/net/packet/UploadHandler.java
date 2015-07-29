package sepim.server.net.packet;

import sepim.server.clients.World;

public class UploadHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>6){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}else{
			System.out.println(ringId+"数据上传间隔设置成功！！");
		}
	}

}

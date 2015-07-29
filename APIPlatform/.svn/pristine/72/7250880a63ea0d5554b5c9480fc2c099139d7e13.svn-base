package sepim.server.net.packet;

import sepim.server.clients.World;

public class SOSHandler {

	public void handle(String company, String ringId, String contentsLength,
			String contents) {
		if(contents.contains("SOS1")){
			if(contents.contains("SOS1,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"sos1设置成功！！");
			}
		}
		if(contents.contains("SOS2")){
			if(contents.contains("SOS2,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"sos2设置成功！！");
			}
		}
		if(contents.contains("SOS3")){
			if(contents.contains("SOS3,")){
				World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			}else{
				System.out.println(ringId+"sos3设置成功！！");
			}
		}
		if(contents.contains("SOS")&&contentsLength.equals("0003")){
			System.out.println(ringId+"sos同时设置成功！！");
		}
		if(contents.contains("SOS,")){
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

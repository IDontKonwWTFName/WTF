package sepim.server.net.packet;

import sepim.server.clients.World;

public class IPHandler {
//��ʱδ��
	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents) {
			System.out.println("IP�˿�����");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
	}

}

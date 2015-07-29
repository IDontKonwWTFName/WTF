package sepim.server.net.packet;

import java.util.Hashtable;

import sepim.server.clients.Client;
import sepim.server.net.packet.handled.ChatPacketHandler;
import sepim.server.net.packet.handled.CommandPacketHandler;
import sepim.server.net.packet.handled.DefaultPacketHandler;

public class ServerPacketHandler {
	
   
	
public void handle(Client client, Packet packet) {

		final int opcode = packet.getOpcode();
		//map string to int
		Hashtable<String, Integer> maptable= new Hashtable();
		maptable.put("cmd",0);
		maptable.put("voi",1);
		maptable.put("sos",2);
		int opnum=maptable.get(opcode);
		
//		switch(opcode) {
//		case 0:
//			// change CommandPacketHandler to PacketHandler
//			new ServerPacketHandler().handle(client, packet);
//			break;
//		case 1:
//			new ChatPacketHandler().handle(client, packet);
//			break;
//		default:
//			new DefaultPacketHandler().handle(client, packet);
//			break;
//		}
	}

}

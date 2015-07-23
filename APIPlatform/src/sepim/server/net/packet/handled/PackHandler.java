package sepim.server.net.packet.handled;

import sepim.server.clients.Client;
import sepim.server.clients.chat.Chat;
import sepim.server.net.packet.Packet;

public class PackHandler {
	public void handle(Client client, Packet packet){
		
		String command = packet.getString();
		//handle the packet
		System.out.println(command);
		int len = packet.getString().length();
	/*	for(int i=0; i<len; i++){
			System.out.println(command.charAt(i));
		}*/
	} 

}

package sepim.server.net.packet.handled;

import org.jboss.netty.channel.Channel;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.clients.chat.Chat;
import sepim.server.net.packet.Packet;

public class CommandPacketHandler {
	public void handle(Channel channel, Packet packet){
		
		String command = packet.getString();
		System.out.println(command);
		int len = packet.getString().length();
	} 

}

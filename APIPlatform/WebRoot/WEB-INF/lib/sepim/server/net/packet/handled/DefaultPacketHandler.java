package sepim.server.net.packet.handled;

import java.util.logging.Logger;

import org.jboss.netty.channel.Channel;

import sepim.server.clients.Client;
import sepim.server.net.packet.Packet;

public class DefaultPacketHandler {
	public void handle(Channel channel, Packet packet){
		
		String command = packet.getString();
		System.out.println(command);
		int len = packet.getString().length();
	} 

}

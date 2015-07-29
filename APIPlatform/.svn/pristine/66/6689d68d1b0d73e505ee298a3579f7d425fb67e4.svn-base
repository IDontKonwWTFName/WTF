package sepim.server.net.packet;

import java.util.Hashtable;

import org.jboss.netty.channel.Channel;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.net.packet.handled.ChatPacketHandler;
import sepim.server.net.packet.handled.CommandPacketHandler;
import sepim.server.net.packet.handled.DefaultPacketHandler;
import sepim.server.net.packet.handled.PackHandler;


public class LinkHandler {
	
 
public void handle(String company, String ringId, String contentsLength, String contents) {
	if(contents.length()>2){
		/*
		 * 给servlet传数据
		 */
		World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
	}else{
		World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
	}
}

}

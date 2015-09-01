package sepim.server.net;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.clients.chat.Chat;
import sepim.server.net.packet.Packet;
import sepim.server.net.packet.LinkHandler;

// handler for ring
public class RServerHandler extends SimpleChannelHandler {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private final LinkHandler packetHandler = new LinkHandler();
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Packet packet = (Packet) e.getMessage();
		//System.out.println(e.getMessage().toString());
		//transfer MessageEvent to ArrayList
	/*	List<Object> list=new ArrayList<Object>();
		list.add(e);
		for(int i=0; i<list.size();i++)
		{
			System.out.println(list.get(i));
		}*/
		
		//System.out.println(packet.getOpcode());
//		if(packet != null) {
//			
//			packetHandler.handle(ctx.getChannel(), packet);
//			//System.out.println(packet.getString());
//		}
		//print the receive message

		
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
//		World.getWorld().register(ctx.getChannel());
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
//		World.getWorld().unregister(ctx.getChannel());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.info("Exception caught: " + e.getCause());
	}

}

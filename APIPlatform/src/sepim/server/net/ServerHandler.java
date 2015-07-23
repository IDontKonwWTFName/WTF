package sepim.server.net;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.platform.model.Group;
import com.platform.model.Historyrecord;
import com.platform.model.User;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.clients.chat.Chat;
import sepim.server.net.packet.Packet;
import sepim.server.net.packet.PacketHandler;

public class ServerHandler extends SimpleChannelHandler {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	private final PacketHandler packetHandler = new PacketHandler();
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		System.out.println("receive a message from client");
		 // the below code may encounter error
		Packet packet = (Packet) e.getMessage();
		System.out.println("put received message into local packet, the packet is"+packet.getString());
		//System.out.println(e.getMessage().toString());
		//transfer MessageEvent to ArrayList
	/*	List<Object> list=new ArrayList<Object>();
		list.add(e);
		for(int i=0; i<list.size();i++)
		{
			System.out.println(list.get(i));
		}*/
		
		//System.out.println(packet.getOpcode());
		SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		User group =new User();
		
		group.setUser_id(packet.getString());
		
		
		session.save(group);
		
		transaction.commit();
		session.close();
		sessionFactory.close();
		
		
		if(packet != null) {
			
			packetHandler.handle(World.getWorld().getClient(ctx.getChannel()), packet);
			System.out.println(packet.getString());
		}
		//print the receive message

		
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		World.getWorld().register(new Client(ctx.getChannel()));
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Client client = World.getWorld().getClient(ctx.getChannel());
		World.getWorld().unregister(client);
		World.getWorld().updateUserList();
		Chat chat = client.getChat();
		if(chat != null) {
			chat.remove(client);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.info("Exception caught: " + e.getCause());
	}

}

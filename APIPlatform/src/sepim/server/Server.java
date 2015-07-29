package sepim.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.net.ServerPipelineFactory;

public class Server {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	public Server() {
		NioServerSocketChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ServerPipelineFactory());
		bootstrap.bind(new InetSocketAddress("192.168.199.179", 8082));
		logger.info("Ready and Listening");
	}
	
	public static void main(String args[]) {
		new Server();
	}

}

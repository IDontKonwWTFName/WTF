package sepim.server.net;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

import sepim.server.net.handlers.TestDecoder;



public class ServerPipelineFactory implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		/*
		 * ºúÆôî¸ÊÔÑéº¯Êý
		 * 
		 * */
		//pipeline.addLast("decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,15,4,1,23));
	     pipeline.addLast("decoder", new TestDecoder(Integer.MAX_VALUE, 15, 4, 2, 0)); 
	     //pipeline.addLast("encoder", new LengthFieldPrepender(4, false));
	     pipeline.addLast("handler", new ServerHandler());
	     return pipeline;
	}

}

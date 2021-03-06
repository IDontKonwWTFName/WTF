package sepim.server.net;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

import sepim.server.net.handlers.LinebasedEncoder;
import sepim.server.net.handlers.TestDecoder;



public class ServerPipelineFactory implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		/*
		 * ��������麯��
		 * 
		 * */
	     pipeline.addLast("decoder", new TestDecoder(Integer.MAX_VALUE, 15, 4, 2, 0)); 
	     pipeline.addLast("encoder", new LinebasedEncoder());
	     pipeline.addLast("handler", new ServerHandler());
	     return pipeline;
	}

}

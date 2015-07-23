package sepim.server.net;

import static org.jboss.netty.channel.Channels.*;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.Delimiters;

import sepim.server.net.handlers.LinebasedDecoder;
import sepim.server.net.handlers.LinebasedEncoder;
import sepim.server.net.handlers.PacketDecoder;
import sepim.server.net.handlers.PacketEncoder;


public class ServerPipelineFactory implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		//pipeline.addLast("decoder", new PacketDecoder());
		//pipeline.addLast("encoder", new PacketEncoder());
		//ring
		pipeline.addLast("decodehandler1", new LinebasedDecoder(8192, false, Delimiters.lineDelimiter()));  
        
		pipeline.addLast("encoderhandler1", new LinebasedEncoder());
		
		pipeline.addLast("handler", new ServerHandler());
		return pipeline;
	}

}

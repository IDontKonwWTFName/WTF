package sepim.server.net.handlers;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import sepim.server.net.packet.Packet;

public class LinebasedEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel chl, Object obj) throws Exception {
		Packet packet = (Packet) obj;
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeByte(packet.getOpcode());
		buffer.writeBytes(packet.getBuffer());
		return buffer;
	}

}

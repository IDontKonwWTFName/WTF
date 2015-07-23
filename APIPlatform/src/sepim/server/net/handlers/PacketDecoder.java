package sepim.server.net.handlers;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import sepim.server.net.packet.Packet;


public class PacketDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel chl, ChannelBuffer buf) throws Exception {
		if(buf.readableBytes() > 0) {
			// packet(opcode, content)
			System.out.println(buf.readByte());
			System.out.println(buf.readerIndex());
			Packet packet = new Packet(buf.readByte(), buf);
			//Packet packet = new Packet(buf);
			//System.out.println(packet.getString());
			return packet;
		}
		return null;
	}

}

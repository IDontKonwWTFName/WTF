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
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		byte[] body = obj.toString().getBytes();  //将对象转换为byte
        buffer.writeBytes(body);  //消息体中包含我们要发送的数据
		return buffer;
	}
}

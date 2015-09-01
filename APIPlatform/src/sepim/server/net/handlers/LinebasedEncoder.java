package sepim.server.net.handlers;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;


public class LinebasedEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel chl, Object obj) throws Exception {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		System.out.println("Encoder�����ͣ�"+obj.getClass());
		if(obj.getClass().equals(String.class))
		{
			byte[] body = obj.toString().getBytes();  //������ת��Ϊbyte
	        buffer.writeBytes(body);  //��Ϣ���а�������Ҫ���͵�����
		}
		else 
		{
			byte[] body = (byte[]) obj;
		    buffer.writeBytes(body);  //��Ϣ���а�������Ҫ���͵�����
		}
		return buffer;
	}
}

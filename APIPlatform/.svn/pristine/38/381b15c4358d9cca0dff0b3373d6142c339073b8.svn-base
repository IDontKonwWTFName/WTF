package sepim.server.net.handlers;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import sepim.server.net.packet.Packet;
public class LinebasedDecoder extends DelimiterBasedFrameDecoder{
	

private	ChannelBuffer bufcontent;
public static int maxFrameLen;
  
	public LinebasedDecoder(int maxFrameLength, boolean stripDelimiter, ChannelBuffer[] delimiter) {
		
		super(maxFrameLength, stripDelimiter, delimiter);
		// TODO Auto-generated constructor stub
		
	}




	// DelimiterBasedFrameDecoder(Delimiters.lineDelimiter())
  protected  Object decode(ChannelHandlerContext ctx, Channel chl, ChannelBuffer buf) throws Exception {
		this.bufcontent=buf;
	  if(bufcontent.readableBytes() > 0) {
			// packet(opcode, content)
		     buf.readBytes(bufcontent);
		     // 根据协议内容读取opcode 
		      // getBytes(int index, byte[] dst, int dstIndex, int length) 
	         //current, we choose the first byte
		     while(buf.readable()) {
		     System.out.println("the "+buf.readerIndex() +" byte is "+buf.readByte());
		     }
			
			 System.out.println("the buffer's current readerIndex is "+buf.readerIndex());
			 buf.resetReaderIndex();
			 System.out.println("the buffer's current readerIndex is "+buf.readerIndex());
			
			 Packet packet = new Packet(buf.readByte(),bufcontent);
			 System.out.println("the decode packet is "+packet.getString());
			//Packet packet = new Packet(buf);
			//System.out.println(packet.getString());
			return packet;
		}
		return null;
	}
	

}

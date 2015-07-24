package sepim.server.net.packet;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class Serverpacket {
	
	private String opcode;
	
	private ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
	
	public Serverpacket(String opcode) {
		this.opcode = opcode;
	}
	
	public Serverpacket(String opcode, ChannelBuffer buffer) {
		this.opcode = opcode;
		this.buffer = buffer;
	}
	
	public Serverpacket(ChannelBuffer buffer) {
		
		this.buffer = buffer;
	}
	
	public Serverpacket put(int b) {
		buffer.writeByte(b);
		return this;
	}
	
	public Serverpacket put(byte[] b) {
		//write bytes to buffer
		buffer.writeBytes(b);
		return this;
	}
	
	public Serverpacket putString(String s) {
		put(s.getBytes().length);
		return put(s.getBytes());
	}
	
	public String getString() {
		int length = get();
		byte[] b = new byte[length];
		for(int i = 0; i < b.length; i++) {
			b[i] = get();
		}
		return new String(b);
	}
	
	public byte get() {
		//get a byte from this buffer
		return buffer.readByte();
	}
	
	public String getOpcode() {
		return opcode;
	}
	
	public ChannelBuffer getBuffer() {
		return buffer;
	}

}

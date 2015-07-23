package sepim.server.net.packet;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class Packet {
	
	private int opcode;
	// ring command
	private String opString;
	
	private ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
	
	public Packet(int opcode) {
		this.opcode = opcode;
	}
	
	public Packet(int opcode, ChannelBuffer buffer) {
		this.opcode = opcode;
		this.buffer = buffer;
	}
	
	public Packet(ChannelBuffer buffer) {
		
		this.buffer = buffer;
	}
	
	public Packet put(int b) {
		buffer.writeByte(b);
		return this;
	}
	
	public Packet put(byte[] b) {
		//write bytes to buffer
		buffer.writeBytes(b);
		return this;
	}
	
	public Packet putString(String s) {
		put(s.getBytes().length);
		return put(s.getBytes());
	}
	
	public String getString() {
		buffer.resetReaderIndex();
		buffer.readByte();
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
	// for ring command
	public String getOpString() {
		opString=this.getString().substring(0, 1);
		return opString;
	}
	public int getOpcode() {
		
		return opcode;
	}
	// for ring command
	public void setOpcode(int Opcode) {
		this.opcode=Opcode;
	}
	
	
	public ChannelBuffer getBuffer() {
		return buffer;
	}

}

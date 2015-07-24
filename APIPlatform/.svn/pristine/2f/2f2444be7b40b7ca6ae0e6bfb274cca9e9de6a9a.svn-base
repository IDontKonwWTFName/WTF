package sepim.server.clients;

import org.jboss.netty.channel.Channel;

import sepim.server.clients.chat.Chat;
import sepim.server.net.packet.Packet;

public class Client {
	
	private Channel channel;
	
	private int index;
	
	private String username;
	
	private Chat chat;
	
	public Client(Channel channel) {
		this.channel = channel;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public int getId() {
		return index;
	}
	
	public void setId(int index) {
		this.index = index;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void sendErrorMessage(String string) {
		this.getChannel().write(new Packet(0).putString(string));
	}
	// not implemented
	public void sendChatMessage(String string) {
		this.getChannel().write(new Packet(1).putString(string));
	}
	
	
     public void sendClearChatArea() {
		this.getChannel().write(new Packet(2));
	}
	
	public void sendUpdateChatUsers() {
		Chat chat = getChat();
		Packet packet = new Packet(3);
		if(chat != null) {
			packet.put(chat.getClients().size());
			for(Client c : chat.getClients()) {
				packet.putString(c.getUsername());
			}
		}
		this.getChannel().write(packet);
	}
	
	public void setChat(Chat chat) {
		this.chat = chat;
	}
	
	public Chat getChat() {
		return chat;
	}

}

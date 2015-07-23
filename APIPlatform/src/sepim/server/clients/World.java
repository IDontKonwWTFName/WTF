package sepim.server.clients;

import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.channel.Channel;

import sepim.server.clients.chat.Chat;

public class World {
	
	private List<Client> clients = new ArrayList<Client>();
	
	private List<Chat> chats = new ArrayList<Chat>();
	
	private static final World world = new World();
	
	private int index;
	
	public static World getWorld() {
		return world;
	}
	
	public void updateUserList() {
		for(Client c : clients) {
			c.sendUpdateChatUsers();
		}
	}
	
	public void register(Client client) {
		client.setId(index++);
		clients.add(client);
	}
	
	public void unregister(Client client) {
		clients.remove(client);
	}
	
	public void addChat(Chat chat) {
		chats.add(chat);
	}
	
	public void removeChat(Chat chat) {
		chats.remove(chat);
	}
	
	public boolean chatExists(Chat chat) {
		for(Chat c : chats) {
			if(c.getId().equalsIgnoreCase(chat.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public Chat getChat(String id) {
		for(Chat c : chats) {
			if(c.getId().equalsIgnoreCase(id)) {
				return c;
			}
		}
		return null;
	}
	
	public Client getClient(Channel channel) {
		for(Client client : clients) {
			if(client.getChannel().equals(channel)) {
				return client;
			}
		}
		return null;
	}
	
	public List<Client> getClients() {
		return clients;
	}

}

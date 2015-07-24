package sepim.server.clients.chat;

import java.util.ArrayList;
import java.util.List;

import sepim.server.clients.Client;

public class Chat {
	
	private List<Client> clients = new ArrayList<Client>();
	
	private String id;
	
	private String pass = null;
	
	private Client creator;
	
	public Chat(String id, Client creator) {
		this.id = id;
		this.creator = creator;
	}
	
	public Chat(String id, String pass, Client creator) {
		this.id = id;
		this.pass = pass;
		this.creator = creator;
	}
	
	public String getId() {
		return id;
	}
	
	public Client getCreator() {
		return creator;
	}
	
	public void add(Client client) {
		clients.add(client);
	}
	
	public void remove(Client client) {
		clients.remove(client);
	}
	
	public List<Client> getClients() {
		return clients;
	}
	
	public boolean clientExists(Client client) {
		for(Client c : clients) {
			if(c.equals(client)) {
				return true;
			}
		}
		return false;
	}
	
	public String getPassword() {
		return pass;
	}

}

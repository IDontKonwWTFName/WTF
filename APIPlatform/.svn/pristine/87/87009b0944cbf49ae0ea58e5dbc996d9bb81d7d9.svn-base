package sepim.server.net.packet.handled;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.clients.chat.Chat;
import sepim.server.net.packet.Packet;

public class CommandPacketHandler {

	public void handle(Client client, Packet packet) {
		String command = packet.getString();
		String[] cmd = command.split(" ");
		if(cmd.length > 1) {
			if(cmd[0].equalsIgnoreCase("nick")) {
				String user = cmd[1].trim();
				if(client.getChat() != null) {
					String oldUser = client.getUsername();
					client.setUsername(user);
					client.sendErrorMessage("You have just set your nick to " + user);
					for(Client c : client.getChat().getClients()) {
						if(!c.equals(client)) {
							c.sendErrorMessage(oldUser + " changed his nick to " + user);
						}
					}
				} else {
					client.setUsername(user);
					client.sendErrorMessage("You have just set your nick to " + user);
				}
			}
			if(cmd[0].equalsIgnoreCase("create")) {
				if(client.getUsername() != null) {
					Chat chat = new Chat(cmd[1].trim(), client);
					if(!World.getWorld().chatExists(chat)) {
						World.getWorld().addChat(chat);
						chat.add(client);
						client.setChat(chat);
						client.sendClearChatArea();
						World.getWorld().updateUserList();
						client.sendErrorMessage("You have just created and joined " + chat.getId());
					} else {
						client.sendErrorMessage(chat.getId() + " already exists.");
					}
				} else {
					client.sendErrorMessage("You need to set a nick before you can create a chat.");
				}
			}
			if(cmd[0].equalsIgnoreCase("join")) {
				if(client.getUsername() != null) {
					String id = cmd[1].trim();
					Chat chat = World.getWorld().getChat(id);
					if(chat != null) {
						if(!chat.clientExists(client)) {
							chat.add(client);
							client.setChat(chat);
							client.sendClearChatArea();
							World.getWorld().updateUserList();
							client.sendErrorMessage("You have just joined " + id);
						} else {
							client.sendErrorMessage("You are already in " + id);
						}
					} else {
						client.sendErrorMessage(id + " does not exist.");
					}
				} else {
					client.sendErrorMessage("You need to set a nick before you can join a chat.");
				}
			}
			if(cmd[0].equalsIgnoreCase("leave")) {
				Chat chat = client.getChat();
				if(chat != null) {
					chat.remove(client);
					client.sendClearChatArea();
					World.getWorld().updateUserList();
					client.sendErrorMessage("You have just left the chat");
				} else {
					client.sendErrorMessage("You are not in a chat");
				}
			}
			if(cmd[0].equalsIgnoreCase("delete")) {
				String id = cmd[1].trim();
				Chat chat = World.getWorld().getChat(id);
				if(chat != null) {
					if(chat.getCreator().equals(client)) {
						World.getWorld().removeChat(chat);
						chat.remove(client);
						client.setChat(null);
						client.sendClearChatArea();
						client.sendErrorMessage("You have deleted " + id);
						for(Client c : chat.getClients()) {
							c.setChat(null);
							c.sendClearChatArea();
							c.sendUpdateChatUsers();
							c.sendErrorMessage(id + " has been deleted and you were kicked out.");
						}
						World.getWorld().updateUserList();
					} else {
						client.sendErrorMessage("You are not the creator and cannot delete " + id);
					}
				} else {
					client.sendErrorMessage(id + " does not exist.");
				}
			}
		}
	}

}

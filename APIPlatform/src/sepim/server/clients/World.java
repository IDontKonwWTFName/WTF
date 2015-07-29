package sepim.server.clients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jboss.netty.channel.Channel;

import sepim.server.clients.chat.Chat;

public class World {
	
	private List<Channel> channelList = new ArrayList<Channel>();
	
	private List<Chat> chats = new ArrayList<Chat>();
	
	private static final World world = new World();
	
	private HashMap<String,Channel> ringChannelMap = new HashMap<String,Channel>();
	private HashMap<Channel,String> channelRingMap = new HashMap<Channel,String>();
	
	private int index;
	
	public static World getWorld() {
		return world;
	}
	
//	public void updateUserList() {
//		for(Channel c : channelList) {
//			c.sendUpdateChatUsers();
//		}
//	}
	
	public void register(String ringId,Channel channel) {
		if(!ringChannelMap.keySet().contains(ringId)){
			setIndex(getIndex() + 1);
			channelRingMap.put(channel, ringId);
			ringChannelMap.put(ringId, channel);
			channelList.add(channel);
		}
	}
	
	public void unregister(Channel channel) {
		setIndex(getIndex() - 1);
		String ringId = channelRingMap.get(channel);
		ringChannelMap.remove(ringId);
		channelRingMap.remove(channel);
		channelList.remove(channel);
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
	
	public List<Channel> getChannelList() {
		return channelList;
	}

	public HashMap<String,Channel> getRingChannelMap() {
		return ringChannelMap;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public HashMap<Channel,String> getChannelRingMap() {
		return channelRingMap;
	}
	
	public void WriteMessageToRing(String channelId,String writeMessage){
		Channel writeChannel = ringChannelMap.get(channelId);
		if(writeChannel!=null){
			writeChannel.write(writeMessage);
		}
	}

}

package sepim.server.clients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jboss.netty.channel.Channel;

import sepim.server.clients.chat.Chat;

public class World {
	
	//客户端手环channelList
	private List<Channel> channelList = new ArrayList<Channel>();
	
	//外部获取全局资源对象
	private static final World world = new World();
	
	//手环id对应手环channel的映射关系
	private HashMap<String,Channel> ringChannelMap = new HashMap<String,Channel>();
	
	//手环channel对应手环ID的映射关系
	private HashMap<Channel,String> channelRingMap = new HashMap<Channel,String>();
	
	//手环和当前操作手环的电话对应映射关系
	private HashMap<String, String> ringPhoneMap = new HashMap<String, String>();
	
	//手环和所有具有操作手环权限的映射关系
	private HashMap<String,ArrayList<String>> ringPhoneListMap = new HashMap<String, ArrayList<String>>();
	
	//当前连接到平台的手环数量
	private int index;
	
	public static World getWorld() {
		return world;
	}
	
	/*
	 * 手环发送连接信息，注册到平台
	 * @ringId 手环ID
	 * @channel 手环建立的channel
	 */
	public void register(String ringId,Channel channel) {
		if(!ringChannelMap.keySet().contains(ringId)){
			setIndex(getIndex() + 1);
			channelRingMap.put(channel, ringId);
			ringChannelMap.put(ringId, channel);
			channelList.add(channel);
		}
	}
	
	/*
	 * 手环解除连接时，从平台解除绑定
	 * @channel 手环建立的channel
	 */
	public void unregister(Channel channel) {
		setIndex(getIndex() - 1);
		String ringId = channelRingMap.get(channel);
		ringChannelMap.remove(ringId);
		channelRingMap.remove(channel);
		channelList.remove(channel);
	}
	
	/*
	 * 获取连接平台的手环channel的列表
	 */
	public List<Channel> getChannelList() {
		return channelList;
	}

	/*
	 * 获取手环ID和手环channel的映射关系
	 */
	public HashMap<String,Channel> getRingChannelMap() {
		return ringChannelMap;
	}

	/*
	 * 获取当前连接平台的手环数量
	 */
	public int getIndex() {
		return index;
	}

	/*
	 * 设置当前连接平台的手环数量
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/*
	 * 获取手环建立channel和手环ID的映射关系
	 */
	public HashMap<Channel,String> getChannelRingMap() {
		return channelRingMap;
	}
	
	/*
	 * 通过手环ID在映射关系中寻找到对应手环channel并把信息写入手环
	 * @ringId 手环ID
	 * @writeMessage 写入手环信息
	 */
	public void WriteMessageToRing(String ringId,String writeMessage)
	{
		Channel writeChannel = ringChannelMap.get(ringId);
		if(writeChannel!=null)
		{
			System.out.println("服务器端发往手环的writeChannel："+writeChannel);
			System.out.println("服务器端发往手环的消息："+writeMessage);
			writeChannel.write(writeMessage);
		}
	}
	
	/*
	 * 获取当前连接手环的映射关系列表
	 */
	public HashMap<String, String> getRingPhoneMap() {
		return ringPhoneMap;
	}

	/*
	 * 获取当前具有手环连接权限的手机映射关系列表
	 */
	public HashMap<String,ArrayList<String>> getRingPhoneListMap() {
		return ringPhoneListMap;
	}

}

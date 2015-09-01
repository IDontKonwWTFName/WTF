package sepim.server.clients;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jboss.netty.channel.Channel;

import sepim.server.clients.chat.Chat;

public class World {
	
	//�ͻ����ֻ�channelList
	private List<Channel> channelList = new ArrayList<Channel>();
	
	//�ⲿ��ȡȫ����Դ����
	private static final World world = new World();
	
	//�ֻ�id��Ӧ�ֻ�channel��ӳ���ϵ
	private HashMap<String,Channel> ringChannelMap = new HashMap<String,Channel>();
	
	//�ֻ�channel��Ӧ�ֻ�ID��ӳ���ϵ
	private HashMap<Channel,String> channelRingMap = new HashMap<Channel,String>();
	
	//�ֻ��͵�ǰ�����ֻ��ĵ绰��Ӧӳ���ϵ
	private HashMap<String, String> ringPhoneMap = new HashMap<String, String>();
	
	//�ֻ������о��в����ֻ�Ȩ�޵�ӳ���ϵ
	private HashMap<String,ArrayList<String>> ringPhoneListMap = new HashMap<String, ArrayList<String>>();
	
	//��ǰ���ӵ�ƽ̨���ֻ�����
	private int index;
	
	public static World getWorld() {
		return world;
	}
	
	/*
	 * �ֻ�����������Ϣ��ע�ᵽƽ̨
	 * @ringId �ֻ�ID
	 * @channel �ֻ�������channel
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
	 * �ֻ��������ʱ����ƽ̨�����
	 * @channel �ֻ�������channel
	 */
	public void unregister(Channel channel) {
		setIndex(getIndex() - 1);
		String ringId = channelRingMap.get(channel);
		ringChannelMap.remove(ringId);
		channelRingMap.remove(channel);
		channelList.remove(channel);
	}
	
	/*
	 * ��ȡ����ƽ̨���ֻ�channel���б�
	 */
	public List<Channel> getChannelList() {
		return channelList;
	}

	/*
	 * ��ȡ�ֻ�ID���ֻ�channel��ӳ���ϵ
	 */
	public HashMap<String,Channel> getRingChannelMap() {
		return ringChannelMap;
	}

	/*
	 * ��ȡ��ǰ����ƽ̨���ֻ�����
	 */
	public int getIndex() {
		return index;
	}

	/*
	 * ���õ�ǰ����ƽ̨���ֻ�����
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/*
	 * ��ȡ�ֻ�����channel���ֻ�ID��ӳ���ϵ
	 */
	public HashMap<Channel,String> getChannelRingMap() {
		return channelRingMap;
	}
	
	/*
	 * ͨ���ֻ�ID��ӳ���ϵ��Ѱ�ҵ���Ӧ�ֻ�channel������Ϣд���ֻ�
	 * @ringId �ֻ�ID
	 * @writeMessage д���ֻ���Ϣ
	 */
	public void WriteMessageToRing(String ringId,String writeMessage)
	{
		Channel writeChannel = ringChannelMap.get(ringId);
		if(writeChannel!=null)
		{
			System.out.println("�������˷����ֻ���writeChannel��"+writeChannel);
			System.out.println("�������˷����ֻ�����Ϣ��"+writeMessage);
			writeChannel.write(writeMessage);
		}
	}
	
	/*
	 * ��ȡ��ǰ�����ֻ���ӳ���ϵ�б�
	 */
	public HashMap<String, String> getRingPhoneMap() {
		return ringPhoneMap;
	}

	/*
	 * ��ȡ��ǰ�����ֻ�����Ȩ�޵��ֻ�ӳ���ϵ�б�
	 */
	public HashMap<String,ArrayList<String>> getRingPhoneListMap() {
		return ringPhoneListMap;
	}

}

package sepim.server.net.packet;

import java.util.ArrayList;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class VERNOHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents,String userId) {
		if(userId.equals(""))//�ֻ�����
		{
			System.out.println(ringId+"�汾��ѯ�ɹ�"+World.getWorld().getRingPhoneMap().get(ringId)+"�ֻ�"+contents);
			String[] contentsStrings = contents.split(",",2);
			//�汾��Ϣ
			String verno = contentsStrings[1];
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			jsonObject.put("verno",verno);  
			//���������͸��ֻ�
			ArrayList<String> channelIdList = new ArrayList<String>();
			channelIdList.add(World.getWorld().getRingPhoneMap().get(ringId));
			new Push().pushToApp(channelIdList,jsonObject.toString());
		}
		else
		{
			System.out.println(ringId+"�汾��ѯ����");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

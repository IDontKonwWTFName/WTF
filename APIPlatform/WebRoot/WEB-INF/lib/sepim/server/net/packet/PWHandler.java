package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class PWHandler {
	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) 
	{
		if(!userId.equals(""))//�ֻ�����
		{
			System.out.println(ringId+"���������������ã���");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
		else
		{
			System.out.println(ringId+"���������������óɹ�����");
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//���������͸��ֻ�
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
	}

}

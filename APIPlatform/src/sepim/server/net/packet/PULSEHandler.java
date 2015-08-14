package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class PULSEHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) 
	{
		if(userId.equals(""))//�ֻ����͵�
		{
			System.out.println(ringId+"�������������͵��ֻ�"+contents);
			String[] contentsStrings = contents.split(",",2);
			//�汾��Ϣ
			String pulse = contentsStrings[1];
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			jsonObject.put("pulse",pulse);  
			//���������͸��ֻ�
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
		else
		{
			System.out.println("���ͻ�ȡ����ָ�");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

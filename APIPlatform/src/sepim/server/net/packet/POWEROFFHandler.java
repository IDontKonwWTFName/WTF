package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class POWEROFFHandler {
	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) {
		if(!userId.equals(""))//�ֻ����͵�
		{
			System.out.println("���͹ػ�ָ��");
			System.out.println("ringId"+ringId);
			System.out.println("["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
		else//�ֻ����ص�
		{
			System.out.println(ringId+"�ػ�ָ��ͳɹ�����");
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//���������͸��ֻ�
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
	}

}

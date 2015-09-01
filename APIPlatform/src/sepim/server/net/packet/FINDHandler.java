package sepim.server.net.packet;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;
import sepim.server.clients.WriteSql;

public class FINDHandler 
{
	public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) {
		if(!userId.equals(""))//�ֻ�����
		{
			System.out.println(ringId+"�����ֱ�ָ���");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	�����ֱ�ָ��");
			World.getWorld().WriteMessageToRing(ringId, command);
		}else{
			System.out.println(ringId+"�����ֱ�ָ��ͳɹ�����");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	�����ֱ�ָ����Ӧ");
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//���������͸��ֻ�
		    new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
	}

}
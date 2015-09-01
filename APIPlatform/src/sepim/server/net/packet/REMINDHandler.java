package sepim.server.net.packet;

import net.sf.json.JSONObject;

import com.a.push.Push;

import sepim.server.clients.World;
import sepim.server.clients.WriteSql;

public class REMINDHandler {
	//�������ĺ���
		public void handle(String leixing,String company, String ringId, String contentsLength,String contents,String userId) {
		if(!userId.equals(""))//�ֻ�����
		{
			System.out.println(ringId+"�������ã���");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoUserInfo(userId, command+"	��������");
			World.getWorld().WriteMessageToRing(ringId, command);
		}
		else
		{
			System.out.println(ringId+"�������óɹ�����");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	�������óɹ�");
			
			World.getWorld().getPhoneCommandMap().remove(ringId+leixing);//ɾ���˼�¼
			
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			//���������͸��ֻ�
			new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
		}
	}
}

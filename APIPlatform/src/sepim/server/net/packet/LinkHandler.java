package sepim.server.net.packet;

import java.util.Date;

import net.sf.json.JSONObject;

import com.a.push.Push;

import sepim.server.clients.World;
import sepim.server.clients.WriteSql;


public class LinkHandler {
	
 
	public void handle(String leixing,String company, String ringId, String contentsLength, String contents,String userId) 
	{
		if(userId.equals(""))//�ֻ���������
		{
			System.out.println("��·����");
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	��·����");
//			String[] contentsStrings = contents.split(",");
//			if(contentsStrings.length==4)
//			{
//				//����
//				String steps = contentsStrings[1];
//				//��������
//				String turning = contentsStrings[2];
//				//�����ٷֱ�
//				String elecPec = contentsStrings[3];
//				//�����ݷ�װ��json
//				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("type",leixing);  
//				jsonObject.put("shouhuan_id",ringId); 
//				jsonObject.put("steps",steps);  
//				jsonObject.put("turning",turning);  
//				jsonObject.put("elecPower",elecPec);
//				//���������͸��ֻ�
//				Push push =new Push();
//				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
//			}
			
			if(World.getWorld().getRingLkTimeMap().get(ringId)==null)//����
			{
	        	JSONObject jsonObject = new JSONObject();
				jsonObject.put("type","on");  
				jsonObject.put("shouhuan_id",ringId);
				jsonObject.put("gsmSignalStr","100");  
				jsonObject.put("elecPower","100");  
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
			}
			Date now = new Date();
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*0002*LK"+"]");
			World.getWorld().getRingLkTimeMap().put(ringId, now);
		}
	}

}

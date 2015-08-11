package sepim.server.net.packet;

import java.util.ArrayList;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class URLHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents,String userId) {
		if(userId.equals(""))//�ֻ�����
		{
			System.out.println(ringId+"��ѯ URL�ȸ����ӳɹ�"+contents);
			String[] contentsStrings = contents.split(",",2);
			String[] parameterStrings = contentsStrings[1].split(";");
			//�ȸ�URL
			String url = parameterStrings[0].split(":",2)[1];
			//����
			String data = parameterStrings[1].split(":")[1];
			//ʱ��
			String time = parameterStrings[2].split(":",2)[1];
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("leixing",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			jsonObject.put("url",url);  
			jsonObject.put("data",data);
			jsonObject.put("time",time);
			//���������͸��ֻ�
			ArrayList<String> channelIdList = new ArrayList<String>();
			channelIdList.add(World.getWorld().getRingPhoneMap().get(ringId));
			new Push().pushToApp(channelIdList,jsonObject.toString());
		}
		else
		{
			System.out.println(ringId+"��ѯ URL�ȸ����ӣ�"+contents);
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

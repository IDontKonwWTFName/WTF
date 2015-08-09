package sepim.server.net.packet;

import java.util.ArrayList;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class TSHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents) {
		if(contents.length()>2){
			System.out.println(ringId+"�������������͵��ֻ�"+contents);
			String[] contentsStrings = contents.split(",",2);
			String[] parameterStrings = contentsStrings[1].split(";");
			//�汾��
			String verno = parameterStrings[0].split(":",2)[1];
			//�ֻ�ID
			String id = parameterStrings[1].split(":",2)[1];
			//email?
			String imei = parameterStrings[2].split(":",2)[1];
			//url��γ��
			String url = parameterStrings[3].split(":",2)[1];
			//�˿ں�
			String port = parameterStrings[4].split(":",2)[1];
			//���ĺ���
			String center = parameterStrings[5].split(":",2)[1];
			//�������ĺ���
			String slave = parameterStrings[6].split(":",2)[1];
			//sos1
			String sos1 = parameterStrings[7].split(":",2)[1];
			//sos2
			String sos2 = parameterStrings[8].split(":",2)[1];
			//sos3
			String sos3 = parameterStrings[9].split(":",2)[1];
			//�ϴ�ʱ��
			String upload = parameterStrings[10].split(":",2)[1];
			//����ʱ��
			String workMode = parameterStrings[11].split(":",2)[1];
			//bat����
			String batLevel = parameterStrings[12].split(":",2)[1];
			//����
			String language = parameterStrings[13].split(":",2)[1];
			//������Ϣ
			String zone = parameterStrings[14].split(":",2)[1];
			//��λ
			String gps = parameterStrings[15].split(":",2)[1];
			//���߷���ҵ��
			String GPRS = parameterStrings[16].split(":",2)[1];
			//led����
			String led = parameterStrings[17].split(":",2)[1];
			//����
			String pw = parameterStrings[18].split(":",2)[1];
			
			//�����ݷ�װ��json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("leixing",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			jsonObject.put("verno",verno);  
			jsonObject.put("id",id);
			jsonObject.put("imei",imei);
			jsonObject.put("url",url);
			jsonObject.put("port",port);
			jsonObject.put("center",center);
			jsonObject.put("slave",slave);
			jsonObject.put("sos1",sos1);
			jsonObject.put("sos2",sos2);
			jsonObject.put("sos3",sos3);
			jsonObject.put("upload",upload);
			jsonObject.put("workMode",workMode);
			jsonObject.put("batLevel",batLevel);
			jsonObject.put("language",language);
			jsonObject.put("zone",zone);
			jsonObject.put("gps",gps);
			jsonObject.put("GPRS",GPRS);
			jsonObject.put("led",led);
			jsonObject.put("pw",pw);
			//���������͸��ֻ�
			ArrayList<String> channelIdList = new ArrayList<String>();
			channelIdList.add(World.getWorld().getRingPhoneMap().get(ringId));
			new Push().pushToApp(channelIdList,jsonObject.toString());
		}else{
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}
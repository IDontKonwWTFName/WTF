package sepim.server.net.packet;

import java.util.ArrayList;

import com.a.push.Push;

import net.sf.json.JSONObject;
import sepim.server.clients.World;

public class TSHandler {

	public void handle(String leixing,String company, String ringId, String contentsLength,
			String contents,String userId) {
		if(userId.equals(""))//手机发出
		{
			System.out.println(ringId+"参数查询成功"+contents);
			String[] contentsStrings = contents.split(",",2);
			String[] parameterStrings = contentsStrings[1].split(";");
			//版本号
			String verno = parameterStrings[0].split(":",2)[1];
			//手环ID
			String id = parameterStrings[1].split(":",2)[1];
			//email?
			String imei = parameterStrings[2].split(":",2)[1];
			//url经纬度
			String url = parameterStrings[3].split(":",2)[1];
			//端口号
			String port = parameterStrings[4].split(":",2)[1];
			//中心号码
			String center = parameterStrings[5].split(":",2)[1];
			//辅助中心号码
			String slave = parameterStrings[6].split(":",2)[1];
			//sos1
			String sos1 = parameterStrings[7].split(":",2)[1];
			//sos2
			String sos2 = parameterStrings[8].split(":",2)[1];
			//sos3
			String sos3 = parameterStrings[9].split(":",2)[1];
			//上传时间
			String upload = parameterStrings[10].split(":",2)[1];
			//工作时间
			String workMode = parameterStrings[11].split(":",2)[1];
			//bat级别
			String batLevel = parameterStrings[12].split(":",2)[1];
			//语言
			String language = parameterStrings[13].split(":",2)[1];
			//区域信息
			String zone = parameterStrings[14].split(":",2)[1];
			//定位
			String gps = parameterStrings[15].split(":",2)[1];
			//无线分组业务
			String GPRS = parameterStrings[16].split(":",2)[1];
			//led开关
			String led = parameterStrings[17].split(":",2)[1];
			//密码
			String pw = parameterStrings[18].split(":",2)[1];
			
			//把数据封装进json
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type",leixing);  
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
			//把数据推送给手机
			ArrayList<String> channelIdList = new ArrayList<String>();
			channelIdList.add(World.getWorld().getRingPhoneMap().get(ringId));
			new Push().pushToApp(channelIdList,jsonObject.toString());
		}
		else
		{
			System.out.println(ringId+"参数查询"+contents);
			World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]");
		}
	}

}

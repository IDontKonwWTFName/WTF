package sepim.server.net.packet;

import java.util.Hashtable;

import net.sf.json.JSONObject;

import org.jboss.netty.channel.Channel;

import com.a.push.Push;

import sepim.server.clients.Client;
import sepim.server.clients.World;
import sepim.server.net.packet.handled.ChatPacketHandler;
import sepim.server.net.packet.handled.CommandPacketHandler;
import sepim.server.net.packet.handled.DefaultPacketHandler;
import sepim.server.net.packet.handled.PackHandler;


public class WADHandler {
	
 
public void handle(String leixing,String company, String ringId, String contentsLength, String contents) {
		String[] contentsStrings = contents.split(",");
		//日期
		String data = contentsStrings[1];
		//时间
		String time = contentsStrings[2];
		//是否定位
		String location = contentsStrings[3];
		//纬度
		String lat = contentsStrings[4];
		//纬度标识
		String latSign = contentsStrings[5];
		//经度
		String lng = contentsStrings[6];
		//经度标识
		String lngSign = contentsStrings[7];
		//速度
		String speed = contentsStrings[8];
		//方向
		String direction = contentsStrings[9];
		//海拔
		String altitude = contentsStrings[10];
		//卫星个数
		String satelliteNum = contentsStrings[11];
		//GSM信号强度
		String gsmSignalStr = contentsStrings[12];
		//电量
		String elecPower = contentsStrings[13];
		//记步数
		String stepsNum = contentsStrings[14];
		//翻滚次数
		String turning = contentsStrings[15];
		//终端状态
		String endingStatus = contentsStrings[16];
		//基站个数
		String baseStationNum = contentsStrings[17];
		//连接基站塔
		String conBaseStation = contentsStrings[18];
		//MCC国家码
		String mccCountryCode = contentsStrings[19];
		//MNC网号
		String mncNetNum = contentsStrings[20];
		
		//连接基站位置区域码
		String conBaseStationAreaCode = contentsStrings[21];
		//连接基站编号
		String conBaseStationNum = contentsStrings[22];
		//连接基站信号强度
		String conBaseStationSingalStr = contentsStrings[23];
		
		//附近基站1位置区域码
		String nearBaseStationAreaCode1 = contentsStrings[24];
		//附近基站1编号
		String nearBaseStationNum1 = contentsStrings[25];
		//附近基站1信号强度
		String nearBaseStationSingalStr1 = contentsStrings[26];

		//附近基站2位置区域码
		String nearBaseStationAreaCode2 = contentsStrings[27];
		//附近基站2编号
		String nearBaseStationNum2 = contentsStrings[28];
		//附近基站2信号强度
		String nearBaseStationSingalStr2 = contentsStrings[29];

		//附近基站3位置区域码
		String nearBaseStationAreaCode3 = contentsStrings[30];
		//附近基站3编号
		String nearBaseStationNum3 = contentsStrings[31];
		//附近基站3信号强度
		String nearBaseStationSingalStr3 = contentsStrings[32];
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("leixing",leixing);  
		jsonObject.put("shouhuan_id",ringId); 
		jsonObject.put("data",data); 
		jsonObject.put("time",time);  
		jsonObject.put("location",location);  
		jsonObject.put("lat",lat);  
		jsonObject.put("latSign",latSign);  
		jsonObject.put("lng",lng);  
		jsonObject.put("lngSign",lngSign);  
		jsonObject.put("speed",speed);  
		jsonObject.put("direction",direction);  
		jsonObject.put("altitude",altitude);  
		jsonObject.put("satelliteNum",satelliteNum);  
		jsonObject.put("gsmSignalStr",gsmSignalStr);  
		jsonObject.put("elecPower",elecPower);  
		jsonObject.put("stepsNum",stepsNum);  
		jsonObject.put("turning",turning);  
		jsonObject.put("endingStatus",endingStatus);  
		jsonObject.put("baseStationNum",baseStationNum);  
		jsonObject.put("conBaseStation",conBaseStation);  
		jsonObject.put("mccCountryCode",mccCountryCode);  
		jsonObject.put("mncNetNum",mncNetNum);  
		
		jsonObject.put("conBaseStationAreaCode",conBaseStationAreaCode);  
		jsonObject.put("conBaseStationNum",conBaseStationNum);  
		jsonObject.put("conBaseStationSingalStr",conBaseStationSingalStr);  
		
		jsonObject.put("nearBaseStationAreaCode1",nearBaseStationAreaCode1);  
		jsonObject.put("nearBaseStationNum1",nearBaseStationNum1);  
		jsonObject.put("nearBaseStationSingalStr1",nearBaseStationSingalStr1); 
		
		jsonObject.put("nearBaseStationAreaCode2",nearBaseStationAreaCode2);  
		jsonObject.put("nearBaseStationNum2",nearBaseStationNum2);  
		jsonObject.put("nearBaseStationSingalStr2",nearBaseStationSingalStr2); 
		
		jsonObject.put("nearBaseStationAreaCode3",nearBaseStationAreaCode3);  
		jsonObject.put("nearBaseStationNum3",nearBaseStationNum3);  
		jsonObject.put("nearBaseStationSingalStr3",nearBaseStationSingalStr3);  
		new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());
}

}

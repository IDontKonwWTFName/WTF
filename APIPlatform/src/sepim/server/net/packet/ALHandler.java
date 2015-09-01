package sepim.server.net.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import net.sf.json.JSONObject;

import com.a.push.Push;
import com.platform.model.Historylocation;
import com.platform.model.StepNumHistory;

import sepim.server.clients.World;
import sepim.server.clients.WriteSql;


public class ALHandler {
	
 
	public void handle(String leixing,String company, String ringId, String contentsLength, String contents) 
	{
		String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
		WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	报警信息上传");
		String[] contentsStrings = contents.split(",");
		if(contentsStrings.length>=0)
		{
			//时间
			Date nowTime = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMdd-HH:mm");
			String time = dateFormat.format(nowTime);

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
					
			//记步数存数据库
			
			SimpleDateFormat dateFormat1 = new SimpleDateFormat(
					"yyyy-MM-dd");
			String Time = dateFormat1.format(nowTime);
			SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction trans=session.beginTransaction();
			SQLQuery sqlQuery = session
					.createSQLQuery(
							"select * from dbo.[StepNumHistory] where shouhuan_id =:shouhuan_id and convert(varchar(10),time,23) =:time")
					.addEntity(StepNumHistory.class);
			sqlQuery.setString("shouhuan_id", ringId);
			sqlQuery.setString("time", Time);
			StepNumHistory stepNumHistory= (StepNumHistory) sqlQuery
					.uniqueResult();
			
			int NowStepNum = Integer.parseInt(stepsNum);//现在的记步数
			if(stepNumHistory!=null)//当天已有记步数了
			{
				int historyStepNum = stepNumHistory.getStepNum();//历史记步数
				
				//更新记步数
				SQLQuery sqlQueryChannelId = session.createSQLQuery("update dbo.[StepNumHistory] set stepNum=:stepnums where shouhuan_id=:shouhuan_id and convert(varchar(10),time,23) =:time");
				sqlQueryChannelId.setString("stepnums",(NowStepNum)+"");
				sqlQueryChannelId.setString("shouhuan_id",ringId);	
				sqlQueryChannelId.setString("time", Time);
				sqlQueryChannelId.executeUpdate();
				
				stepsNum = (NowStepNum)+"";//推送给手机的记步数
			}
			else
			{
				stepNumHistory = new StepNumHistory();
				stepNumHistory.setShouhuan_id(ringId);
				stepNumHistory.setStepNum(NowStepNum);
				stepNumHistory.setTime(nowTime);
				session.save(stepNumHistory);
			}
	    	trans.commit();
			session.close();
			sessionFactory.close();
			
			
			
			
			
			
			
			//翻滚次数
			String turning = contentsStrings[15];
			//终端状态
			@SuppressWarnings("unused")
			String endingStatus = contentsStrings[16];
			
			
			//存数据库并且推送
			
			
			if(contentsStrings.length>=18)
			{
				String requestdataString = "http://apilocate.amap.com/position?accesstype=0&imei=359615060121011&cdma=0";
				//基站个数
				String baseStationNum = null;
				baseStationNum = contentsStrings[17];
				int j=18;
				if(baseStationNum!=null)
				{
					int BaseStationNum = Integer.parseInt(baseStationNum);//附近基站个数
					if(BaseStationNum >0)
					{		
						//连接基站塔
						String conBaseStation = contentsStrings[j];
						j++;
						//MCC国家码
						String mccCountryCode = contentsStrings[j];
						j++;
						//MNC网号
						String mncNetNum = contentsStrings[j];
						j++;
						
						String[] conBaseStationAreaCode = new String[BaseStationNum];
						String[] conBaseStationNum = new String[BaseStationNum];
						String[] conBaseStationSingalStr = new String[BaseStationNum];
						
						String nearbts="";
						for(int i=0;i<BaseStationNum;i++)
						{
							//连接基站位置区域码
							conBaseStationAreaCode[i] = contentsStrings[j];
							j++;
							//连接基站编号
							conBaseStationNum[i] = contentsStrings[j];
							j++;
							//连接基站信号强度
							conBaseStationSingalStr[i] = ""+((Integer.parseInt(contentsStrings[j])-110)*2-113);
							j++;
							if(i==0)//这里是连接的基站信息
							{
								requestdataString+="&bts="+mccCountryCode+","+mncNetNum+","+conBaseStationAreaCode[i]+","+conBaseStationNum[i]+","+conBaseStationSingalStr[i];
							}
							else if(i!=BaseStationNum-1)//附近基站信息
							{
								nearbts+=mccCountryCode+","+mncNetNum+","+conBaseStationAreaCode[i]+","+conBaseStationNum[i]+","+conBaseStationSingalStr[i]+"|";
							}
							else
							{
								nearbts+=mccCountryCode+","+mncNetNum+","+conBaseStationAreaCode[i]+","+conBaseStationNum[i]+","+conBaseStationSingalStr[i];
							}
						}
						if(!nearbts.equals(""))//有附近基站信息
						{
							requestdataString+="&nearbts="+nearbts;
						}
					}
					if(contentsStrings.length>j)
					{
						String WifiNum = contentsStrings[j];
						j++;
						int wifiNum = Integer.parseInt(WifiNum);
						if(wifiNum>0)//附近有wifi
						{
							requestdataString+="&macs=";
							String[] WifiName = new String[wifiNum];
							String[] WifiMac = new String[wifiNum];
							String[] WifiSingalStr = new String[wifiNum];
							for(int i=0;i<wifiNum;i++)
							{
								WifiName[i] = contentsStrings[j];
								j++;
								WifiMac[i] = contentsStrings[j];
								j++;
								WifiSingalStr[i] = contentsStrings[j];
								j++;
								if(i!=wifiNum-1)
								{
									requestdataString+=WifiMac[i]+","+WifiSingalStr[i]+","+WifiName[i]+"|";
								}
								else
								{
									requestdataString+=WifiMac[i]+","+WifiSingalStr[i]+","+WifiName[i];
								}
							}
						}	
					}
					requestdataString+="&output=json&key=dca5c01debed7dc9e177b8c289dfbfe7";
					if(((int)Double.parseDouble(lat))==0&&((int)Double.parseDouble(lng))==0)//GPS无信号
					{
						String result = "";
					    BufferedReader in = null;
						try 
						{
							URL realUrl = new URL(requestdataString);
						     // 打开和URL之间的连接
				            try 
				            {
								URLConnection connection = realUrl.openConnection();
								  // 设置通用的请求属性
					            connection.setRequestProperty("accept", "*/*");
					            connection.setRequestProperty("connection", "Keep-Alive");
					            connection.setRequestProperty("user-agent",
					                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
					            // 建立实际的连接
					            connection.connect();
					            // 获取所有响应头字段
					            Map<String, List<String>> map = connection.getHeaderFields();
					            // 遍历所有的响应头字段
					            for (String key : map.keySet()) 
					            {
					            }
					            // 定义 BufferedReader输入流来读取URL的响应
					            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
					            String line;
					            while ((line = in.readLine()) != null) 
					            {
					                result += line;
					            }
					            System.out.println(result);
							} 
				            catch (IOException e)
				            {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						catch (MalformedURLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 使用finally块来关闭输入流
				        finally 
				        {
				            try
				            {
				                if (in != null)
				                {
				                    in.close();
				                }
				            } 
				            catch (Exception e2)
				            {
				                e2.printStackTrace();
				            }
				        }
						@SuppressWarnings("unused")
						JSONObject jsonObject =JSONObject.fromObject(result);
						if(result.split("\"location\":\"").length==2)
						{
							WriteSql.getWritesql().WriteIntoRingInfo(ringId, result);
							String temp=result.split("\"location\":\"")[1];
							String temps=temp.split("\",",2)[0];
							lng=temps.split(",")[0];
							lat=temps.split(",")[1];
						}
					}
					else//GPS定位 
					{
						String url ="http://restapi.amap.com/v3/geocode/regeo?location="+lng+","+lat+"&extensions=base&output=json&key=dca5c01debed7dc9e177b8c289dfbfe7";
						String result = "";
					    BufferedReader in = null;
						try 
						{
							URL realUrl = new URL(url);
						     // 打开和URL之间的连接
				            try 
				            {
								URLConnection connection = realUrl.openConnection();
								  // 设置通用的请求属性
					            connection.setRequestProperty("accept", "*/*");
					            connection.setRequestProperty("connection", "Keep-Alive");
					            connection.setRequestProperty("user-agent",
					                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
					            // 建立实际的连接
					            connection.connect();
					            // 获取所有响应头字段
					            Map<String, List<String>> map = connection.getHeaderFields();
					            // 遍历所有的响应头字段
					            for (String key : map.keySet()) 
					            {
					            }
					            // 定义 BufferedReader输入流来读取URL的响应
					            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
					            String line;
					            while ((line = in.readLine()) != null) 
					            {
					                result += line;
					            }
					            System.out.println(result);
							} 
				            catch (IOException e)
				            {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						catch (MalformedURLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 使用finally块来关闭输入流
				        finally 
				        {
				            try
				            {
				                if (in != null)
				                {
				                    in.close();
				                }
				            } 
				            catch (Exception e2)
				            {
				                e2.printStackTrace();
				            }
				        }
						@SuppressWarnings("unused")
						JSONObject jsonObject =JSONObject.fromObject(result);
						if(result.split("\"location\":\"").length==2)
						{
							WriteSql.getWritesql().WriteIntoRingInfo(ringId, result);
							String temp=result.split("\"location\":\"")[1];
							String temps=temp.split("\",",2)[0];
							lng=temps.split(",")[0];
							lat=temps.split(",")[1];
						}
					
					}
				}
			}
			JSONObject jsonObject = new JSONObject();
			
			/*
			 * by lj 8.29 ?????
			 */
			if (location.equals("A")){
				jsonObject.put("mode", "gps");
					
			}if (location.equals("V")) {
				jsonObject.put("mode", "mix");
			}
			
			
			jsonObject.put("type",leixing);  
			jsonObject.put("shouhuan_id",ringId); 
			jsonObject.put("time",time);  
			jsonObject.put("location",location);  
			jsonObject.put("lat",lat);  
			jsonObject.put("latSign",latSign);  
			jsonObject.put("lng",lng);  
			jsonObject.put("lngSign",lngSign);  
			//jsonObject.put("speed",speed);  
			//jsonObject.put("direction",direction);  
			//jsonObject.put("altitude",altitude);  
			//jsonObject.put("satelliteNum",satelliteNum);  
			jsonObject.put("gsmSignalStr",gsmSignalStr);  
			jsonObject.put("elecPower",elecPower);  
			jsonObject.put("stepsNum",stepsNum);  
			//jsonObject.put("turning",turning);  
			//jsonObject.put("endingStatus",endingStatus);  
			//jsonObject.put("baseStationNum",baseStationNum);  
			//jsonObject.put("conBaseStation",conBaseStation);  
			//jsonObject.put("mccCountryCode",mccCountryCode);  
			//jsonObject.put("mncNetNum",mncNetNum);  
			
	//		jsonObject.put("conBaseStationAreaCode",conBaseStationAreaCode);  
	//		jsonObject.put("conBaseStationNum",conBaseStationNum);  
	//		jsonObject.put("conBaseStationSingalStr",conBaseStationSingalStr);  
	//		
	//		jsonObject.put("nearBaseStationAreaCode1",nearBaseStationAreaCode1);  
	//		jsonObject.put("nearBaseStationNum1",nearBaseStationNum1);  
	//		jsonObject.put("nearBaseStationSingalStr1",nearBaseStationSingalStr1); 
	//		
	//		jsonObject.put("nearBaseStationAreaCode2",nearBaseStationAreaCode2);  
	//		jsonObject.put("nearBaseStationNum2",nearBaseStationNum2);  
	//		jsonObject.put("nearBaseStationSingalStr2",nearBaseStationSingalStr2); 
	//		
	//		jsonObject.put("nearBaseStationAreaCode3",nearBaseStationAreaCode3);  
	//		jsonObject.put("nearBaseStationNum3",nearBaseStationNum3);  
	//		jsonObject.put("nearBaseStationSingalStr3",nearBaseStationSingalStr3);  
			
			if(((int)Double.parseDouble(lat))!=0&&((int)Double.parseDouble(lng))!=0)
			{
				//写入数据库
				Date now = new Date();
			    SessionFactory sessionFactory1 =new Configuration().configure().buildSessionFactory();
		        Session session1 =sessionFactory1.openSession();
		        Transaction transaction=session1.beginTransaction();
				
				Historylocation hl = new Historylocation();
				hl.setShouhuan_id(ringId);
				hl.setTime(now);
		
				hl.setLat(Double.parseDouble(lat));
				hl.setLng(Double.parseDouble(lng));
				session1.save(hl);
				transaction.commit();
				
				session1.close();
				sessionFactory1.close();
				
				new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),jsonObject.toString());

			}
		}
		else 
		{
			//System.out.println("位置数据格式错误");
		}
	}
}
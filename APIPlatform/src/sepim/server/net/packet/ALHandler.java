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
		WriteSql.getWritesql().WriteIntoRingInfo(ringId, command+"	������Ϣ�ϴ�");
		String[] contentsStrings = contents.split(",");
		if(contentsStrings.length>=0)
		{
			//ʱ��
			Date nowTime = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMdd-HH:mm");
			String time = dateFormat.format(nowTime);

			//�Ƿ�λ
			String location = contentsStrings[3];
			//γ��
			String lat = contentsStrings[4];
			//γ�ȱ�ʶ
			String latSign = contentsStrings[5];
			//����
			String lng = contentsStrings[6];
			//���ȱ�ʶ
			String lngSign = contentsStrings[7];
			//�ٶ�
			String speed = contentsStrings[8];
			//����
			String direction = contentsStrings[9];
			//����
			String altitude = contentsStrings[10];
			//���Ǹ���
			String satelliteNum = contentsStrings[11];
			//GSM�ź�ǿ��
			String gsmSignalStr = contentsStrings[12];
			//����
			String elecPower = contentsStrings[13];
			//�ǲ���
			String stepsNum = contentsStrings[14];
					
			//�ǲ��������ݿ�
			
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
			
			int NowStepNum = Integer.parseInt(stepsNum);//���ڵļǲ���
			if(stepNumHistory!=null)//�������мǲ�����
			{
				int historyStepNum = stepNumHistory.getStepNum();//��ʷ�ǲ���
				
				//���¼ǲ���
				SQLQuery sqlQueryChannelId = session.createSQLQuery("update dbo.[StepNumHistory] set stepNum=:stepnums where shouhuan_id=:shouhuan_id and convert(varchar(10),time,23) =:time");
				sqlQueryChannelId.setString("stepnums",(NowStepNum)+"");
				sqlQueryChannelId.setString("shouhuan_id",ringId);	
				sqlQueryChannelId.setString("time", Time);
				sqlQueryChannelId.executeUpdate();
				
				stepsNum = (NowStepNum)+"";//���͸��ֻ��ļǲ���
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
			
			
			
			
			
			
			
			//��������
			String turning = contentsStrings[15];
			//�ն�״̬
			@SuppressWarnings("unused")
			String endingStatus = contentsStrings[16];
			
			
			//�����ݿⲢ������
			
			
			if(contentsStrings.length>=18)
			{
				String requestdataString = "http://apilocate.amap.com/position?accesstype=0&imei=359615060121011&cdma=0";
				//��վ����
				String baseStationNum = null;
				baseStationNum = contentsStrings[17];
				int j=18;
				if(baseStationNum!=null)
				{
					int BaseStationNum = Integer.parseInt(baseStationNum);//������վ����
					if(BaseStationNum >0)
					{		
						//���ӻ�վ��
						String conBaseStation = contentsStrings[j];
						j++;
						//MCC������
						String mccCountryCode = contentsStrings[j];
						j++;
						//MNC����
						String mncNetNum = contentsStrings[j];
						j++;
						
						String[] conBaseStationAreaCode = new String[BaseStationNum];
						String[] conBaseStationNum = new String[BaseStationNum];
						String[] conBaseStationSingalStr = new String[BaseStationNum];
						
						String nearbts="";
						for(int i=0;i<BaseStationNum;i++)
						{
							//���ӻ�վλ��������
							conBaseStationAreaCode[i] = contentsStrings[j];
							j++;
							//���ӻ�վ���
							conBaseStationNum[i] = contentsStrings[j];
							j++;
							//���ӻ�վ�ź�ǿ��
							conBaseStationSingalStr[i] = ""+((Integer.parseInt(contentsStrings[j])-110)*2-113);
							j++;
							if(i==0)//���������ӵĻ�վ��Ϣ
							{
								requestdataString+="&bts="+mccCountryCode+","+mncNetNum+","+conBaseStationAreaCode[i]+","+conBaseStationNum[i]+","+conBaseStationSingalStr[i];
							}
							else if(i!=BaseStationNum-1)//������վ��Ϣ
							{
								nearbts+=mccCountryCode+","+mncNetNum+","+conBaseStationAreaCode[i]+","+conBaseStationNum[i]+","+conBaseStationSingalStr[i]+"|";
							}
							else
							{
								nearbts+=mccCountryCode+","+mncNetNum+","+conBaseStationAreaCode[i]+","+conBaseStationNum[i]+","+conBaseStationSingalStr[i];
							}
						}
						if(!nearbts.equals(""))//�и�����վ��Ϣ
						{
							requestdataString+="&nearbts="+nearbts;
						}
					}
					if(contentsStrings.length>j)
					{
						String WifiNum = contentsStrings[j];
						j++;
						int wifiNum = Integer.parseInt(WifiNum);
						if(wifiNum>0)//������wifi
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
					if(((int)Double.parseDouble(lat))==0&&((int)Double.parseDouble(lng))==0)//GPS���ź�
					{
						String result = "";
					    BufferedReader in = null;
						try 
						{
							URL realUrl = new URL(requestdataString);
						     // �򿪺�URL֮�������
				            try 
				            {
								URLConnection connection = realUrl.openConnection();
								  // ����ͨ�õ���������
					            connection.setRequestProperty("accept", "*/*");
					            connection.setRequestProperty("connection", "Keep-Alive");
					            connection.setRequestProperty("user-agent",
					                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
					            // ����ʵ�ʵ�����
					            connection.connect();
					            // ��ȡ������Ӧͷ�ֶ�
					            Map<String, List<String>> map = connection.getHeaderFields();
					            // �������е���Ӧͷ�ֶ�
					            for (String key : map.keySet()) 
					            {
					            }
					            // ���� BufferedReader����������ȡURL����Ӧ
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
						// ʹ��finally�����ر�������
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
					else//GPS��λ 
					{
						String url ="http://restapi.amap.com/v3/geocode/regeo?location="+lng+","+lat+"&extensions=base&output=json&key=dca5c01debed7dc9e177b8c289dfbfe7";
						String result = "";
					    BufferedReader in = null;
						try 
						{
							URL realUrl = new URL(url);
						     // �򿪺�URL֮�������
				            try 
				            {
								URLConnection connection = realUrl.openConnection();
								  // ����ͨ�õ���������
					            connection.setRequestProperty("accept", "*/*");
					            connection.setRequestProperty("connection", "Keep-Alive");
					            connection.setRequestProperty("user-agent",
					                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
					            // ����ʵ�ʵ�����
					            connection.connect();
					            // ��ȡ������Ӧͷ�ֶ�
					            Map<String, List<String>> map = connection.getHeaderFields();
					            // �������е���Ӧͷ�ֶ�
					            for (String key : map.keySet()) 
					            {
					            }
					            // ���� BufferedReader����������ȡURL����Ӧ
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
						// ʹ��finally�����ر�������
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
				//д�����ݿ�
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
			//System.out.println("λ�����ݸ�ʽ����");
		}
	}
}
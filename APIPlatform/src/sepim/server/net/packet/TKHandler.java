package sepim.server.net.packet;
// app and ring talk with each other
// 手环和手机对讲 功能

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.a.push.Push;
import com.platform.model.Historyrecord;

import sepim.server.clients.World;
import sepim.server.clients.WriteSql;

/**
 * Handler implementation for the echo server.
 */
public class TKHandler{

	private BufferedOutputStream bufferedOutputStream;
	
	public void channelActive(ChannelHandlerContext ctx) throws Exception 
	{
		
    }
	//need  coding 
	public String handle(String leixing,String company, String ringId, String contentsLength,byte[] contents,String userId,String comdand) 
	{
		if(comdand.equals("TK,1"))
		{
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command);
			System.out.println("手环接受成功！");
			return null;
		}
		else if(comdand.equals("TK,0")) 
		{
			String command = "["+company+"*"+ringId+"*"+contentsLength+"*"+contents+"]";
			WriteSql.getWritesql().WriteIntoRingInfo(ringId, command);
			System.out.println("手环接受失败！");
			return null;
		}
		else
		{
			String realPath = "D:/data/HistoryRecord/"+ringId;
			// 创建文件夹
			File dir = new File(realPath);
			if (!dir.exists()) 
			{
				dir.mkdir();
			}
			
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy_MM_dd_HH_mm_ss");
			String time = dateFormat.format(now);
			
			
			String filename = realPath+"/"+ringId+time+".amr" ;
			if(userId.equals(""))
			{
				WriteSql.getWritesql().WriteIntoRingInfo(ringId, "手环发送语音");
				System.out.println("服务器收到手环发送的音频");
				File voicefile = new File(filename);
				
				byte temp = 0; 
			
				try 
				{
					FileOutputStream file = new FileOutputStream(voicefile);
					int length = contents.length-1;
					for(int i=23;i<length;i++)
					{					
						if(contents[i]==125)
						{
							if(i!=length-1)
							{
								if(contents[i+1]==1)
								{
									file.write(contents[i]);
									i++;
								}
								else if(contents[i+1]==2)
								{
									temp = 91;
									file.write(temp);
									i++;
								}
								else if(contents[i+1]==3)
								{
									temp = 93;
									file.write(temp);
									i++;
								}
								else if(contents[i+1]==4)
								{
									temp = 44;
									file.write(temp);
									i++;
								}
								else if(contents[i+1]==5)
								{
									temp = 42;
									file.write(temp);
									i++;
								}
								else
								{
									file.write(contents[i]);
								}
							}
							else
							{
								file.write(contents[i]);
							}
						}
						else
						{
							file.write(contents[i]);
						}
					}
					file.flush();				   
					file.close();
					
					SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
			        Session session =sessionFactory.openSession();
			        Transaction transaction=session.beginTransaction();
					
					Historyrecord hr = new Historyrecord();
					hr.setShouhuan_id(ringId);
					hr.setFrom_id(ringId);
					hr.setTime(now);
					hr.setRecord_url(ringId+time+".amr");
					hr.setFrom_type(false);
					hr.setIsHeard(false);
					session.save(hr);
					transaction.commit();
					
					JSONObject data = new JSONObject();
					data.put("sign","record");  
					data.put("url",ringId+time+".amr"); 
					data.put("from_id",ringId);  
					now = new Date();
					dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					time = dateFormat.format(now);
					data.put("time",time);  
					data.put("from_type",false);  
					data.put("isheard",false);  
					JSONObject json = new JSONObject();
					json.put("shouhuan_id",ringId );
					json.put("data",data );
					json.put("type","record" );	
					new Push().pushToApp(World.getWorld().getRingPhoneListMap().get(ringId),json.toString());
					
					World.getWorld().WriteMessageToRing(ringId,"["+company+"*"+ringId+"*0004*TK,1]");
				} 
				catch (FileNotFoundException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("手机发送音频"+comdand.split(",")[1]);
				// file name
				filename= comdand.split(",")[1];
 
				byte[] filetxt = null;
				try 
				{
					filetxt = dataInputStream(filename);
	 
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String dataLength = To16(Integer.toHexString(filetxt.length+3)); 
				String temp = "["+company+"*"+ringId+"*"+dataLength+"*TK,";
				byte[] data = combineByteArray(temp.getBytes(),filetxt);
				data = combineByteArray(data,"]".getBytes());
				World.getWorld().WriteMessageToRing(ringId,data); 
			}
			return filename;
		}
	}
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
	}
	
    public void channelReadComplete(ChannelHandlerContext ctx) {
		//ctx.flush();
	}
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        //ctx.close();
    }
	//转成四位16进制的数
	public static String To16(String in)
	{
		int num=in.length();
		for(int i=0;i<4-num;i++)
			in="0"+in;
		return in;
	}
	public static byte[] dataInputStream(String filepath) throws IOException 
	{
		File file = new File(filepath);
		DataInputStream dps = new DataInputStream(new FileInputStream(file));
		int length = (int) file.length();
		System.out.println("初始文件的长度："+length);
		byte[] temp = new byte[70000];
		byte b = 0;
		int temp_length = 0;
		for(int i=0;i<length;i++)
		{ 
			b=dps.readByte();
			if(b==125)
			{
				temp[temp_length]=b;
				b = 1;
				temp_length++;
				temp[temp_length]=b;
			}
			else if(b==91)
			{
				temp[temp_length]=125;
				b = 2;
				temp_length++;
				temp[temp_length]=b;
			}
	       	else if(b==93)
	    	{
	       		temp[temp_length]=125;
	    		b = 3;
	    		temp_length++;
	    		temp[temp_length]=b;
	    	}
	      	else if(b==44)
	    	{
	      		temp[temp_length]=125;
	    		b = 4;
	    		temp_length++;
	    		temp[temp_length]=b;
	    	}
	      	else if(b==42)
	    	{
	      		temp[temp_length]=125;
	    		b = 5;
	    		temp_length++;
	    		temp[temp_length]=b;
	    	}
	      	else {
	      		temp[temp_length]=b;
			}
	    	temp_length++;
	    }
	    dps.close();
	    byte[] message = new byte[temp_length];
	    System.arraycopy(temp, 0, message, 0, temp_length);  
    	return message;
    }   
    private static byte[] combineByteArray(byte[] array1, byte[] array2) 
	{  
        byte[] combined = new byte[array1.length + array2.length];  
        System.arraycopy(array1, 0, combined, 0, array1.length);  
        System.arraycopy(array2, 0, combined, array1.length, array2.length);  
        return combined;  
	}  

}

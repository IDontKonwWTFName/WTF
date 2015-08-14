package sepim.server.net.packet;
// app and ring talk with each other
// 手环和手机对讲 功能

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.platform.model.Historyrecord;

import sepim.server.clients.World;

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
			System.out.println("手环接受成功！");
			return null;
		}
		else if(comdand.equals("TK,0")) 
		{
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
				System.out.println("手环接受成功");
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
}

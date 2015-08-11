package sepim.server.net.packet;
// app and ring talk with each other
// 手环和手机对讲 功能

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

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
			String test_temp="";
			for(byte b:contents)
			{
				test_temp+= b +" ";
			}
			System.out.println("test_temp"+test_temp);
			
			String filename = "E:\\temp.amr" ;
			if(userId.equals(""))
			{
				System.out.println("服务器收到手环发送的音频");
				File voicefile = new File(filename);
				
				StringBuilder hexData = new StringBuilder();//存成16进制
				
				byte temp = 0; 
				String str = "";
				
				byte temp1=125;
				try 
				{
					FileOutputStream file = new FileOutputStream(voicefile);
					for(int i=23;i<contents.length-1;i++)
					{					
	//					if(contents[i]==125)
	//					{
	//						file.write(temp1);
	//						temp=1;
	//						file.write(temp);
	//					}
	//					else if(contents[i]==91)
	//					{
	//						file.write(temp1);
	//						temp=2;
	//						file.write(temp);
	//					}
	//					else if(contents[i]==93)
	//					{
	//						file.write(temp1);
	//						temp=3;
	//						file.write(temp);
	//					}
	//					else if(contents[i]==44)
	//					{
	//						file.write(temp1);
	//						temp=4;
	//						file.write(temp);
	//					}
	//					else if(contents[i]==42)
	//					{
	//						file.write(temp1);
	//						temp=5;
	//						file.write(temp);
	//					}
	//					else
	//					{
							file.write(contents[i]);
	//					}
					}
					file.flush();
					file.close();
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

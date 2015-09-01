package sepim.server.net.packet;
// app and ring talk with each other
// 手环和手机对讲 功能

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.enterprise.inject.New;

import org.apache.commons.collections.functors.IfClosure;
import org.jboss.netty.channel.ChannelHandlerContext;

import sepim.server.clients.World;

/**
 * Handler implementation for the echo server.
 */
public class TKQHandler{
	//暂未使用
	private BufferedOutputStream bufferedOutputStream;
	
	public void channelActive(ChannelHandlerContext ctx) throws Exception 
	{
		
    }
	//need  coding 
	public String handle(String leixing,String company, String ringId, String contentsLength,byte[] contents) 
	{
		 // file name
		 String filename= "D:\\temp.amr";
	     
//	     byte[] filetxt = null;
//	     try 
//	     {
//	    	 filetxt = dataInputStream(filename);
//	    	 System.out.println(filetxt.length);
//	    	 
//	     } catch (IOException e) {
//	    	 // TODO Auto-generated catch block
//	    	 e.printStackTrace();
//	     }
//	     String dataLength = To16(Integer.toHexString(filetxt.length+3)); 
//	     String temp = "["+company+"*"+ringId+"*"+dataLength+"*TK,";
//	     byte[] data = combineByteArray(temp.getBytes(),filetxt);
//	     data = combineByteArray(data,"]".getBytes());
//	     System.out.println(new String(data));
//	     World.getWorld().WriteMessageToRing(ringId,data); 
		 return filename;
	}
	//转成四位16进制的数
	public static String To16(String in)
	{
		int num=in.length();
		for(int i=0;i<4-num;i++)
			in="0"+in;
		return in;
	}
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		
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

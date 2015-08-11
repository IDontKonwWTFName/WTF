package sepim.server.net.packet;
// app and ring talk with each other
// 手环和手机对讲 功能

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jboss.netty.channel.ChannelHandlerContext;

import sepim.server.clients.World;

/**
 * Handler implementation for the echo server.
 */
public class TKQHandler{

	private BufferedOutputStream bufferedOutputStream;
	
	public void channelActive(ChannelHandlerContext ctx) throws Exception 
	{
		
    }
	//need  coding 
	public String handle(String leixing,String company, String ringId, String contentsLength,byte[] contents) 
	{
		 // file name
		 String filename= "E:\\temp.amr";
	     
	     String filetxt = null;
	     try 
	     {
	    	 filetxt = dataInputStream(filename);
	     } catch (IOException e) {
	    	 // TODO Auto-generated catch block
	    	 e.printStackTrace();
	     }
	     String dataLength = To16(Integer.toHexString(filetxt.length()+3)); 
	     
	     String data = "[3G*1506012101*"+dataLength+"*TK,"+filetxt+"]";
	     System.out.println(data);
	     World.getWorld().WriteMessageToRing(ringId,data);
	     
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
    
    public static String dataInputStream(String filepath) throws IOException {
	    File file = new File(filepath);
	    DataInputStream dps = new DataInputStream(new FileInputStream(file));
	    StringBuilder hexData = new StringBuilder();
	    byte bt = 0;
	
	    for(int i=0;i<file.length();i++) { 
	
	    	bt = dps.readByte(); // 以十六进制的无符号整数形式返回一个字符串表示形式。
	    	String str = Integer.toHexString(bt);
	
	        if(str.length() == 8) { //去掉补位的f
	
	        str = str.substring(6);
	
	    }
	
	    if(str.length() == 1) 
	    {
	    	str = "0X0"+str;
	    }	
	    else
	    {
	    	str = "0X"+str;
	    }
	    hexData.append(str.toUpperCase());
	    }

    	return hexData.toString();
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

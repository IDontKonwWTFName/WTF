package sipim.server.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import sepim.server.clients.World;

public class SocketConnectNetty extends Thread{
	
	private String message;
	
	public Socket clientsocket=null;
	
	private Socket s;
	
	private String userId;
	
	public SocketConnectNetty(){
		
	}
	
	public SocketConnectNetty(String userId,String message,Socket soc)
	{
		this.userId = userId;
		this.message = message;
		this.clientsocket=soc;
	}
	

	
	public void run()
	{
//		while(true){
			try{
				OutputStream Os =clientsocket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(Os);
				dos.writeUTF(message);	
				dos.flush();
	//			InputStream is=clientsocket.getInputStream();
	//			DataInputStream dis = new DataInputStream(is);
	//			byte []buf=new byte[100];
	//			int len=dis.read(buf);
	//			System.out.println("Receive from server  "+new String(buf,0,len));
				dos.close();
			}
			catch(Exception e)
			{
				
			}
		}
//	}
	
	public void connect(String userId,String message){
		try{
//			//去除中括号
//			message = message.substring(1,message.length()-1);
			//以*为标记分割字符串
			String[] receiveCommandStrings = message.split("\\*");
			//公司名
			String company = "";
			//手环ID
			String ringId = "";
			//内容长度
			String contentsLength ="";
			//内容
			String contents = "";
			//判断手环发送信息是否符合要求
			if(receiveCommandStrings.length==4){
			//赋值
			company = receiveCommandStrings[0];
			ringId = receiveCommandStrings[1];
			contentsLength = receiveCommandStrings[2];
			contents = receiveCommandStrings[3];
			}else{
//				ctx.getChannel().write("commandError!!!");
			}
			System.out.println("当前RingId"+ringId);
			InetAddress ipadd = InetAddress.getByName("127.0.0.1"); 
			s=new Socket(ipadd,7220);
			SocketConnectNetty task = new SocketConnectNetty(userId,message,s);
			task.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}

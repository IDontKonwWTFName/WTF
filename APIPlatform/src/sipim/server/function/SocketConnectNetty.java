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
//			//ȥ��������
//			message = message.substring(1,message.length()-1);
			//��*Ϊ��Ƿָ��ַ���
			String[] receiveCommandStrings = message.split("\\*");
			//��˾��
			String company = "";
			//�ֻ�ID
			String ringId = "";
			//���ݳ���
			String contentsLength ="";
			//����
			String contents = "";
			//�ж��ֻ�������Ϣ�Ƿ����Ҫ��
			if(receiveCommandStrings.length==4){
			//��ֵ
			company = receiveCommandStrings[0];
			ringId = receiveCommandStrings[1];
			contentsLength = receiveCommandStrings[2];
			contents = receiveCommandStrings[3];
			}else{
//				ctx.getChannel().write("commandError!!!");
			}
			System.out.println("��ǰRingId"+ringId);
			InetAddress ipadd = InetAddress.getByName("218.9.122.126"); 
			s=new Socket(ipadd,7220);
			SocketConnectNetty task = new SocketConnectNetty(userId,message,s);
			task.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
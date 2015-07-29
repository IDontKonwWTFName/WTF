package sepim.server;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;



public class TestFunction extends Thread {
    private static int ID=0;
    public Socket clientsocket=null;
	public TestFunction(int id,Socket soc)
	{
		this.ID=id;
		this.clientsocket=soc;
	}
	

	
	public void run() {
		try {
			StringBuffer sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			sb.delete(0,sb.length()); 
			sb.append(br.readLine());
			OutputStream Os =clientsocket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(Os);
			String ringId = sb.toString();
			dos.writeUTF("[SG*"+ringId+"*0002*LK]");
			dos.flush();
			while (true) {
				InputStream is = clientsocket.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				byte[] buf = new byte[100];
				int len = dis.read(buf);
				String receiveMessageFromServer = new String(buf, 4, len-4);
				System.out.println("Receive from server"+receiveMessageFromServer);
				if(receiveMessageFromServer.contains("UPLOAD")){
					dos.writeUTF("[SG*"+ringId+"*0006*UPLOAD]");
				}
				if(receiveMessageFromServer.contains("CENTER")){
					dos.writeUTF("[SG*"+ringId+"*0006*CENTER]");
				}
				if(receiveMessageFromServer.contains("SLAVE")){
					dos.writeUTF("[SG*"+ringId+"*0005*SLAVE]");
				}
				if(receiveMessageFromServer.contains("PW")){
					dos.writeUTF("[SG*"+ringId+"*0002*PW]");
				}
				if(receiveMessageFromServer.contains("CALL")){
					dos.writeUTF("[SG*"+ringId+"*0004*CALL]");
				}
				if(receiveMessageFromServer.contains("MONITOR")){
					dos.writeUTF("[SG*"+ringId+"*0007*MONITOR]");
				}
				if(receiveMessageFromServer.contains("SOS1,")){
					dos.writeUTF("[SG*"+ringId+"*0004*SOS1]");
				}
				if(receiveMessageFromServer.contains("SOS2,")){
					dos.writeUTF("[SG*"+ringId+"*0004*SOS2]");
				}
				if(receiveMessageFromServer.contains("SOS3,")){
					dos.writeUTF("[SG*"+ringId+"*0004*SOS3]");
				}
				if(receiveMessageFromServer.contains("SOS,")){
					dos.writeUTF("[SG*"+ringId+"*0003*SOS]");
				}
				if(receiveMessageFromServer.contains("UPGRADE")){
					dos.writeUTF("[SG*"+ringId+"*0007*UPGRADE]");
				}
				if(receiveMessageFromServer.contains("IP")){
					break;
				}
				if(receiveMessageFromServer.contains("FACTORY")){
					dos.writeUTF("[SG*"+ringId+"*0007*FACTORY]");
				}
				if(receiveMessageFromServer.contains("LZ")){
					dos.writeUTF("[SG*"+ringId+"*0002*LZ]");
				}
				if(receiveMessageFromServer.contains("URL")){
					dos.writeUTF("[SG*"+ringId+"*006B*URL,url:http://maps.google.com.hk/maps?q=N22.571695,E113.861404,Locate date:2014-4-23,Locate time:18:16:59]");
				}
				if(receiveMessageFromServer.contains("SOSSMS")){
					dos.writeUTF("[SG*"+ringId+"*0006*SOSSMS]");
				}
				if(receiveMessageFromServer.contains("LOWBAT")){
					dos.writeUTF("[SG*"+ringId+"*0006*LOWBAT]");
				}
				if(receiveMessageFromServer.contains("APN")){
					dos.writeUTF("[SG*"+ringId+"*0003*APN]");
				}
				if(receiveMessageFromServer.contains("ANY")){
					dos.writeUTF("[SG*"+ringId+"*0003*ANY]");
				}
				if(receiveMessageFromServer.contains("TS")){
					dos.writeUTF("[SG*"+ringId+"*00FC*TS,ver:G29_BASE_V1.00_2014.04.24_09.47.23;"+
					"ID:SG*5678901234;"+
					"imei:1234SG*56789012345;"+
					"url:113.81.229.9;"+
					"port:5900;"+
					"center:;"+
					"slave:;"+
					"sos1:;"+
					"sos2:;"+
					"sos3:;"+
					"upload:30S;"+
					"work mode:1;"+
					"bat level:3;"+
					"language:1;"+
					"zone:8.00;"+
					"GPS:NO(0);"+
					"GPRS:OK(89);"+
					"LED:OFF;"+
					"pw:123456;]");
				}
				if(receiveMessageFromServer.contains("VERNO")){
					dos.writeUTF("[SG*"+ringId+"*0028*VERNO,G29_BASE_V1.00_2014.04.23_17.46.49]");
				}
				if(receiveMessageFromServer.contains("RESET")){
					dos.writeUTF("[SG*"+ringId+"*0005*RESET]");
				}
				if(receiveMessageFromServer.contains("CR")){
					dos.writeUTF("[SG*"+ringId+"*0002*CR]");
				}
				if(receiveMessageFromServer.contains("BT")){
					dos.writeUTF("[SG*"+ringId+"*0002*BT]");
				}
				if(receiveMessageFromServer.contains("WORK")){
					dos.writeUTF("[SG*"+ringId+"*0004*WORK]");
				}
				if(receiveMessageFromServer.contains("WORKTIME")){
					dos.writeUTF("[SG*"+ringId+"*0008*WORKTIME]");
				}
				if(receiveMessageFromServer.contains("POWEROFF")){
					dos.writeUTF("[SG*"+ringId+"*0008*POWEROFF]");
				}
				if(receiveMessageFromServer.contains("REMOVE")){
					dos.writeUTF("[SG*"+ringId+"*0006*REMOVE]");
				}
				if(receiveMessageFromServer.contains("FLOWER")){
					dos.writeUTF("[SG*"+ringId+"*0006*FLOWER]");
				}
				if(receiveMessageFromServer.contains("PULSE")){
					dos.writeUTF("[SG*"+ringId+"*0008*PULSE,72]");
				}
				if(receiveMessageFromServer.contains("MESSAGE")){
					dos.writeUTF("[SG*"+ringId+"*0007*MESSAGE]");
				}
				if(receiveMessageFromServer.contains("WHITELIST1")){
					dos.writeUTF("[SG*"+ringId+"*000A*WHITELIST1]");
				}
				if(receiveMessageFromServer.contains("WHITELIST2")){
					dos.writeUTF("[SG*"+ringId+"*000A*WHITELIST2]");
				}
			}
		} catch (Exception e) {

		}
	}

	public static void main(String[] args)
	{
		
		try {
				ID = ++ID;
				System.out.println("client" + ID);
				InetAddress ipadd = InetAddress.getByName("192.168.0.102");
				Socket s = new Socket(ipadd, 8082);
				TestFunction a = new TestFunction(ID, s);
				a.start();
			

		} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	}

}

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
			System.out.println("ringId:"+ringId);
			if(ringId.contains("TK")&&!ringId.contains("TKQ"))
			{
				System.out.println("TK");
				VoiceOfClient  voiceOfClient = new  VoiceOfClient(ID,clientsocket);
				voiceOfClient.init();
			}
			else if(ringId.contains("SMS"))
			{
				String[] messages=ringId.split("\\*", 4);
				String message=messages[0]+"*"+messages[1]+"*"+String.format("%04x",messages[3].getBytes("GB2312").length-1)+"*"+messages[3];
				dos.write((message).getBytes("gb2312"));
				dos.flush();	
			}
			else
			{
				String[] messages=ringId.split("\\*", 4);
				String message=messages[0]+"*"+messages[1]+"*"+String.format("%04x",messages[3].getBytes("GB2312").length-1)+"*"+messages[3];
				dos.write((message).getBytes("gb2312"));
				dos.flush();				
			}
			while (true) {
				InputStream is = clientsocket.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				byte[] buf = new byte[100];
				int len = dis.read(buf);
				String receiveMessageFromServer = new String(buf, 0, len);
				System.out.println("Receive from server"+receiveMessageFromServer);
			}
		} catch (Exception e) {

		}
	}

	public static void main(String[] args)
	{
		
		try {
				ID = ++ID;
				System.out.println("client" + ID);
				InetAddress ipadd = InetAddress.getByName("182.92.67.109");
				Socket s = new Socket(ipadd, 8082);
				TestFunction a = new TestFunction(ID, s);
				a.start();
			

		} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	}

}

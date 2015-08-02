package sepim.server;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;



public class TestUD extends Thread {
    private static int ID=0;
    public Socket clientsocket=null;
	public TestUD(int id,Socket soc)
	{
		this.ID=id;
		this.clientsocket=soc;
	}
	

	
	public void run() {
		try {
			OutputStream Os =clientsocket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(Os);
			dos.writeUTF("[SG*8800000015*0087*UD,220414,134652,A,22.571707,N,113.8613968,E,0.1,0.0,100,7,60,90,1000,50,0001,4,1,460,0,9360,4082,131,9360,4092,148,9360,4091,143,9360,4153,141]");
			dos.flush();
			dos.close();
		} catch (Exception e) {

		}
	}

	public static void main(String[] args)
	{
		
		try {
				ID = ++ID;
				System.out.println("client" + ID);
				InetAddress ipadd = InetAddress.getByName("219.217.245.235");
				Socket s = new Socket(ipadd, 8082);
				TestUD a = new TestUD(ID, s);
				a.start();
			

		} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	}

}

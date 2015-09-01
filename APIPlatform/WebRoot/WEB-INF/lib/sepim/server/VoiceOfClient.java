package sepim.server;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;


/*
 * send file to server, with no outputsream buffer size limited
 @param  the name of sending file
*/
public class VoiceOfClient extends Thread {
    private static int ID=0;
    public Socket clientsocket=null;
	// size of bufferStream 
	 public static  int SIZE=8192;
    // the total size of data
	 public static int total;
	 public byte[] buffer;


	public VoiceOfClient(int id,Socket soc)
	{
		this.ID=id;
		this.clientsocket=soc;
	}
	
	public void init(){
	try{
		ID=++ID;
		System.out.println("client"+ID+" is sending message to ip "+InetAddress.getByName("182.92.67.109"));
		InetAddress ipadd = InetAddress.getByName("182.92.67.109"); 
		Socket s=new Socket(ipadd,8082);
		
		new VoiceOfClient(ID,this.clientsocket).start();
		} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	}
	public void run()
	{	
		try{
			
	        // file name
			String filename= "E:\\test.amr";
            File music = new File(filename);
            total=(int) music.length();
            System.out.println("the file size is " +total+" bytes");
			InputStream is=clientsocket.getInputStream();
			OutputStream COvoice =clientsocket.getOutputStream();
			//buffer outputstream
			BufferedOutputStream bufout = new BufferedOutputStream(COvoice);	
    
			FileInputStream Filein = new FileInputStream(filename); 
			BufferedInputStream bufin = new BufferedInputStream(Filein);
		
			// allocate byte[] to restore File
			// read file to byte[]
			byte[] FileInByte = new byte[total];
			int count = total;
			//record send bytes number
			int readCount = 0; 
			while (readCount < count) {
				readCount += bufin.read(FileInByte, readCount, count - readCount);
			}
             System.out.println("Transfer file to byte[], the size is " +readCount+" bytes");
             byte[] TK = "[CS*YYYYYYYYYY*".getBytes();//A326代表41766bytes
             
             // 定义一个发送消息协议格式：|--header:4 byte--|--content:10MB--
             // 获取一个4字节长度的协议体头  
             byte[] dataLength = To16(Integer.toHexString(FileInByte.length+3)).getBytes(); 
             byte[] temp = combineByteArray(TK, dataLength);// [CS*YYYYYYYYYY*1234
             byte[] temp1 = "*TK,".getBytes();
             byte[] temp2 =  combineByteArray(temp, temp1); // [CS*YYYYYYYYYY*1234*TK,
             byte[] requestMessage = combineByteArray(temp2, FileInByte); // [CS*YYYYYYYYYY*1234*TK,ccccc
             
             byte[] signal = "]".getBytes();
             
             byte[] data = combineByteArray(requestMessage, signal);//[CS*YYYYYYYYYY*1234*TK,ccccc]         
             
             System.out.println(new String(data));
             
             bufout.write(data);
			 bufout.flush();
			
            // close resource
		     Filein.close();
		     bufout.close();
		     bufin.close();
		     is.close();
		     COvoice.close();
		     clientsocket.close();
		     System.out.println("actually send "+data.length+" bytes");
	      }
		  catch(Exception e)
		{
			 System.out.println("the exception is "+e);
			 e.printStackTrace();
		}
		
	 }
	//转成四位16进制的数
	public static String To16(String in)
	{
		int num=in.length();
		for(int i=0;i<4-num;i++)
			in="0"+in;
		return in;
	}
	
	private static byte[] combineByteArray(byte[] array1, byte[] array2) 
	 {  
	        byte[] combined = new byte[array1.length + array2.length];  
	        System.arraycopy(array1, 0, combined, 0, array1.length);  
	        System.arraycopy(array2, 0, combined, array1.length, array2.length);  
	        return combined;  
	 }  
}

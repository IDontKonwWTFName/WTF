package sepim.server.net;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import sepim.server.clients.World;
import sepim.server.net.packet.ALHandler;
import sepim.server.net.packet.ANYHandler;
import sepim.server.net.packet.APNHandler;
import sepim.server.net.packet.BTHandler;
import sepim.server.net.packet.CALLHandler;
import sepim.server.net.packet.CRHandler;
import sepim.server.net.packet.CenterHandler;
import sepim.server.net.packet.FACTORYHandler;
import sepim.server.net.packet.FINDHandler;
import sepim.server.net.packet.FLOWERHandler;
import sepim.server.net.packet.IPHandler;
import sepim.server.net.packet.LOWBATHandler;
import sepim.server.net.packet.LZHandler;
import sepim.server.net.packet.LinkHandler;
import sepim.server.net.packet.MESSAGEHandler;
import sepim.server.net.packet.MODEHandler;
import sepim.server.net.packet.MONITORHandler;
import sepim.server.net.packet.PEDOHandler;
import sepim.server.net.packet.POWEROFFHandler;
import sepim.server.net.packet.PULSEHandler;
import sepim.server.net.packet.PWHandler;
import sepim.server.net.packet.RADHandler;
import sepim.server.net.packet.REMINDHandler;
import sepim.server.net.packet.REMOVEHandler;
import sepim.server.net.packet.RESETHandler;
import sepim.server.net.packet.RGHandler;
import sepim.server.net.packet.SMSHandler;
import sepim.server.net.packet.SMSONOFFHandler;
import sepim.server.net.packet.SOSHandler;
import sepim.server.net.packet.SOSSMSHandler;
import sepim.server.net.packet.SlaveHandler;
import sepim.server.net.packet.TKHandler;
import sepim.server.net.packet.TKQHandler;
import sepim.server.net.packet.TSHandler;
import sepim.server.net.packet.UDHandler;
import sepim.server.net.packet.UPGRADEHandler;
import sepim.server.net.packet.URLHandler;
import sepim.server.net.packet.UploadHandler;
import sepim.server.net.packet.VERNOHandler;
import sepim.server.net.packet.WADHandler;
import sepim.server.net.packet.WGHandler;
import sepim.server.net.packet.WHITELISTHandler;
import sepim.server.net.packet.WORKHandler;
import sepim.server.net.packet.WORKTIMEHandler;

public class ServerHandler extends SimpleChannelHandler {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	String[] receiveUserIDAndCommands;
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) 
	{
		System.out.println("\n\n receive a message from client");
		Object msg = e.getMessage();  
		System.out.println("���յ��ַ����鳤��:"+((ChannelBuffer)msg).array().length);
				
		boolean verificat = false;
        
		boolean is_Phone = false;//��˭������falseΪ�ֻ���trueΪ�ֻ�
		String receiveCommandString = null;
		try 
		{
			receiveCommandString = new String(((ChannelBuffer)msg).array(),"utf-8");
		} 
		catch (UnsupportedEncodingException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String userId = "";
		String commands = null;
		
		//ȥ��"["
		receiveUserIDAndCommands = receiveCommandString.split("\\[",2);		
		
		//ȥ��"]"
		if(receiveUserIDAndCommands[1].charAt(receiveUserIDAndCommands[1].length()-1)==']')//�ֻ�����
		{
			System.out.println("�ֻ����ͣ�����");
			commands = receiveUserIDAndCommands[1].substring(0,receiveUserIDAndCommands[1].length()-1);
			userId = "";
			is_Phone = false;
		}
		else//�ֻ�����
		{
			System.out.println("�ֻ����ͣ�����");
			userId =  receiveUserIDAndCommands[1].substring(receiveUserIDAndCommands[1].length()-11);
			commands = receiveUserIDAndCommands[1].substring(0,receiveUserIDAndCommands[1].length()-12);
			is_Phone = true;
		}
		//��*Ϊ��Ƿָ��ַ���
		String[] receiveCommandStrings = commands.split("\\*",4);
		//��˾��
		String company = "";
		//�ֻ�ID
		String ringId = "";
		//���ݳ���
		String contentsLength = "";
		//����
		String contents = "";
		//�ж��ֻ�������Ϣ�Ƿ����Ҫ��
		if(receiveCommandStrings.length==4)
		{
			//��ֵ
			company = receiveCommandStrings[0];
			ringId = receiveCommandStrings[1];
			contents = receiveCommandStrings[3];
			if(!is_Phone)//�ֻ�����
			{
				contentsLength = receiveCommandStrings[2];
				//ע���ֻ�ID��channel
				System.out.println(ctx.getChannel());
				World.getWorld().register(ringId,ctx.getChannel());
			}
			else//�ֻ�����
			{
				contentsLength = receiveCommandStrings[2];
				contentsLength=  String.format("%04x",Integer.parseInt(contentsLength,16)-11);
				//����ȫ����Դ����֪ͨ�ֻ����ֻ�
				World.getWorld().getRingPhoneMap().put(ringId, userId);
				//����ȫ�ֱ������ֻ�����������
				World.getWorld().getPhoneCommandMap().put(ringId+contents.split(",")[0], contents);
			}
			System.out.println("company: "+company);
			System.out.println("ringId: "+ringId);
			System.out.println("contentsLength : "+contentsLength);
			System.out.println("contents:"+contents);
			System.out.println("userId:"+userId);
			System.out.println("��ʾ1��:"+ctx.getChannel());
		}
		//�жϷ��������ֻ��Ƿ��������Ļ������ĺ��룬���"��"ִ�г������"����"�����ֻ�
		//if(World.getWorld().getRingPhoneListMap().get(ringId).contains(userId)){
		//�����UserId������ͨ���ֻ�APP���������������ʱ��Ϊ����
		verificat=true;
//		}else{
//			//�����ݷ�װ��json
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("leixing","ERROR_NUM");  
//			jsonObject.put("shouhuan_id",ringId); 
//			//���������͸��ֻ�
////			new push.xxx(World.getWorld().getRingPhoneMap().get(ringId),jsonObject);
//		}
		//ͨ����֤������������
//		if(verificat){
		//�ֻ����ֻ��Խ�
		System.out.println(contents);
		/*
		 * by lj 8.29
		 */
		if(contents.startsWith("TK")&&!contents.startsWith("TKQ"))
		{
			new TKHandler().handle("TK",company,ringId, contentsLength,((ChannelBuffer)msg).array(),userId,contents);
		}
		else if(contents.startsWith("TKQ"))
		{
			new TKQHandler().handle("TKQ",company,ringId, contentsLength,((ChannelBuffer)msg).array());
		}
		//��·����
		else if(contents.startsWith("LK")){
			new LinkHandler().handle("LK",company,ringId, contentsLength,contents,userId);
		}
		
		//λ�������ϱ�
		else if(contents.startsWith("UD,")){
			new UDHandler().handle("UD",company,ringId, contentsLength,contents);
		}
		
		//��ʷλ�������ϱ�
		else if(contents.startsWith("UD2,")){
			new UDHandler().handle("UD2",company,ringId, contentsLength,contents);
		}

		//���������ϱ�
		else if(contents.startsWith("AL")){
			new ALHandler().handle("AL",company,ringId, contentsLength,contents);
		}

		//�����ַָ��
		else if(contents.startsWith("WAD")){
			new WADHandler().handle("WAD",company,ringId, contentsLength,contents);
		}

		//�����ַָ��
		else if(contents.startsWith("WG")){
			new WGHandler().handle("WG",company,ringId, contentsLength,contents);
		}

		//�����ַָ��
		else if(contents.startsWith("RAD")){
			new RADHandler().handle("RAD",company,ringId, contentsLength,contents);
		}

		//�����ַָ��
		else if(contents.startsWith("RG")){
			new RGHandler().handle("RG",company,ringId, contentsLength,contents);
		}
		
		//�����ϴ��������
		else if(contents.startsWith("UPLOAD")){
			new UploadHandler().handle("UPLOAD",company,ringId, contentsLength,contents,userId);
		}
		
		//���ĺ�������
		else if(contents.startsWith("CENTER")){
			new CenterHandler().handle("CENTER",company,ringId, contentsLength,contents,userId);
		}
		
		//�������ĺ�������
		else if(contents.startsWith("SLAVE")){
			new SlaveHandler().handle("SLAVE",company,ringId, contentsLength,contents,userId);
		}
		
		//������������
		else if(contents.startsWith("PW")){
			new PWHandler().handle("PW",company,ringId, contentsLength,contents,userId);
		}
		
		//����绰����
		else if(contents.startsWith("CALL")){
			new CALLHandler().handle("CALL",company,ringId, contentsLength,contents,userId);
		}
		
		//���Ͷ�������
		else if(contents.startsWith("SMS")&&!contents.startsWith("SMSONOFF")){
			new SMSHandler().handle("SMS",company,ringId, contentsLength,contents,userId);
		}
		
		//��������
		else if(contents.startsWith("MONITOR")){
			new MONITORHandler().handle("MONITOR",company,ringId, contentsLength,contents,userId);
		}
		
		//SOS��������
		else if(contents.startsWith("SOS")&&!contents.startsWith("SOSSMS")){
			new SOSHandler().handle("SOS",company,ringId, contentsLength,contents,userId);
		}
		
		//Զ����������
		else if(contents.startsWith("UPGRADE")){
			new UPGRADEHandler().handle("UPGRADE",company,ringId, contentsLength,contents,userId);
		}
		
		//IP����
		else if(contents.startsWith("IP")){
			new IPHandler().handle("IP",company,ringId, contentsLength,contents);
		}
		
		//�ָ�������������
		else if(contents.startsWith("FACTORY")){
			new FACTORYHandler().handle("FACTORY",company,ringId, contentsLength,contents,userId);
		}
		
		//�������Ժ�ʱ������
		else if(contents.startsWith("LZ")){
			new LZHandler().handle("LZ",company,ringId, contentsLength,contents,userId);
		}
		
		//��ѯ URL�ȸ���������
		else if(contents.startsWith("URL")){
			new URLHandler().handle("URL",company,ringId, contentsLength,contents,userId);
		}
		
		//SOS ���ű�����������
		else if(contents.startsWith("SOSSMS")){
			new SOSSMSHandler().handle("SOSSMS",company,ringId, contentsLength,contents,userId);
		}
		
		//�͵���ű�����������
		else if(contents.startsWith("LOWBAT")){
			new LOWBATHandler().handle("LOWBAT",company,ringId, contentsLength,contents,userId);
		}
		
		//APN����
		else if(contents.startsWith("APN")){
			new APNHandler().handle("APN",company,ringId, contentsLength,contents,userId);
		}
		
		//����Ȩ�޿�������
		else if(contents.startsWith("ANY")){
			new ANYHandler().handle("ANY",company,ringId, contentsLength,contents,userId);
		}
		
		//������ѯ
		else if(contents.startsWith("TS")){
			new TSHandler().handle("TS",company,ringId, contentsLength,contents,userId);
		}
		
		//�汾��ѯ
		else if(contents.startsWith("VERNO")){
			new VERNOHandler().handle("VERNO",company,ringId, contentsLength,contents,userId);
		}
		
		//����
		else if(contents.startsWith("RESET")){
			new RESETHandler().handle("RESET",company,ringId, contentsLength,contents,userId);
		}
		
		//��λָ������
		else if(contents.startsWith("CR")){
			new CRHandler().handle("CR",company,ringId, contentsLength,contents,userId);
		}
		
		//������������
		else if(contents.startsWith("BT")){
			new BTHandler().handle("BT",company,ringId, contentsLength,contents,userId);
		}
		
		//����ʱ�������ָ��
		else if(contents.startsWith("WORK")&&!contents.startsWith("WORKTIME")){
			new WORKHandler().handle("WORK",company,ringId, contentsLength,contents,userId);
		}
		
		//����ʱ������ָ������
		else if(contents.startsWith("WORKTIME")){
			new WORKTIMEHandler().handle("WORKTIME",company,ringId, contentsLength,contents,userId);
		}
		
		//�ػ�
		else if(contents.startsWith("POWEROFF")){
			new POWEROFFHandler().handle("POWEROFF",company,ringId, contentsLength,contents,userId);
		}
		
		//ȡ���ֻ�������������
		else if(contents.startsWith("REMOVE")){
			new REMOVEHandler().handle("REMOVE",company,ringId, contentsLength,contents,userId);
		}
		
		/*
		 * by lj
		 */
		//�Ʋ����ܿ���shezhi
		else if(contents.startsWith("PEDO")){
			new PEDOHandler().handle("PEDO",company,ringId, contentsLength,contents,userId);
		}
		//���ſ���
		else if(contents.startsWith("SMSONOFF")){
			new SMSONOFFHandler().handle("SMSONOFF",company,ringId, contentsLength,contents,userId);
		}
		//............
		
		
		//С�컨��������
		else if(contents.startsWith("FLOWER")){
			new FLOWERHandler().handle("FLOWER",company,ringId, contentsLength,contents,userId);
		}
		
		//��ѯ����
		else if(contents.startsWith("PULSE")){
			new PULSEHandler().handle("PULSE",company,ringId, contentsLength,contents,userId);
		}
		
		//������ʾ����
		else if(contents.startsWith("MESSAGE")){
			new MESSAGEHandler().handle("MESSAGE",company,ringId, contentsLength,contents,userId);
		}
		
		//����������
		else if(contents.startsWith("WHITELIST")){
			new WHITELISTHandler().handle("WHITELIST",company,ringId, contentsLength,contents,userId);
		}
		//�����ֱ�
		else if(contents.startsWith("FIND")){
			new FINDHandler().handle("FIND",company,ringId, contentsLength,contents,userId);
		}
		//��λģʽ
		else if(contents.startsWith("MODE")){
			new MODEHandler().handle("MODE",company,ringId, contentsLength,contents,userId);
		}
		//����ģʽ
		else if(contents.startsWith("REMIND")){
			new REMINDHandler().handle("REMIND",company,ringId, contentsLength,contents,userId);
		}
//		}
		
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) 
	{
 		World.getWorld().register(1+"",ctx.getChannel());
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		World.getWorld().unregister(ctx.getChannel());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.info("Exception caught: " + e.getCause());
	}

}

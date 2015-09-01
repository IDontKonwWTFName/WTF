package sepim.server.net;

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
import sepim.server.net.packet.FLOWERHandler;
import sepim.server.net.packet.IPHandler;
import sepim.server.net.packet.LOWBATHandler;
import sepim.server.net.packet.LZHandler;
import sepim.server.net.packet.LinkHandler;
import sepim.server.net.packet.MESSAGEHandler;
import sepim.server.net.packet.MONITORHandler;
import sepim.server.net.packet.POWEROFFHandler;
import sepim.server.net.packet.PULSEHandler;
import sepim.server.net.packet.PWHandler;
import sepim.server.net.packet.Packet;
import sepim.server.net.packet.RADHandler;
import sepim.server.net.packet.REMOVEHandler;
import sepim.server.net.packet.RESETHandler;
import sepim.server.net.packet.RGHandler;
import sepim.server.net.packet.SMSHandler;
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
		System.out.println("接收的字符数组长度:"+((ChannelBuffer)msg).array().length);
		System.out.println("接收的字符数组长度:"+new String(((ChannelBuffer)msg).array()));
		boolean verificat = false;
        
		boolean is_Phone = false;//由谁发出，false为手环，true为手机
		String receiveCommandString =new String(((ChannelBuffer)msg).array());
		String userId = "";
		String commands = null;
		
		//去除"["
		receiveUserIDAndCommands = receiveCommandString.split("\\[",2);		
		
		//去除"]"
		if(receiveUserIDAndCommands[1].charAt(receiveUserIDAndCommands[1].length()-1)==']')//手环发出
		{
			System.out.println("\n\n手环发送！！！");
			commands = receiveUserIDAndCommands[1].substring(0,receiveUserIDAndCommands[1].length()-1);
			userId = "";
			is_Phone = false;
		}
		else//手机发出
		{
			System.out.println("\n\n手机发送！！！");
			userId =  receiveUserIDAndCommands[1].substring(receiveUserIDAndCommands[1].length()-11);
			commands = receiveUserIDAndCommands[1].substring(0,receiveUserIDAndCommands[1].length()-12);
			is_Phone = true;
		}
		//以*为标记分割字符串
		String[] receiveCommandStrings = commands.split("\\*",4);
		//公司名
		String company = "";
		//手环ID
		String ringId = "";
		//内容长度
		String contentsLength = "";
		//内容
		String contents = "";
		//判断手环发送信息是否符合要求
		if(receiveCommandStrings.length==4)
		{
			//赋值
			company = receiveCommandStrings[0];
			ringId = receiveCommandStrings[1];
			if(!is_Phone)//手环发出
			{
				contentsLength = receiveCommandStrings[2];
			}
			else//手机发出
			{
				contentsLength = receiveCommandStrings[2];
				contentsLength=  String.format("%04x",Integer.parseInt(contentsLength,16)-11);
			}
			contents = receiveCommandStrings[3];
			System.out.println("company: "+company);
			System.out.println("ringId: "+ringId);
			System.out.println("contentsLength : "+contentsLength);
			//System.out.println("contents:"+contents);
			System.out.println("userId:"+userId);
			System.out.println("显示1：:"+ctx.getChannel());
			//注册手环ID和channel
			World.getWorld().register(ringId,ctx.getChannel());
			System.out.println("注册成功");
		}

		//判断发送命令手机是否在是中心或辅助中心号码，如果"是"执行程序，如果"不是"返回手机
		//if(World.getWorld().getRingPhoneListMap().get(ringId).contains(userId)){
		//如果有UserId，则是通过手机APP发过来的命令，推送时作为依据
		verificat=true;
		if(!userId.equals(""))
		{
				//加入全局资源，绑定通知手机和手环
				World.getWorld().getRingPhoneMap().put(ringId, userId);
		}
//		}else{
//			//把数据封装进json
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("leixing","ERROR_NUM");  
//			jsonObject.put("shouhuan_id",ringId); 
//			//把数据推送给手机
////			new push.xxx(World.getWorld().getRingPhoneMap().get(ringId),jsonObject);
//		}
		//通过验证，则进行命令发送
//		if(verificat){
		//手机与手环对讲
		if(contents.startsWith("TK")&&!contents.startsWith("TKQ"))
		{
			new TKHandler().handle("TK",company,ringId, contentsLength,((ChannelBuffer)msg).array(),userId,contents);
		}
		if(contents.startsWith("TKQ"))
		{
			new TKQHandler().handle("TKQ",company,ringId, contentsLength,((ChannelBuffer)msg).array());
		}
		//链路保持
		if(contents.startsWith("LK")){
			new LinkHandler().handle("LK",company,ringId, contentsLength,contents,userId);
		}
		
		//位置数据上报
		if(contents.startsWith("UD,")){
			new UDHandler().handle("UD",company,ringId, contentsLength,contents);
		}
		
		//历史位置数据上报
		if(contents.startsWith("UD2,")){
			new UDHandler().handle("UD2",company,ringId, contentsLength,contents);
		}

		//报警数据上报
		if(contents.startsWith("AL")){
			new ALHandler().handle("AL",company,ringId, contentsLength,contents);
		}

		//请求地址指令
		if(contents.startsWith("WAD")){
			new WADHandler().handle("WAD",company,ringId, contentsLength,contents);
		}

		//请求地址指令
		if(contents.startsWith("WG")){
			new WGHandler().handle("WG",company,ringId, contentsLength,contents);
		}

		//请求地址指令
		if(contents.startsWith("RAD")){
			new RADHandler().handle("RAD",company,ringId, contentsLength,contents);
		}

		//请求地址指令
		if(contents.startsWith("RG")){
			new RGHandler().handle("RG",company,ringId, contentsLength,contents);
		}
		
		//数据上传间隔设置
		if(contents.startsWith("UPLOAD")){
			new UploadHandler().handle("UPLOAD",company,ringId, contentsLength,contents,userId);
		}
		
		//中心号码设置
		if(contents.startsWith("CENTER")){
			new CenterHandler().handle("CENTER",company,ringId, contentsLength,contents,userId);
		}
		
		//辅助中心号码设置
		if(contents.startsWith("SLAVE")){
			new SlaveHandler().handle("SLAVE",company,ringId, contentsLength,contents,userId);
		}
		
		//控制密码设置
		if(contents.startsWith("PW")){
			new PWHandler().handle("PW",company,ringId, contentsLength,contents,userId);
		}
		
		//拨打电话设置
		if(contents.startsWith("CALL")){
			new CALLHandler().handle("CALL",company,ringId, contentsLength,contents,userId);
		}
		
		//发送短信设置
		if(contents.startsWith("SMS")){
			new SMSHandler().handle("SMS",company,ringId, contentsLength,contents,userId);
		}
		
		//监听设置
		if(contents.startsWith("MONITOR")){
			new MONITORHandler().handle("MONITOR",company,ringId, contentsLength,contents,userId);
		}
		
		//SOS号码设置
		if(contents.startsWith("SOS")){
			new SOSHandler().handle("SOS",company,ringId, contentsLength,contents,userId);
		}
		
		//远程升级设置
		if(contents.startsWith("UPGRADE")){
			new UPGRADEHandler().handle("UPGRADE",company,ringId, contentsLength,contents,userId);
		}
		
		//IP设置
		if(contents.startsWith("IP")){
			new IPHandler().handle("IP",company,ringId, contentsLength,contents);
		}
		
		//恢复出厂设置设置
		if(contents.startsWith("FACTORY")){
			new FACTORYHandler().handle("FACTORY",company,ringId, contentsLength,contents,userId);
		}
		
		//设置语言和时区设置
		if(contents.startsWith("LZ")){
			new LZHandler().handle("LZ",company,ringId, contentsLength,contents,userId);
		}
		
		//查询 URL谷歌链接设置
		if(contents.startsWith("URL")){
			new URLHandler().handle("URL",company,ringId, contentsLength,contents,userId);
		}
		
		//SOS 短信报警开关设置
		if(contents.startsWith("SOSSMS")){
			new SOSSMSHandler().handle("SOSSMS",company,ringId, contentsLength,contents,userId);
		}
		
		//低电短信报警开关设置
		if(contents.startsWith("LOWBAT")){
			new LOWBATHandler().handle("LOWBAT",company,ringId, contentsLength,contents,userId);
		}
		
		//APN设置
		if(contents.startsWith("APN")){
			new APNHandler().handle("APN",company,ringId, contentsLength,contents,userId);
		}
		
		//短信权限控制设置
		if(contents.startsWith("ANY")){
			new ANYHandler().handle("ANY",company,ringId, contentsLength,contents,userId);
		}
		
		//参数查询
		if(contents.startsWith("TS")){
			new TSHandler().handle("TS",company,ringId, contentsLength,contents,userId);
		}
		
		//版本查询
		if(contents.startsWith("VERNO")){
			new VERNOHandler().handle("VERNO",company,ringId, contentsLength,contents,userId);
		}
		
		//重启
		if(contents.startsWith("RESET")){
			new RESETHandler().handle("RESET",company,ringId, contentsLength,contents,userId);
		}
		
		//定位指令设置
		if(contents.startsWith("CR")){
			new CRHandler().handle("CR",company,ringId, contentsLength,contents,userId);
		}
		
		//蓝牙控制设置
		if(contents.startsWith("BT")){
			new BTHandler().handle("BT",company,ringId, contentsLength,contents,userId);
		}
		
		//工作时间段设置指令
		if(contents.startsWith("WORK")){
			new WORKHandler().handle("WORK",company,ringId, contentsLength,contents,userId);
		}
		
		//工作时间设置指令设置
		if(contents.startsWith("WORKTIME")){
			new WORKTIMEHandler().handle("WORKTIME",company,ringId, contentsLength,contents,userId);
		}
		
		//关机
		if(contents.startsWith("POWEROFF")){
			new POWEROFFHandler().handle("POWEROFF",company,ringId, contentsLength,contents,userId);
		}
		
		//取下手环报警开关设置
		if(contents.startsWith("REMOVE")){
			new REMOVEHandler().handle("REMOVE",company,ringId, contentsLength,contents,userId);
		}
		
		//小红花个数设置
		if(contents.startsWith("FLOWER")){
			new FLOWERHandler().handle("FLOWER",company,ringId, contentsLength,contents,userId);
		}
		
		//查询脉搏
		if(contents.startsWith("PULSE")){
			new PULSEHandler().handle("PULSE",company,ringId, contentsLength,contents,userId);
		}
		
		//短语显示设置
		if(contents.startsWith("MESSAGE")){
			new MESSAGEHandler().handle("MESSAGE",company,ringId, contentsLength,contents,userId);
		}
		
		//白名单设置
		if(contents.startsWith("WHITELIST")){
			new WHITELISTHandler().handle("WHITELIST",company,ringId, contentsLength,contents,userId);
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

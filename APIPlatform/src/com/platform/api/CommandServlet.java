package com.platform.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.NTCredentials;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import sipim.server.function.SocketConnectNetty;

import com.platform.model.User;

/**
 * Servlet implementation class CommandServlet
 */
//command  post
//命令格式
//cmd:
//shouhuan_id
//user_id
//参数：
@WebServlet("/command")
public class CommandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommandServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		String user_id = request.getParameter("user_id");
		String parameter= request.getParameter("parameter");
		String shouhuan_id=request.getParameter("shouhuan_id");
		String cmd = request.getParameter("cmd");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session s = sf.openSession();

		boolean flag = false;
		/*
		 * 执行命令
		 * 
		 * 交互
		 */
		
		String NettyString=null;
		String companyString=null;
		String cmdString=null;
		String nettyParameter=null;
		//len是一个00XX类型的十六进制数
		int l=0;
		 String len= null;
		switch (cmd) {
		
		
		// 数据上传间隔设置：netty 1
		
		
		
		case "UPLOAD":
			
			 companyString="LJ";	
			 cmdString="UPLOAD";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty+++++++++++++++++,
			//将nettyString传给netty
			//user_id,netyystring
			new SocketConnectNetty().connect(user_id,NettyString);
			//
			flag=true;
			System.out.println(NettyString);
		    
			break;
			
	////设置中心号码   2
		case "CENTER":
			 companyString="LJ";
			 cmdString="CENTER";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			
			
			break;
	//辅助中心号码设置   3
		case "SLAVE":
			 companyString="LJ";
			 cmdString="SLAVE";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			
			break;
		//控制密码设置  4		
		case "PW":
			 companyString="LJ";
			 cmdString="PW";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			
			break;
	//SOS 4
		case "SOS1":
			companyString="LJ";
			 cmdString="SOS1";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			
			break;
	//UPGRADE  5
		case "UPGRADE":
			companyString="LJ";
			 cmdString="UPGRADE";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//ip port  6
		case "IP":
			companyString="LJ";
			 cmdString="IP";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
			
		//no parameter
		//FACTORY   7
		case "FACTORY":
			companyString="LJ";
			 cmdString="FACTORY";
			// nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 //l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
		//设置语言和时区   8
		case "LZ":
			companyString="LJ";
			 cmdString="LZ";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//URL
		case "13":
			companyString="LJ";
			 cmdString="URL";
			 //nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//传给netty
			
			
			// 
			flag=true;
			System.out.println(NettyString);
			break;
	//SOSSMS SOS 开关
		case "14":
			companyString="LJ";
			 cmdString="SOSSMS";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//LOWBAT 低电量短信通知开关
		case "15":
			companyString="LJ";
			 cmdString="LOWBAT";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//APN
		case "16":
			companyString="LJ";
			 cmdString="APN";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//ANY  短信权限
		case "17":
			companyString="LJ";
			 cmdString="ANY";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//参数查询
		case "18":
			companyString="LJ";
			 cmdString="TS";
			 //nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//VERNO 版本
		case "19":
			companyString="LJ";
			 cmdString="VERNO";
			 //nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//RESET
		case "20":
			companyString="LJ";
			 cmdString="RESET";
			 //nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
			
	//CR 定位指令
		case "21":
			companyString="LJ";
			 cmdString="CR";
			 //nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//BT  蓝牙
		case "22":
			companyString="LJ";
			 cmdString="BT";
			 //nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//WORK 工作时间段设置
		case "23":
			companyString="LJ";
			 cmdString="WORK";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			 l=nettyParameter.length()+cmdString.length()+1;
			// l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//WORKTIME
		case "24":
			companyString="LJ";
			 cmdString="WORKTIME";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			 NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//POWEROFF 关机  25
		case "POWEROFF":
			companyString="LJ";
			 cmdString="POWEROFF";
			 //nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//REMOVE 取下手环报警开关
		case "26":
			companyString="LJ";
			 cmdString="REMOVE";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//FLOWER 小红花
		case "27":
			companyString="LJ";
			 cmdString="FLOWER";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			 NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//PULSE 脉搏
		case "28":
			companyString="LJ";
			 cmdString="PULSE";
			 //nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//短语显示设置指令	
		case "33":
			companyString="LJ";
			 cmdString="MESSAGE";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			l=nettyParameter.length()+cmdString.length()+1;
			// l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//白名单设置指令		
		case "41":
			companyString="LJ";
			 cmdString="WHITELIST1";
			 nettyParameter=parameter;
			//len是一个00XX类型的十六进制数
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			 NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			
			//传给netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
		
			
		default:
			//flag=false;
			break;
		}

		if (flag) {
			// 执行成功
			data.put("code", "100");
			data.put("msg", "操作成功");
			data.put("data", "");

			out.println(JSONObject.fromObject(data).toString());
			System.out.println(JSONObject.fromObject(data).toString());
		} else {
			// 失败
			data.put("code", "200");
			data.put("msg", "操作失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			System.out.println(JSONObject.fromObject(data).toString());
		}
		s.close();
		sf.close();
	}

}

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
// command post
// 命令格式
// cmd:
// shouhuan_id
// user_id
// 参数：
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
		String parameter = request.getParameter("parameter");
		String shouhuan_id = request.getParameter("shouhuan_id");
		String cmd = request.getParameter("cmd");
		//默认手环ID为这个
		//shouhuan_id = "1506012101";

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

//		SessionFactory sf = new Configuration().configure()
//				.buildSessionFactory();
//		Session s = sf.openSession();

		boolean flag = false;
		/*
		 * 执行命令
		 * 
		 * 交互
		 */
		//发给netty的string
		String nettyString = null;
		//
		String companyString = "LJ";
		//cmd
		String cmdString = null;
		//参数
		String nettyParameter = null;
		// len是一个00XX类型的十六进制数
		int l = 0;
		String len = null;
		if (parameter.equals("")) {
			cmdString = cmd;
			// nettyParameter=parameter;
			// len是一个00XX类型的十六进制数
			l = cmdString.length() + user_id.length();
			len = String.format("%04x", l);

			nettyString = "[" + companyString + "*" + shouhuan_id + "*" + len
					+ "*" + cmdString + "]" + user_id;

			// 传给netty
			new SocketConnectNetty().connect(user_id, nettyString);

			//
			flag = true;
			System.out.println(nettyString);

		} else {
			companyString = "LJ";
			cmdString = cmd;
			nettyParameter = parameter;
			// len是一个00XX类型的十六进制数
			l = nettyParameter.length() + cmdString.length() + 1
					+ user_id.length();
			len = String.format("%04x", l);

			nettyString = "[" + companyString + "*" + shouhuan_id + "*" + len
					+ "*" + cmdString + "," + nettyParameter + "]" + user_id;

			// 传给netty+++++++++++++++++,将nettyString传给netty user_id,netyystring
			// user_id,直接不用了
			new SocketConnectNetty().connect(user_id, nettyString);
			//
			flag = true;
			System.out.println(nettyString);

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
			data.put("code", "500");
			data.put("msg", "操作失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			System.out.println(JSONObject.fromObject(data).toString());
		}
		
		// switch (cmd) {
		//
		//
		// // 模式设置数据上传间隔设置：netty 1
		//
		//
		//
		// case "UPLOAD":
		//
		// companyString="LJ";
		// cmdString="UPLOAD";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty+++++++++++++++++,
		// //将nettyString传给netty
		// //user_id,netyystring
		// //user_id,直接不用了
		// new SocketConnectNetty().connect(user_id,nettyString);
		// //
		// flag=true;
		// System.out.println(nettyString);
		//
		// break;
		//
		// ////设置中心号码 2
		// case "CENTER":
		// companyString="LJ";
		// cmdString="CENTER";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id,nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		//
		//
		// break;
		// //辅助中心号码设置 3
		// case "SLAVE":
		// companyString="LJ";
		// cmdString="SLAVE";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		//
		// break;
		// //控制密码设置 4
		// case "PW":
		// companyString="LJ";
		// cmdString="PW";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		//
		// break;
		// //SOS 4
		// case "SOS1":
		// companyString="LJ";
		// cmdString="SOS1";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		//
		// break;
		// //UPGRADE 5
		// case "UPGRADE":
		// companyString="LJ";
		// cmdString="UPGRADE";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //ip port 6
		// case "IP":
		// companyString="LJ";
		// cmdString="IP";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		//
		// //no parameter
		// //FACTORY 7
		// case "FACTORY":
		// companyString="LJ";
		// cmdString="FACTORY";
		// // nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// //l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		//
		// //设置语言和时区 8
		// case "LZ":
		// companyString="LJ";
		// cmdString="LZ";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		//
		// new SocketConnectNetty().connect(user_id, nettyString);
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //URL
		// case "URL":
		// companyString="LJ";
		// cmdString="URL";
		// //nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		//
		// //SOSSMS SOS 开关
		// case "SOSSMS":
		// companyString="LJ";
		// cmdString="SOSSMS";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		//
		// //LOWBAT 低电量短信通知开关
		// case "LOWBAT":
		// companyString="LJ";
		// cmdString="LOWBAT";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		//
		// //APN
		// case "APN":
		// companyString="LJ";
		// cmdString="APN";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //ANY 短信权限
		// case "ANY":
		// companyString="LJ";
		// cmdString="ANY";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //参数查询
		// case "TS":
		// companyString="LJ";
		// cmdString="TS";
		// //nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		//
		// //VERNO 版本
		// case "VERNO":
		// companyString="LJ";
		// cmdString="VERNO";
		// //nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //RESET
		// case "RESET":
		// companyString="LJ";
		// cmdString="RESET";
		// //nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		//
		// //CR 定位指令
		// case "CR":
		// companyString="LJ";
		// cmdString="CR";
		// //nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id,nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //BT 蓝牙
		// case "BT":
		// companyString="LJ";
		// cmdString="BT";
		// //nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //WORK 工作时间段设置
		// case "WORK":
		// companyString="LJ";
		// cmdString="WORK";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// // l=cmdString.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //WORKTIME
		// case "WORKTIME":
		// companyString="LJ";
		// cmdString="WORKTIME";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		//
		// //POWEROFF 关机 25
		// case "POWEROFF":
		// companyString="LJ";
		// cmdString="POWEROFF";
		// //nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id,nettyString);
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //REMOVE 取下手环报警开关
		// case "REMOVE":
		// companyString="LJ";
		// cmdString="REMOVE";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //FLOWER 小红花
		// case "FLOWER":
		// companyString="LJ";
		// cmdString="FLOWER";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //PULSE 脉搏
		// case "PULSE":
		// companyString="LJ";
		// cmdString="PULSE";
		// //nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //短语显示设置指令
		// case "MESSAGE":
		// companyString="LJ";
		// cmdString="MESSAGE";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// l=nettyParameter.length()+cmdString.length()+1+user_id.length();
		// // l=cmdString.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		// //白名单设置指令
		// case "WHITELIST1":
		// companyString="LJ";
		// cmdString="WHITELIST1";
		// nettyParameter=parameter;
		// //len是一个00XX类型的十六进制数
		// // l=nettyParameter.length()+cmdString.length()+1;
		// l=cmdString.length()+user_id.length();
		// len= String.format("%04x", l);
		//
		// nettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]"+user_id;
		//
		//
		// //传给netty
		// new SocketConnectNetty().connect(user_id, nettyString);
		//
		//
		// //
		// flag=true;
		// System.out.println(nettyString);
		// break;
		//
		//
		// default:
		// //flag=false;
		// break;
		// }

	}

}

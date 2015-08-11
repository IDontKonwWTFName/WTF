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
//�����ʽ
//cmd:
//shouhuan_id
//user_id
//������
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
		 * ִ������
		 * 
		 * ����
		 */
		
		String NettyString=null;
		String companyString=null;
		String cmdString=null;
		String nettyParameter=null;
		//len��һ��00XX���͵�ʮ��������
		int l=0;
		 String len= null;
		switch (cmd) {
		
		
		// �����ϴ�������ã�netty 1
		
		
		
		case "UPLOAD":
			
			 companyString="LJ";	
			 cmdString="UPLOAD";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty+++++++++++++++++,
			//��nettyString����netty
			//user_id,netyystring
			new SocketConnectNetty().connect(user_id,NettyString);
			//
			flag=true;
			System.out.println(NettyString);
		    
			break;
			
	////�������ĺ���   2
		case "CENTER":
			 companyString="LJ";
			 cmdString="CENTER";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			
			
			break;
	//�������ĺ�������   3
		case "SLAVE":
			 companyString="LJ";
			 cmdString="SLAVE";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			
			break;
		//������������  4		
		case "PW":
			 companyString="LJ";
			 cmdString="PW";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			
			break;
	//SOS 4
		case "SOS1":
			companyString="LJ";
			 cmdString="SOS1";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			
			break;
	//UPGRADE  5
		case "UPGRADE":
			companyString="LJ";
			 cmdString="UPGRADE";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//ip port  6
		case "IP":
			companyString="LJ";
			 cmdString="IP";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
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
			//len��һ��00XX���͵�ʮ��������
			 //l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
		//�������Ժ�ʱ��   8
		case "LZ":
			companyString="LJ";
			 cmdString="LZ";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//URL
		case "13":
			companyString="LJ";
			 cmdString="URL";
			 //nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//����netty
			
			
			// 
			flag=true;
			System.out.println(NettyString);
			break;
	//SOSSMS SOS ����
		case "14":
			companyString="LJ";
			 cmdString="SOSSMS";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//LOWBAT �͵�������֪ͨ����
		case "15":
			companyString="LJ";
			 cmdString="LOWBAT";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//APN
		case "16":
			companyString="LJ";
			 cmdString="APN";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//ANY  ����Ȩ��
		case "17":
			companyString="LJ";
			 cmdString="ANY";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//������ѯ
		case "18":
			companyString="LJ";
			 cmdString="TS";
			 //nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//VERNO �汾
		case "19":
			companyString="LJ";
			 cmdString="VERNO";
			 //nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//RESET
		case "20":
			companyString="LJ";
			 cmdString="RESET";
			 //nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
			
	//CR ��λָ��
		case "21":
			companyString="LJ";
			 cmdString="CR";
			 //nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//BT  ����
		case "22":
			companyString="LJ";
			 cmdString="BT";
			 //nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//WORK ����ʱ�������
		case "23":
			companyString="LJ";
			 cmdString="WORK";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			 l=nettyParameter.length()+cmdString.length()+1;
			// l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//WORKTIME
		case "24":
			companyString="LJ";
			 cmdString="WORKTIME";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			 NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//POWEROFF �ػ�  25
		case "POWEROFF":
			companyString="LJ";
			 cmdString="POWEROFF";
			 //nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//REMOVE ȡ���ֻ���������
		case "26":
			companyString="LJ";
			 cmdString="REMOVE";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//FLOWER С�컨
		case "27":
			companyString="LJ";
			 cmdString="FLOWER";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			 NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//PULSE ����
		case "28":
			companyString="LJ";
			 cmdString="PULSE";
			 //nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//������ʾ����ָ��	
		case "33":
			companyString="LJ";
			 cmdString="MESSAGE";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			l=nettyParameter.length()+cmdString.length()+1;
			// l=cmdString.length();
			 len= String.format("%04x", l);
			
			NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
	//����������ָ��		
		case "41":
			companyString="LJ";
			 cmdString="WHITELIST1";
			 nettyParameter=parameter;
			//len��һ��00XX���͵�ʮ��������
			// l=nettyParameter.length()+cmdString.length()+1;
			 l=cmdString.length();
			 len= String.format("%04x", l);
			
			 NettyString="["+companyString+"*"+shouhuan_id+"*"+len+"*"+cmdString+","+nettyParameter+"]";
			
			
			//����netty
			
			
			//
			flag=true;
			System.out.println(NettyString);
			break;
		
			
		default:
			//flag=false;
			break;
		}

		if (flag) {
			// ִ�гɹ�
			data.put("code", "100");
			data.put("msg", "�����ɹ�");
			data.put("data", "");

			out.println(JSONObject.fromObject(data).toString());
			System.out.println(JSONObject.fromObject(data).toString());
		} else {
			// ʧ��
			data.put("code", "200");
			data.put("msg", "����ʧ��");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			System.out.println(JSONObject.fromObject(data).toString());
		}
		s.close();
		sf.close();
	}

}

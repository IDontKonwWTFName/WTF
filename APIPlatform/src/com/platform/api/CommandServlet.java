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

import com.platform.model.User;

/**
 * Servlet implementation class CommandServlet
 */
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
		String userid = request.getParameter("id");
		String parameter= request.getParameter("parameter");
		String shouhuan_id=request.getParameter("shouhuan_id");
		
		String cmd = request.getParameter("cmd");
		System.out.println("CMD: " + cmd + " User:" + userid);
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
		// �����ϴ�������ã�netty
		case "1":
			
			 companyString="LJ";
			 cmdString="UPLOAD";
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
			
	////�������ĺ���
		case "2":
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
	//�������ĺ�������
		case "3":
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
	//SOS 
		case "8":
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
	//UPGRADE
		case "9":
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
		case "10":
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
		case "11":
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
		case "12":
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
		case "13":
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
		case "14":
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
		case "15":
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
		case "16":
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
		case "17":
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
		case "18":
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
		} else {
			// ʧ��
			data.put("code", "200");
			data.put("msg", "����ʧ��");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
		}
		s.close();
		sf.close();
	}

}

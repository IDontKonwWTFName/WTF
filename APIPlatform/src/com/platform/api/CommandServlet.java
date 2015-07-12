package com.platform.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.platform.model.User;

/**
 * Servlet implementation class CommandServlet
 */
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		String userid = request.getParameter("id");
		String cmd = request.getParameter("cmd");
		System.out.println("CMD: "+cmd+" User:"+userid);
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		
		HttpSession session1 = request.getSession();
		User userInfo = (User)session1.getAttribute("user");
		boolean flag = true;
		/*
		 * 	执行命令
		 * 
		 * 	交互
		 * 
		 */
		
		if(flag && userInfo != null){
			// 执行成功
			data.put("code","100");
			data.put("msg", "操作成功");
			data.put("data", "");
			
			out.println(JSONObject.fromObject(data).toString());
		}else{
			// 失败
			data.put("code","200");
			data.put("msg", "操作失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
		}
		s.close();
		sf.close();
	}

}

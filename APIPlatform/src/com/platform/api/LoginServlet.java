package com.platform.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.*;
import java.util.*;
import java.io.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import com.platform.model.User;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json");
		JSONObject jo = new JSONObject();
		Map<String,String> user = new HashMap<String, String>();
		user.put("name","APIPlatform");
		user.put("version","1.0");
		user.put("author", "carpela");
		user.put("email", "carpela@163.com");
		jo = JSONObject.fromObject(user);
		System.out.println(jo.toString());
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		System.out.println("Login: "+id+" "+passwd);
		response.setContentType("text/x-json");
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();  
        Session s = sf.openSession();  
        
		PrintWriter out = response.getWriter();
		JSONObject msg;
		Map<String, String> data = new HashMap<String, String>();
		
		try{  
            //准备数据   
            SQLQuery query = s.createSQLQuery("select * from dbo.[user] where user_id="+id).addEntity(User.class);
            User user = (User)query.uniqueResult();
            if(user != null && user.getPassword().equals(passwd))
            {
            	data.put("code","100");
    			data.put("msg","验证成功");
    			data.put("data","");
            } else {
            	data.put("code","200");
    			data.put("msg","用户名或密码错误");
    			data.put("data","");
            }
        }catch(Exception err){  
	        err.printStackTrace();  
	        data.put("code","500");
			data.put("msg","内部服务器错误");
			data.put("data","");
	    }finally{  
	        s.close();  
	    } 
		msg = JSONObject.fromObject(data);
		out.println(msg.toString());
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

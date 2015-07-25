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

import com.platform.model.Relation;
import com.platform.model.User;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/zhangtingxiu")
public class zhangtingxiuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		JSONObject jo = new JSONObject();
		Map<String,String> user = new HashMap<String, String>();
		user.put("name","张婷秀");
		user.put("nickname","停车");
		user.put("sex", "女");
		user.put("oo", "正在/(ㄒoㄒ)/~~哭");
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
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/x-json");
		String user_id = request.getParameter("user_id");
		String passwd = request.getParameter("passwd");
		System.out.println("Login: "+user_id+" "+passwd);
		
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();  
        Session s = sf.openSession();  
        
		PrintWriter out = response.getWriter();
		JSONObject msg;
		Map<String, String> data = new HashMap<String, String>();
		
		try{  
            //准备数据   
			SQLQuery query = s.createSQLQuery("select * from dbo.[user] where user_id=:user_id").addEntity(User.class);
			query.setString("user_id", user_id);
			User user = (User)query.uniqueResult();
            if(user != null && user.getPassword().equals(passwd))
            {
            //登录成功后返回关联手环信息?
            	SQLQuery sqlQuery =s.createSQLQuery("select * from dbo.[relation] where user_id=:user_id").addEntity(Relation.class);
            	sqlQuery.setString("user_id", user_id);
            	List<Relation> relations=sqlQuery.list();
            	
            	
            	
            	
            	
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
		System.out.println(msg.toString());
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

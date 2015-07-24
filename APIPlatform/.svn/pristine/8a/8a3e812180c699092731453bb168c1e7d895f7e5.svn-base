package com.platform.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.border.EmptyBorder;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import antlr.collections.List;

import com.platform.model.User;
import com.platform.model.User_info;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	
	//查看是否注册
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		
		String user_id = request.getParameter("user_id");
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		Map<String,String> data = new HashMap<String, String>();
		
		try{
			SQLQuery sqlQuery = session.createSQLQuery("select * from dbo.[user] where user_id = "+user_id).addEntity(User.class);
			//没有注册过
			if (sqlQuery.uniqueResult()==null){
				data.put("code", "100");
				data.put("msg", "可以注册！");
				data.put("data", "");
			}
			//注册过
			else {
				data.put("code", "200");
				data.put("msg", "注册过，请找回密码！");
				data.put("data", "");
				
			}
		}catch(Exception e){
			data.put("code", "500");
			data.put("msg", "数据库查找失败！");
			data.put("data", "");
			
		}
		response.getWriter().println(JSONObject.fromObject(data).toString());
		
		
		
	}
	
    //插入账号密码
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {
			//获取注册id和passwd
		   request.setCharacterEncoding("utf-8");
		   response.setCharacterEncoding("utf-8");
		   response.setContentType("text/json");
		  
		    String id=request.getParameter("user_id");
			String passwd=request.getParameter("passwd");
			String email=request.getParameter("email");
			System.out.println(id+"is coming!");
			//查找是否已经注册
			SessionFactory sessionFactory= new Configuration().configure().buildSessionFactory();
			Session session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			Map<String,String>data =new HashMap<String,String>();
			try {
				
				SQLQuery sqlQuery= session.createSQLQuery("select * from dbo.[user] where user_id = "+id).addEntity(User.class);
			
				//如果返回的user为空，则可以注册
				if (sqlQuery.uniqueResult() == null){
					User user=new User();
					user.setUser_id(id);
					user.setPassword(passwd);
					//注册用户的同时创建用户info表
					User_info user_info =new User_info();
					user_info.setUser_id(id);
					user_info.setEmail(email);
					
					
					session.save(user);
					session.save(user_info);
					transaction.commit();
					
					data.put("code", "100");
					data.put("msg", "注册成功成功/sucesess!");
					data.put("data","");
					response.getWriter().println(JSONObject.fromObject(data).toString());
				}else{
				//否则返回
				data.put("code", "200");
				data.put("msg", "账号已注册！already!");
				data.put("data","");
				response.getWriter().println(JSONObject.fromObject(data).toString());
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				 e.printStackTrace(); 
				data.put("code", "500");
				data.put("msg", "获取数据失败/failed");
				data.put("data","");
				//response.getWriter().println(JSONObject.fromObject(data).toString().getBytes("utf-8"));
				response.getWriter().write(JSONObject.fromObject(data).toString());
			}finally{
				session.close();
				sessionFactory.close();
			}
			
	}
}

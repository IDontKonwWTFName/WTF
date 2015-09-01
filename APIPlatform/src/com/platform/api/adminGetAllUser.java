package com.platform.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.platform.model.User_info;

@WebServlet("/admingetalluser")
public class adminGetAllUser extends HttpServlet{
	@Override
	    //每个人的id，性别和注册时间
		//admingetuser  get
		//
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		System.out.println("admin");
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session =sessionFactory.openSession();
		Map<String, String> data =new HashMap<String, String>();
		
		
		try {
			SQLQuery sqlQuery=(SQLQuery) session.createSQLQuery("select * from dbo.[user_info] ").addEntity(User_info.class);
			List<User_info>  uis=  sqlQuery.list();
			
			JSONObject jsonObject=null;
			JSONArray jsonArray=new JSONArray().fromObject(uis);
			
			System.out.println(uis.size());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd "); 
			
//			for(User_info ui:uis ){
//				
//				jsonObject=new JSONObject();
//				
//				//jsonObject.put("birthday",formatter.format());
//				jsonObject.put("birthday",ui.getBirthday() == null ? "" : formatter.format(ui.getBirthday()));
//				jsonObject.put("user_id",  ui.getUser_id() == null ? "" : ui.getUser_id());
//				jsonObject.put("channel_id", ui.getChannel_id() == null ? "" : ui.getChannel_id());
//				jsonObject.put("user_name", ui.getUser_name() == null ? "" : ui.getUser_name());
//				jsonObject.put("sex",  ui.getSex() == null ? 0 : ui.getSex());
//				jsonObject.put("phonenumber",  ui.getPhonenumber() == null ? "" : ui.getPhonenumber());
//				jsonObject.put("email",ui.getEmail() == null ? "" : ui.getEmail());
////				jsonObject.put("user_id", ui.getUser_id() == null ? "" : ui.getUser_id());
////				jsonObject.put("channel_id", ui.getChannel_id() == null ? "" : ui.getChannel_id());
////				jsonObject.put("user_name", ui.getUser_name() == null ? "" : ui.getUser_name());
////				jsonObject.put("sex", ui.getSex() == null ? "" : ui.getSex());
////				jsonObject.put("phonenumber", ui.getPhonenumber() == null ? "" : ui.getPhonenumber());
////				jsonObject.put("email", ui.getEmail() == null ? "" : ui.getEmail());
//				
//				jsonArray.add(jsonObject);
//			}
			data.put("code", "100");
			data.put("msg", "user");
			data.put("data", jsonArray.toString());
			response.getWriter().println(JSONObject.fromObject(data).toString());
			System.err.println(jsonArray.toString());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		}
		
	}
}

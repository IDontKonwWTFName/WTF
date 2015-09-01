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
import javax.xml.crypto.Data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.cfg.Configuration;

import com.platform.model.User_log;

@WebServlet("/admimGetLatestUserLog")
public class getLatestUserLog extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	response.setContentType("text/x-json");
	
	String user_id = request.getParameter("user_id");
	
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	Session session =sessionFactory.openSession();
	Map<String, String> data =new HashMap<String, String>();
	
	try {
		SQLQuery sqlQuery =session.createSQLQuery("select top 500 * from dbo.[User_log] where user_id=" + user_id + " order by time asc").addEntity(User_log.class);
		List<User_log> uls =sqlQuery.list();
		JSONObject jsonObject=null;
		JSONArray jsonArray=new JSONArray();
		
		for (User_log u : uls ){
			jsonObject=new JSONObject();
			jsonObject.put("event", u.getEvent());
			jsonObject.put("log_type", u.getLog_type());
			jsonObject.put("user_log_id", u.getUser_log_id());
			jsonObject.put("user_id", u.getUser_id());
			jsonObject.put("time", u.getTime());
			
			jsonArray.add(jsonObject);
		}
		
		
		data.put("code", "100");
		data.put("msg", "get 数据成功");
		data.put("data", jsonArray.toString());
		response.getWriter().println(JSONObject.fromObject(data).toString());
		System.out.println(data.toString()); 
	} catch (Exception e) {
		// TODO: handle exception
	}	
	}
}


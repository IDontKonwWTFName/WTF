package com.platform.api;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.platform.model.Payment;
import com.platform.model.Shouhuan;
@WebServlet("/adminGetAPayment")
public class adminGetAPayment extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/x-json");
        
		String shouhuan_id = request.getParameter("shouhuan_id");
		String user_id=request.getParameter("user_id");
		System.out.println("Shouhuan_id: "+shouhuan_id);
		
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		
		if(shouhuan_id==null || shouhuan_id.equals(""))
		{
			data.put("code","200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			return;
		}
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
	
		try{
			SQLQuery sqlQuery = session.createSQLQuery("select * from dbo.[payment] where shouhuan_id=:shouhuan_id").addEntity(Shouhuan.class);
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			List<Payment> ps = sqlQuery.list();
			JSONArray ja = JSONArray.fromObject(ps);
						 
			data.put("code","100");
			data.put("msg", "获取数据成功");
			data.put("data", ja.toString());
			
			out.println(JSONObject.fromObject(data).toString());
		}catch(Exception e)
		{
			data.put("code","200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		}finally
		{
			session.close();
			sessionFactory.close();
		}
	}
}

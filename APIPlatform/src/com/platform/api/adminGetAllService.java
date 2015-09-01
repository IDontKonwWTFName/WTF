package com.platform.api;

import java.io.IOException;
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

import com.platform.model.Service;
@WebServlet("/admingetallservice")
public class adminGetAllService extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session =sessionFactory.openSession();
		Map<String, String> data =new HashMap<String, String>();
		
		try {
			SQLQuery sqlQuery =session.createSQLQuery("select * from dbo.[service] ").addEntity(Service.class);
			List<Service> services =sqlQuery.list();
			JSONObject jsonObject=null;
			JSONArray jsonArray=new JSONArray().fromObject(services);
			
//			for (Service s : services) {
//				jsonObject=new JSONObject();
//				jsonObject.put("service_describe", s.getService_describe());
//				jsonObject.put("service_price", s.getService_price());
//				jsonObject.put("service_type", s.getService_type());
//				jsonObject.put("service_workime", s.getService_workime());
//				
//				jsonArray.add(jsonObject);
//			}
			
			
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

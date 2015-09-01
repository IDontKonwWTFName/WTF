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

import com.platform.model.Shouhuan_log;

@WebServlet("/adminGetAllShouhuanLog")
public class adminGetAllShouhuanLog extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	response.setContentType("text/x-json");
		
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	Session session =sessionFactory.openSession();
	Map<String, String> data =new HashMap<String, String>();
	
	try {
		SQLQuery sqlQuery =session.createSQLQuery("select top 500 * from dbo.[Shouhuan_log] order by time asc").addEntity(Shouhuan_log.class);
		List<Shouhuan_log> sls =sqlQuery.list();
		JSONObject jsonObject=null;
		JSONArray jsonArray=new JSONArray().fromObject(sls);
		
//		for (Shouhuan_log s : sls) {
//			jsonObject=new JSONObject();
//			jsonObject.put("event", s.getEvent());
//			jsonObject.put("log_type", s.getLog_type());
//			jsonObject.put("shouhuan_log_id", s.getShouhuan_log_id());
//			jsonObject.put("shouhuan_id", s.getShouhuan_id());
//			jsonObject.put("time", s.getTime());
//			
//			jsonArray.add(jsonObject);
//		}
		
		
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

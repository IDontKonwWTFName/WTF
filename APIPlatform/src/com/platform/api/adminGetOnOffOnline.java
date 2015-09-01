package com.platform.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.platform.model.Shouhuan;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sepim.server.clients.World;


@WebServlet("/adminGetOnOffOnline")
public class adminGetOnOffOnline extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		HashMap<String, Date> Map = World.getWorld().getRingLkTimeMap();
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session =sessionFactory.openSession();
		Map<String, String> dataAll =new HashMap<String, String>();
		
		try{
			int onCount = Map.size();
			JSONObject jsonObject = new JSONObject();
			
			
			SQLQuery sqlQuery =session.createSQLQuery("select * from dbo.[shouhuan] ").addEntity(Shouhuan.class);
			List< Shouhuan> shouhuans =sqlQuery.list();
			int allCount = shouhuans.size();
			
			jsonObject.put("onCount", onCount);
			jsonObject.put("allCount", allCount);
			
			data.put("code","100");
			data.put("msg", "数据获取成功");
			data.put("data", jsonObject.toString());
			
			out.println(JSONObject.fromObject(data).toString());
		}catch(Exception e){
			data.put("code","200");
			data.put("msg", "信息获取失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		}finally{}
	}
}

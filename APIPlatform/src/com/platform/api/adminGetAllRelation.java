package com.platform.api;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.platform.model.Relation;
import com.platform.model.Repairment;

@WebServlet("/adminGetAllRelation")
public class adminGetAllRelation extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");

		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
	
		try{
			SQLQuery query = s.createSQLQuery("select * from relation").addEntity(Relation.class);
			List<Relation> rs = query.list();
			JSONObject jsonObject=null;
			JSONArray jsonArray=new JSONArray().fromObject(rs);
			
//			for(Relation r : rs){
//				jsonObject=new JSONObject();
//				jsonObject.put("shouhuan_id", r.getShouhuan_id());
//				jsonObject.put("user_id", r.getUser_id());
//				jsonObject.put("administor", r.getAdministor());
//				jsonObject.put("power", r.getPower());
//				jsonObject.put("relation", r.getRelation());
//				
//				jsonArray.add(jsonObject);
//			}
			
			data.put("code","100");
			data.put("msg", "获取数据成功");
			data.put("data", jsonArray.toString());
			
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
			s.close();
			sf.close();
		}
	}
}

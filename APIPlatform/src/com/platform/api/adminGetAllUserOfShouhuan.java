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

import com.platform.model.Relation;

@WebServlet("/admimGetRelationByShouhuan")
public class adminGetAllUserOfShouhuan extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	response.setContentType("text/x-json");
	
	String shouhuan_id = request.getParameter("shouhuan_id");
	
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	Session session =sessionFactory.openSession();
	Map<String, String> data =new HashMap<String, String>();
	
	try {
		SQLQuery sqlQuery =session.createSQLQuery("select * from dbo.[Relation] where shouhuan_id=" + shouhuan_id).addEntity(Relation.class);
		List<Relation> rs =sqlQuery.list();
		JSONObject jsonObject=null;
		JSONArray jsonArray=new JSONArray().fromObject(rs);
		
//		for (Relation r : rs) {
//			jsonObject=new JSONObject();
//			jsonObject.put("shouhuan_id", r.getShouhuan_id());
//			jsonObject.put("user_id", r.getUser_id());
//			jsonObject.put("power", r.getPower());
//			jsonObject.put("administor", r.getAdministor());
//			jsonObject.put("relation", r.getRelation());
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


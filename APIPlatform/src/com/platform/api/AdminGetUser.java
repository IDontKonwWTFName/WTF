package com.platform.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.hibernate.hql.internal.ast.SqlASTFactory;
import org.hibernate.transform.Transformers;

import antlr.collections.List;

@WebServlet("/admingetuser")
public class AdminGetUser extends HttpServlet{
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
		Map map=null;
		JSONObject jsonObject=null;
		JSONArray jsonArray=new JSONArray();
		
		try {
			SQLQuery sqlQuery=(SQLQuery) session.createSQLQuery("select info.user_id,info.sex,log.time from dbo.[user_info] info join dbo.[user_log] log on info.user_id=log.user_id where log.log_type=1").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			java.util.List  list=  sqlQuery.list();
			for(int i=0;i<list.size();i++){
				map=(Map) list.get(i);
//				jsonObject=JSONObject.fromObject(map);
				jsonObject=new JSONObject();
				jsonObject.put("user_id", map.get("user_id"));
				jsonObject.put("sex", map.get("sex"));
				jsonObject.put("time", map.get("time").toString());
				jsonArray.add(jsonObject);
			}
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

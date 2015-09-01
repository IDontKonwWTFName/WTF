package com.platform.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.Shouhuan;


@WebServlet("/adminUpdateFlower")
public class adminUpdateShouhuanFlower extends HttpServlet{
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));  
	    String line;  
	    StringBuilder sb = new StringBuilder();
	    while ((line = in.readLine()) != null)
	    {  
//	        System.out.println(line);
	        sb.append(line);
	    }
	    
	    //
	    
		   // System.out.println(sb.toString());
		    
	    JSONObject jo = JSONObject.fromObject(sb.toString());
	    String shouhuan_id = null;
	    String goal = null;
	    response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
	    try{
	    	shouhuan_id = jo.getString("shouhuan_id");
	    	goal = jo.getString("goal");
	    }catch(Exception e)
	    {
	    	data.put("code","200");
			data.put("msg", "修改数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
			return;
	    }
		
		System.out.println("Shouhuan(update): flower->"+goal);
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		try{
			SQLQuery sqlQuery = null;
			if(goal.equals("")){
				sqlQuery = s.createSQLQuery("update shouhuan set flower=flower+1 where shouhuan_id =:shouhuan_id");
				sqlQuery.setString("shouhuan_id", shouhuan_id);
				sqlQuery.addEntity(Shouhuan.class);
			}else if(goal.equals("")){
				sqlQuery = s.createSQLQuery("update shouhuan set flower=0 where shouhuan_id =:shouhuan_id");
				sqlQuery.setString("shouhuan_id", shouhuan_id);
				sqlQuery.addEntity(Shouhuan.class);
			}

//			SQLQuery query = s.createSQLQuery("update user set ? = ? where user_id = ?");
//			query.setParameter(0, which);
//			query.setParameter(1, value);
//			query.setParameter(2, id);
			sqlQuery.executeUpdate();
			t.commit();
			
			data.put("code","100");
			data.put("msg", "修改数据成功");
			data.put("data", "");
			
			out.println(JSONObject.fromObject(data).toString());
			
		}catch(Exception e)
		{
			data.put("code","200");
			data.put("msg", "修改数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		}
		finally
		{
			s.close();
			sf.close();
			
		}
	}
}

package com.platform.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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

@WebServlet("/adminUpdateAllShouhaunInfo")
public class adminUpdateAllShouhaunInfo extends HttpServlet{
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
	    String shouhuaninfo_name = null;
	    String shouhuaninfo_nickname = null;
	    String shouhuaninfo_birthday = null;
	    String shouhuaninfo_sex = null;
	    String shouhuaninfo_rate = null;
	    response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
	    try{
	    	shouhuan_id = jo.getString("shouhuan_id");
	    	shouhuaninfo_name = jo.getString("shouhuaninfo_name");
	    	shouhuaninfo_nickname = jo.getString("shouhuaninfo_nickname");
	    	shouhuaninfo_birthday = jo.getString("shouhuaninfo_birthday");
	    	shouhuaninfo_sex = jo.getString("shouhuaninfo_sex");
	    	shouhuaninfo_rate = jo.getString("shouhuaninfo_rate");
	    }catch(Exception e)
	    {
	    	data.put("code","200");
			data.put("msg", "修改数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
			return;
	    }
		
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		try{
			SQLQuery sqlQuery = null;
			sqlQuery = s.createSQLQuery("update shouhuan set "
					+ "sex =:sex "
					+ "nickname =:nickname "
					+ "birthday =:birthday "
					+ "name =:name "
					+ "rate =:rate "
					+ "where shouhuan_id =:shouhuan_id");
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			sqlQuery.setInteger("sex", Integer.parseInt(shouhuaninfo_sex));
			sqlQuery.setString("nickname", shouhuaninfo_nickname);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sqlQuery.setDate("birthday", sdf.parse(shouhuaninfo_birthday));
			sqlQuery.setString("name", shouhuaninfo_name);
			sqlQuery.setString("rate", shouhuaninfo_rate);
			sqlQuery.addEntity(Shouhuan.class);
			
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

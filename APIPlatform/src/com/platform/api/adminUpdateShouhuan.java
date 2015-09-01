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

import com.platform.model.User_info;

@WebServlet("/adminUpdateShouhuan")
public class adminUpdateShouhuan extends HttpServlet{
	@Override
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
	 
	    //System.out.println("user_info-----update"+sb.toString());
	    System.out.println("user_info-----update"+sb);
	    JSONObject jo = JSONObject.fromObject(sb.toString());
	    String user_id = null;
	    String user_name = null;
	    String phonenumber = null;
	    String email = null;
	    String sex = null;
	    String birthday = null;
	    response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
	    try{
	    	user_id = jo.getString("user_id");
	    	user_name = jo.getString("user_name");
	    	phonenumber = jo.getString("phonenumber");
	    	email = jo.getString("email");
	    	sex = jo.getString("sex");
	    	birthday = jo.getString("birthday");
	    }catch(Exception e)
	    {
	    	data.put("code","200");
			data.put("msg", "修改数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
			return;
	    }
		
		System.out.println("dbo.[User_info](update): user_id=" + user_id + ";user_name=" + user_name + ";"
				+ "phonenumber=" + phonenumber + ";email=" + email + ";sex=" + sex + ";birthday=" + birthday);
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		try{
			
			SQLQuery sqlQuery = s.createSQLQuery("update dbo.[User_info] set "
					+ "user_name=:user_name,"
					+ "phonenumber=:phonenumber,"
					+ "email=:email,"
					+ "sex=:sex,"
					+ "birthday=:birthday "
					+ "where user_id =:user_id");
			sqlQuery.setString("user_name", user_name);
			sqlQuery.setString("phonenumber", phonenumber);
			sqlQuery.setString("email", email);
			sqlQuery.setInteger("sex", Integer.parseInt(sex));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sqlQuery.setDate("birthday", sdf.parse(birthday));
			sqlQuery.setString("user_id", user_id);
			sqlQuery.addEntity(User_info.class);
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

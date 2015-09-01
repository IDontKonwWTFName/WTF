package com.platform.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.Relation;

@WebServlet("/adminAddRelation")
public class adminAddRelation extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {
		//获取注册id和passwd
	   request.setCharacterEncoding("utf-8");
	   response.setCharacterEncoding("utf-8");
	   response.setContentType("text/json");
	  
	    String user_id=request.getParameter("user_id");
		String shouhuan_id=request.getParameter("shouhuan_id");
		String relation=request.getParameter("relation");
		Integer administor = Integer.parseInt(request.getParameter("administor"));
		Integer power = Integer.parseInt(request.getParameter("power"));
		
		System.out.println("user_id:" + user_id + "  shouhuan_id:" + shouhuan_id + "  relation:" + relation + "  administor:" + "  power:" + power);
		//查找是否已经注册
		SessionFactory sessionFactory= new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Map<String,String>data =new HashMap<String,String>();
		try {
			
			Relation r = new Relation();
			r.setAdministor(administor);
			r.setPower(power);
			r.setRelation(relation);
			r.setUser_id(user_id);
			r.setShouhuan_id(shouhuan_id);
				
				
				session.save(r);
				transaction.commit();
				
				data.put("code", "100");
				data.put("msg", "注册成功成功/sucesess!");
				data.put("data","");
				response.getWriter().println(JSONObject.fromObject(data).toString());
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace(); 
			data.put("code", "500");
			data.put("msg", "获取数据失败/failed");
			data.put("data","");
			//response.getWriter().println(JSONObject.fromObject(data).toString().getBytes("utf-8"));
			response.getWriter().write(JSONObject.fromObject(data).toString());
		}finally{
			session.close();
			sessionFactory.close();
		}
		
}
}

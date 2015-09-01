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

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.User;
@WebServlet("/getpassword")
public class GetPassword extends HttpServlet  {
	@Override
	//忘记密码  
	//getpassword    post  user_id new_password
	//
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
    	String  user_id =request.getParameter("user_id");
    	String new_password=request.getParameter("new_password");
    	
    	Map<String, String> data = new HashMap<String, String>();

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
				SQLQuery sqlQuery2 = session
						.createSQLQuery("update dbo.[user] set password=:new_password where user_id=:user_id");
				sqlQuery2.setString("new_password", new_password);
				sqlQuery2.setString("user_id", user_id);
				sqlQuery2.executeUpdate();

				transaction.commit();
				data.put("code", "100");
				data.put("msg", "更新密码成功");
				data.put("data", "");
				response.getWriter().println(JSONObject.fromObject(data).toString());
		} catch (Exception e) {
			transaction.rollback();
			data.put("code", "200");
			data.put("msg", "更新密码失败");
			data.put("data", "");
			e.printStackTrace();
			response.getWriter().println(JSONObject.fromObject(data).toString());
		} finally {
			session.close();
			sessionFactory.close();
			System.out.println(JSONObject.fromObject(data).toString());
		}
		
	}
	

}

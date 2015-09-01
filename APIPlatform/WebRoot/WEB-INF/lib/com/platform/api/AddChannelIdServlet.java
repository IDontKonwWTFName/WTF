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

/** * @author  作者 E-mail: * @date 创建时间：2015年8月10日 下午3:20:23 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/addchannelid")

public class AddChannelIdServlet extends HttpServlet{
	@Override
	//addchannelid  post
	//user_id   channel_id
	//
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		String user_id=request.getParameter("user_id");
		String channel_id=request.getParameter("channel_id");
		System.out.println("channel_id"+channel_id);
		
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session =sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		Map<String, String>data=new HashMap<String, String>();
		
		try {
			SQLQuery sqlQuery=session.createSQLQuery("update dbo.[user_info] set channel_id=:channel_id where user_id=:user_id");
			sqlQuery.setString("channel_id", channel_id);
			sqlQuery.setString("user_id", user_id);
			sqlQuery.executeUpdate();
			transaction.commit();
			
			data.put("code", "100");
			data.put("msg", "设置channel_id成功");
			data.put("data", "");
			response.getWriter().println(JSONObject.fromObject(data).toString());
			System.out.println(JSONObject.fromObject(data).toString());
		} catch (Exception e) {
			// TODO: handle exception
			data.put("code", "500");
			data.put("msg", "设置channel_id失败");
			data.put("data", "");
			response.getWriter().println(JSONObject.fromObject(data).toString());
			System.out.println(JSONObject.fromObject(data).toString());
		}
		
		
		
		
	}

}

package com.platform.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.a.mail.SendMailDemo;

/** * @author  作者 E-mail: * @date 创建时间：2015年7月25日 下午5:59:40 * @version 1.0 * @parameter  * @since  * @return  */

//user_id,根据user_id，发送邮件给数据库的邮箱,用来修改密码
@WebServlet("/email")
public class EmailServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		
		String user_id=request.getParameter("user_id");
		String address=null;
		SessionFactory sessionFactory =new  Configuration().configure().buildSessionFactory();
		Session session =sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Map<String, String> data=new HashMap<String, String>();
		
		try {
			SQLQuery sqlQuery=session.createSQLQuery("select email from dbo.[user_info] where user_id =:user_id");
			sqlQuery.setString("user_id", user_id);
			address=(String) sqlQuery.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sql error");
		}
		
		 String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";   
		    Random random = new Random();   
		    StringBuffer sb = new StringBuffer();   
		    for (int i = 0; i < 20; i++) {   
		        int number = random.nextInt(base.length());   
		        sb.append(base.charAt(number));   
		    }  
		String npassword=sb.toString();
		System.out.println(npassword);
		try {
			SQLQuery sqlQuery2=session.createSQLQuery("update dbo.[user] set password=:password where user_id =:user_id");
			sqlQuery2.setString("password", npassword);
			sqlQuery2.setString("user_id", user_id);
			sqlQuery2.executeUpdate();
			transaction.commit();
			System.out.println(npassword+"update?"+user_id);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sql error 2");
		}
			
		session.close();
		sessionFactory.close();
		
		System.out.println(address);
		new SendMailDemo(address,npassword).send();
		System.out.println("ok");
		
		
		
	}
}

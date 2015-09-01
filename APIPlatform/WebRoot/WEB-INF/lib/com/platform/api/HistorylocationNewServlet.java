package com.platform.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

import com.platform.model.Historylocation;

/** * @author  作者 E-mail: * @date 创建时间：2015年7月23日 下午6:32:52 * @version 1.0 * @parameter  * @since  * @return  */
//get 最近的位置信息
@WebServlet("/historylocationnew")
public class HistorylocationNewServlet extends HttpServlet{
		public void  doGet(HttpServletRequest request ,HttpServletResponse response)throws ServletException,IOException {
			request.setCharacterEncoding("utf-8");  
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			
			String shouhuan_id =request.getParameter("shouhuan_id");
			
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session =sessionFactory.openSession();
			Map<String, String>data =new HashMap<String, String>();
			try {
				SQLQuery sqlQuery = session.createSQLQuery("select * from dbo.[historylocation] h where shouhuan_id =:shouhuan_id and time =( select max(time)as t from dbo.[historylocation] h where shouhuan_id =:shouhuan_id )").addEntity(Historylocation.class);
				sqlQuery.setString("shouhuan_id", shouhuan_id);
				Historylocation historylocation =(Historylocation) sqlQuery.uniqueResult();
				System.out.println(historylocation.getLat());
				
				data.put("code", "100");
				data.put("msg", "位置信息");
				data.put("location", "("+historylocation.getLng()+","+historylocation.getLat()+")");
				
			} catch (Exception e) {
				// TODO: handle exception
				
				data.put("code", "500");
				data.put("msg", "system error!");
				data.put("data", "");
			}
			
			
			
		}
	
}

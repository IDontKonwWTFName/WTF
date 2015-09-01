package com.platform.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebServlet("/getnewlocation")
//getnewlocation
//post
//shouhuan_id
//user_id
/** * @author  作者 E-mail: * @date 创建时间：2015年7月29日 上午11:45:23 * @version 1.0 * @parameter  * @since  * @return  */
//getnewlocation
//get
//shouhuan_id
//user_id
public class GetNewLocationServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		String shouhuan_idS=request.getParameter("shouhuan_id");
		String user_id=request.getParameter("user_id");
		
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		
		try {
			SQLQuery sqlQuery=session.createSQLQuery("select * from dbo.[historylocation] where shouhuan_id =:shouhuan_id ");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}

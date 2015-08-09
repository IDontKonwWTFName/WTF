package com.platform.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** * @author  作者 E-mail: * @date 创建时间：2015年8月9日 下午2:06:21 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/versiondownload")
public class VersionDownloadServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		String app_type =request.getParameter("app_type");
		
		SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		
		try {
			SQLQuery sqlQuery=session.createSQLQuery("select * from dbo.[app_version] where version_id=(select max(version_id) from dbo.[app_version] where app_type=:app_type)" );
			//SQLQuery sqlQuery=session.createSQLQuery("select max(version_code) from dbo.[app_version] where app_type=1");
			sqlQuery.setString("app_type", app_type);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}
}

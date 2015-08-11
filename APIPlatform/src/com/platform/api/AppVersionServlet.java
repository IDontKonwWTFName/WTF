package com.platform.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import net.sf.json.JSONObject;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.App_Version;

/** * @author  作者 E-mail: * @date 创建时间：2015年8月9日 下午2:06:21 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/appversion")
public class AppVersionServlet extends HttpServlet{
	@Override
	// appversion get 
	// app_type user_id
	// 返回 version_code,version_name
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		String app_type =request.getParameter("app_type");
		System.out.println(app_type);
		
		SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		
		Map<String, String>data=new HashMap<String, String>();
		
		try {
			System.out.println("1");
			SQLQuery sqlQuery=session.createSQLQuery("select * from dbo.[app_version] where version_id=(select max(version_id) from dbo.[app_version] where app_type=1)").addEntity(App_Version.class);
			//SQLQuery sqlQuery=session.createSQLQuery("select max(version_code) from dbo.[app_version] where app_type=1");
			//sqlQuery.setString("app_type", app_type);
			App_Version app_Version=(App_Version) sqlQuery.uniqueResult();
			System.out.println("1");
			JSONObject jsonObject=new JSONObject();
			
			jsonObject.put("version_code", app_Version.getVersion_code());
			jsonObject.put("version_name", app_Version.getVersion_name());
			System.out.println("1");
			data.put("code", "100");
			data.put("msg", "success");
			data.put("data",jsonObject.toString());
			response.getWriter().println(JSONObject.fromObject(data).toString());
			System.out.println(jsonObject.toString());
		} catch (Exception e) {
			// TODO: handle exception
			data.put("code", "500");
			data.put("msg", "sql error");
			data.put("data", "");
			response.getWriter().println(JSONObject.fromObject(data).toString());

		}
		
		
		
	}
	
	@Override
	
	// 下载最新版本
	// appversion post
	// user_id,app_type
	// 返回文件
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/x-json");
        
       
        String user_id =request.getParameter("user_id");
        String app_type = request.getParameter("app_type");
        
        System.out.println(user_id+"---"+app_type);
		
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
	    Session session=sessionFactory.openSession();
		
		Map< String, String>data =new HashMap<String, String>();
		
		String url="PushDemo.apk";
		try {
			
//				SQLQuery sqlQuery =session.createSQLQuery("select app_url from dbo.[app_version] where version_code=(select max(version_code) from dbo.[app_version] where app_type=1)").addEntity(App_Version.class);
//				//sqlQuery.setString("app_type", app_type);
//				
//				url=(String) sqlQuery.uniqueResult();
				System.out.println(url);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sql error");
			data.put("code", "500");
			data.put("msg", "sql error");
			data.put("data", "");
			response.getWriter().println(JSONObject.fromObject(data).toString());
		}finally{
			session.close();
			sessionFactory.close();
		}
		if (url!=null){
			try {
				//File f=new File("C:/apache-tomcat-7.0.62/webapps/APIPlatform/upload/"+info.getHeadiconurl());
			    File f =new File("C:/Users/军/Desktop/data/AppVersion/"+url);
		        FileInputStream fi=new FileInputStream(f);
		        byte temp[]=new byte[1024*3];//3M的空间
		        //回复
		        response.setContentType("multipart/form-data");
		        response.setContentLength((int)f.length());
		        OutputStream output=response.getOutputStream();
		        while(fi.read(temp)!=-1)
		        {
		        	output.write(temp);
		        }
		        
		        System.out.println("下载app成功");
				
			} catch (Exception e) {
				// TODO: handle exception
				data.put("code", "500");
				data.put("msg", "open file error");
				data.put("data", "");
				response.getWriter().println(JSONObject.fromObject(data).toString());
			}
			
			
		}
		
		
		
		
        
		
	
		
		
	}
	
}

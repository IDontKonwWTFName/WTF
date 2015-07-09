package com.platform.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;







import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.MidiDevice.Info;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.User_info;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet{

	public void  doPost(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException,IOException{
		
		//String dataDirectory =request.getServletContext().getRealPath("WEB-INF/data");
		
		
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		org.hibernate.Session session = sessionFactory.openSession();
		Transaction transaction =session.beginTransaction();
		
		String id_by_session="18646098148";
		User_info info = null;
		try {
			//SQLQuery sqlQuery=((SQLQuery) session.createQuery("select headiconurl " +" from dbo.[user_info] "+" where user_id= "id_by_session)).addEntity(User_info.class);
			
			SQLQuery query = session.createSQLQuery("select headiconurl from user_info where user_id="+id_by_session).addEntity(User_info.class);
		     info=(User_info)query.uniqueResult();
		    System.out.println(info.getHeadiconurl());
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			session.close();
			sessionFactory.close();
		}
		
		
		File f=new File("C:/apache-tomcat-7.0.62/webapps/APIPlatform/upload/"+info.getHeadiconurl());
	    
        FileInputStream fi=new FileInputStream(f);
        byte temp[]=new byte[1024];
        //»Ø¸´
        response.setContentType("multipart/form-data");
        response.setContentLength((int)f.length());
        OutputStream output=response.getOutputStream();
        while(fi.read(temp)!=-1)
        {
        	output.write(temp);
        }
        System.out.println("download");
		
	}

}

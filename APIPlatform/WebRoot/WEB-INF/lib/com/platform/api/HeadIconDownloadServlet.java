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
import javax.sound.midi.MidiDevice.Info;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.User_info;

@WebServlet("/headicondownload")
public class HeadIconDownloadServlet extends HttpServlet{
	//获取头像
	//headicondownload  post
	//download_id ,user_id,type
	//download_id,下载头像的ID，type指手环还是用户类型，手环0，用户1
	public void  doPost(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException,IOException{
		
		//String dataDirectory =request.getServletContext().getRealPath("WEB-INF/data");
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/x-json");
        
        String download_id = request.getParameter("download_id");
        String user_id =request.getParameter("user_id");
        String type = request.getParameter("type");
        
        
		
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		org.hibernate.Session session = sessionFactory.openSession();
		Transaction transaction =session.beginTransaction();
		Map< String, String>data =new HashMap<String, String>();
		
		String url=null;
		try {
			//SQLQuery sqlQuery=((SQLQuery) session.createQuery("select headiconurl " +" from dbo.[user_info] "+" where user_id= "id_by_session)).addEntity(User_info.class);
			if (type.equals("0")){
				//shouhuan
				SQLQuery sqlQuery =session.createSQLQuery("select headiconurl from dbo.[shouhuan] where shouhuan_id=:shouhuan_id");
				sqlQuery.setString("shouhuan_id", download_id);
				url=(String) sqlQuery.uniqueResult();
			}
			if (type.equals("1")){
				//user_id
				SQLQuery sqlQuery = session.createSQLQuery("select headiconurl from dbo.[user_info] where user_id=:user_id");
			    sqlQuery.setString("user_id", download_id);
				url=(String) sqlQuery.uniqueResult();
			   
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
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
			    File f =new File("D:/data/HeadIcon/"+url);
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

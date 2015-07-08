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

@WebServlet("/download")
public class DownloadServlet extends HttpServlet{

	public void  doPost(HttpServletRequest request ,HttpServletResponse response)
			throws ServletException,IOException{
		
		//String dataDirectory =request.getServletContext().getRealPath("WEB-INF/data");
		
		
		
		File f=new File("C:/apache-tomcat-7.0.62/webapps/APIPlatform/upload/1436259240590.txt");
	    
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

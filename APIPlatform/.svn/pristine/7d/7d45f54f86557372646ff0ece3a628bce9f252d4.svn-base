package com.platform.api;

import java.io.IOException;

import javax.mail.Part;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@MultipartConfig(location="")
@WebServlet("/getpartbody")
public class GetPartBody extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
				throws ServletException,IOException{
		request.setCharacterEncoding("UTF-8");
		Part part = (Part) request.getPart("filename");
		System.out.println("1");
		((javax.servlet.http.Part) part).write(getServletContext().getRealPath("/WEB-INF"));
		
	}

}

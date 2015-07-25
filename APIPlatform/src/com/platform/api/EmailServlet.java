package com.platform.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.a.mail.SendMailDemo;

/** * @author  ���� E-mail: * @date ����ʱ�䣺2015��7��25�� ����5:59:40 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/email")
public class EmailServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		String address=request.getParameter("address");
		System.out.println(address);
		new SendMailDemo(address).send();
		System.out.println("ok");
		
		
		
	}
}

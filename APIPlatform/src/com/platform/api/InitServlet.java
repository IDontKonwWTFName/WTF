package com.platform.api;

import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import sepim.server.Server;

/** * @author  ���� E-mail: * @date ����ʱ�䣺2015��7��22�� ����7:49:48 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/init")

public class InitServlet extends HttpServlet { 

private static final long serialVersionUID = 1L; 

public void init() throws ServletException{
	new Server();
} 

} 

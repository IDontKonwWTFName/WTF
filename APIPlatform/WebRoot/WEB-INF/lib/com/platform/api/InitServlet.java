package com.platform.api;

import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import sepim.server.Server;

/** * @author  作者 E-mail: * @date 创建时间：2015年7月22日 下午7:49:48 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/init")

public class InitServlet extends HttpServlet { 

private static final long serialVersionUID = 1L; 

public void init() throws ServletException{
	new Server();
} 

} 

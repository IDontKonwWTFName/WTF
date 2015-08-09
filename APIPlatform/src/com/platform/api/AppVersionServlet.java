package com.platform.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** * @author  作者 E-mail: * @date 创建时间：2015年8月6日 上午9:36:02 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/appversion")
//
//
public class AppVersionServlet extends HttpServlet{
	@Override
	//appversion get
	//user_id
	// 返回  版本信息
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	//下载最新APP文件
	//appversion post
	//user_id
	//返回文件
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
}

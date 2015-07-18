package com.platform.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** * @author  作者 E-mail: * @date 创建时间：2015年7月18日 上午9:33:31 * @version 1.0 * @parameter  * @since  * @return  */
//得到某时间带你以前的报警信息和位置信息
@WebServlet("/HistoryLocationAndRecord")
public class HistoryLocationAndRecordServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		
		
		
	}

}

package com.platform.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/** * @author  作者 E-mail: * @date 创建时间：2015年7月30日 上午10:17:56 * @version 1.0 * @parameter  * @since  * @return  */
public class A extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		Map<String, String>data=new HashMap<String, String>();
		
		
		response.getWriter().println(JSONObject.fromObject(data).toString());
	}

}

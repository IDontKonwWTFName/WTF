package com.platform.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** * @author  ���� E-mail: * @date ����ʱ�䣺2015��7��18�� ����9:33:31 * @version 1.0 * @parameter  * @since  * @return  */
//�õ�ĳʱ�������ǰ�ı�����Ϣ��λ����Ϣ
@WebServlet("/HistoryLocationAndRecord")
public class HistoryLocationAndRecordServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		
		
		
	}

}

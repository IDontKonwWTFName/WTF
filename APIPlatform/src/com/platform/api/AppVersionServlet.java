package com.platform.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** * @author  ���� E-mail: * @date ����ʱ�䣺2015��8��6�� ����9:36:02 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/appversion")
//
//
public class AppVersionServlet extends HttpServlet{
	@Override
	//appversion get
	//user_id
	// ����  �汾��Ϣ
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	//��������APP�ļ�
	//appversion post
	//user_id
	//�����ļ�
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
}

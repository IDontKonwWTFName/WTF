package com.a.aPay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** * @author  ���� E-mail: * @date ����ʱ�䣺2015��8��7�� ����8:48:00 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/RefundsSucceeded")
//�˿�ɹ�
public class RefundsSucceededServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
	}
}

package com.a.aPay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** * @author  作者 E-mail: * @date 创建时间：2015年8月7日 下午8:48:00 * @version 1.0 * @parameter  * @since  * @return  */
@WebServlet("/RefundsSucceeded")
//退款成功
public class RefundsSucceededServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
	}
}

package com.platform.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

import com.platform.model.Historylocation;

/**
 * * @author ���� E-mail: * @date ����ʱ�䣺2015��7��23�� ����6:32:52 * @version 1.0 * @parameter
 * * @since * @return
 */
// post �����λ����Ϣ

@WebServlet("/getshouhuanlatestlocation")
public class GetShouhuanLatestLocationServlet extends HttpServlet {

	//get����λ��
	//getshouhuanlatestlocation
	//����:post
	//shouhuan_id
	//user_id
	//���أ�location(XX,XX)
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String shouhuan_id = request.getParameter("shouhuan_id");
		String user_id= request.getParameter("user_id");

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Map<String, String> data = new HashMap<String, String>();
		try {
			SQLQuery sqlQuery = session
					.createSQLQuery(
							"select * from dbo.[historylocation] h where shouhuan_id =:shouhuan_id and time =( select max(time)as t from dbo.[historylocation] h where shouhuan_id =:shouhuan_id )")
					.addEntity(Historylocation.class);
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			Historylocation historylocation = (Historylocation) sqlQuery
					.uniqueResult();
			System.out.println(historylocation.getLat());

			data.put("code", "100");
			data.put("msg", "λ����Ϣ");
			data.put("location",  historylocation.getLng() + ","
					+ historylocation.getLat() );
			

		} catch (Exception e) {
			// TODO: handle exception

			data.put("code", "500");
			data.put("msg", "system error!");
			data.put("data", "");
		}
		response.getWriter().println(JSONObject.fromObject(data).toString());
		System.out.println(JSONObject.fromObject(data).toString());

	}

}
 
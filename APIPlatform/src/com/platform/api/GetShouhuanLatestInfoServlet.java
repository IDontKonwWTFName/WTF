package com.platform.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

import com.platform.model.Fence;
import com.platform.model.Historylocation;

/**
 * * @author ���� E-mail: * @date ����ʱ�䣺2015��7��23�� ����6:32:52 * @version 1.0 * @parameter
 * * @since * @return
 */
// post �����λ����Ϣ

@WebServlet("/getshouhuanlatestinfo")
public class GetShouhuanLatestInfoServlet extends HttpServlet {

	//�õ��ֻ�������Ϣ
	//getshouhuanlatestinfo
	//����:post
	//shouhuan_id
	//user_id
	//���أ�location(XX,XX),bat(XX),online(0/1),gprs(XX)
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String shouhuan_id = request.getParameter("shouhuan_id");

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
			//�õ�fence
			SQLQuery sqlQuery2 = session.createSQLQuery(
					"select * from dbo.[fence] where shouhuan_id=:shouhuan_id")
					.addEntity(Fence.class);
			sqlQuery2.setString("shouhuan_id", shouhuan_id);
			List<Fence> fences = sqlQuery2.list();
			// List to JSONArray
			JSONArray jsonArray = new JSONArray().fromObject(fences);

			data.put("code", "100");
			data.put("msg", "λ����Ϣ");
			data.put("location", historylocation.getLng() + ","
					+ historylocation.getLat() );
			data.put("bat", "75");
			data.put("online", "1");
			data.put("gprs", "89");
			data.put("fence", jsonArray.toString());

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
 
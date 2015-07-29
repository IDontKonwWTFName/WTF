package com.platform.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.AmbiguousResolutionException;
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
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.hql.internal.ast.SqlASTFactory;

import com.a.push.Push;
import com.mysql.jdbc.BufferRow;
import com.platform.model.Relation;

@WebServlet("/addrelation")
// ����ֻ�,����ֻ�Ҫ���ж��Ƿ�ע��ǰ�й���Ա�����У����Ⱦ���ͬ�⣬������Ϊ����Ա
/** * @author  ���� E-mail: * @date ����ʱ�䣺2015��7��26�� ����6:34:21 * @version 1.0 * @parameter  * @since  * @return  */
public class AddRelationServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String shouhuanid = request.getParameter("shouhuan_id");

		response.setContentType("text/x-json");

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			SQLQuery sqlQuery = session
					.createSQLQuery(
							"select * from dbo.[relation] where shouhuan_id=:shouhuan_id")
					.addEntity(Relation.class);
			// sqlQuery.setString("pshouhuan_id", "shouhuan_id");
			sqlQuery.setString("shouhuan_id", shouhuanid);
			List<Relation> info = sqlQuery.list();

			data.put("code", "100");
			data.put("msg", "��ȡ���ݳɹ�");
			data.put("data", JSONArray.fromObject(info).toString());

			out.println(JSONObject.fromObject(data).toString());
		} catch (Exception e) {
			data.put("code", "200");
			data.put("msg", "��ȡ����ʧ��");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		} finally {
			session.close();
			sessionFactory.close();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// ����user_id����Ҫ���relation����Ϣ���ж��Ƿ���Լӣ��������͸�����Ա
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		// ����
		String shouhuan_id = request.getParameter("shouhuan_id");
		String set_userid = request.getParameter("set_userid");
		String relation = request.getParameter("relation");
		String powerString = request.getParameter("power");
		int power = Integer.parseInt(powerString);
		// int power=8;
		String user_id = request.getParameter("user_id");

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String admin_id = null;
		// ��ѯ�ֻ��������Ƿ��й���Ա������У��Ƚ�user_id��admin_id
		try {
			SQLQuery sqlQuery = session
					.createSQLQuery("select user_id from dbo.[relation] where shouhuan_id=:shouhuan_id and administor=1");
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			admin_id = (String) sqlQuery.uniqueResult();
			System.out.println("admin_id:" + admin_id);
			System.out.println("user_id:" + user_id);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sql error");

		}

		// List<String> admin_id=sqlQuery.list();
		// ���û�й���Ա��ֱ�Ӳ��룬���ҳ�Ϊ����Ա
		if (admin_id == null) {
			Relation insertRelation = new Relation();
			insertRelation.setShouhuan_id(shouhuan_id);
			insertRelation.setUser_id(set_userid);
			insertRelation.setPower(power);
			insertRelation.setRelation(relation);
			insertRelation.setAdministor(1);

			session.save(insertRelation);
			transaction.commit();
			System.out.println("null");
		} else {
			if (admin_id.equals(user_id)) {
				// ���user_id���ǹ���Ա����ô��������������set_useridΪ������
				Relation insertRelation = new Relation();
				insertRelation.setShouhuan_id(shouhuan_id);
				insertRelation.setUser_id(set_userid);
				insertRelation.setPower(power);
				insertRelation.setRelation(relation);
				insertRelation.setAdministor(0);

				session.save(insertRelation);
				transaction.commit();
				System.out.println("1");
			} else {
				// ���͸�admin������������
				// Ӧ�� ��admin_id��ѯchannelID
				String channelID = null;
				channelID = "3473377944743044766";

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("sign", "addRelation");
				jsonObject.put("shouhuan_id", shouhuan_id);
				jsonObject.put("set_userid", set_userid);
				jsonObject.put("power", power);
				jsonObject.put("relation", relation);
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("title", "addRelation");
				jsonObject2.put("description", jsonObject.toString());
				new Push().pushToApp(channelID, jsonObject2.toString());
				System.out.println("���͸�����Ա��");

			}
		}

		// if ()

	}

	// �޸�relation
	// user_id ���ͷ�
	// shouhuan_id �ֻ�_id
	// which,�޸�ʲô
	// value���޸ĵ�ֵ
	// set_id���޸ĵ�id
	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		JSONObject jsonObject = new JSONObject().fromObject(sb.toString());

		String user_id = null;
		String shouhuan_id = null;
		String set_id = null;
		String which = null;
		String value = null;

		user_id = jsonObject.getString("user_id");
		shouhuan_id = jsonObject.getString("shouhuan_id");
		set_id = jsonObject.getString("set_id");
		which = jsonObject.getString("which");
		value = jsonObject.getString("value");

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		String admin_id = null;
		Map<String, String> data=new HashMap<String,String>();

		try {
			SQLQuery sqlQuery = session
					.createSQLQuery("select user_id from dbo.[relation] where shouhuan_id=:shouhuan_id and administor=1");
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			admin_id = (String) sqlQuery.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sql error");
		}

		if (admin_id == null) {
			// ϵͳ����
			data.put("code", "500");
			data.put("msg", "ϵͳ����");
			data.put("data", "");

		} else if (admin_id.equals(user_id)) {
			SQLQuery sqlQuery2 = session
					.createSQLQuery("update dbo.[relation] set "+ which +"= '"+ value +"' where shouhuan_id=:shouhuan_id and user_id =:set_id");
			
//			sqlQuery2.setString("which", which);
//			sqlQuery2.setString("value", value);
			sqlQuery2.setString("shouhuan_id", shouhuan_id);
			sqlQuery2.setString("set_id", set_id);
			sqlQuery2.executeUpdate();
			transaction.commit();
			data.put("code", "100");
			data.put("msg", "���ĳɹ���");
			data.put("data", "");
			System.out.println("update+++++++++++++++++++++++++++++++");
		} else {
			// ������ǹ���Ա�����͸�����Ա���ù���Ա���޸�Ȩ��
			JSONObject jsonObject2=new JSONObject();
			jsonObject2.put("set_id", set_id);
			jsonObject2.put("shouhuan_id", shouhuan_id);
			jsonObject2.put("which", which);
			jsonObject2.put("value", value);
			
			
			
			
			JSONObject jsonObject_Push=new JSONObject();
			data.put("code", "200");
			data.put("msg", "���͵�����Ա");
			data.put("data", "");
		}
		response.getWriter().println(JSONObject.fromObject(data).toString());

	}

	@Override
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		// request��get

	}

}

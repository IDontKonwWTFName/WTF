package com.platform.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.*;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String userid = request.getParameter("user_id");
		System.out.println("User: " + userid);
		response.setContentType("text/x-json");

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

		if (userid == null || userid.equals("")) {
			data.put("code", "200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			return;
		}

		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session s = sf.openSession();

		try {
			SQLQuery query = s.createSQLQuery(
					"select * from user where user_id=" + userid).addEntity(
					User.class);
			User info = (User) query.uniqueResult();
			System.out.println(info.getUser_id() + " " + info.getPassword());

			data.put("code", "100");
			data.put("msg", "获取数据成功");
			data.put("data", JSONObject.fromObject(info).toString());

			out.println(JSONObject.fromObject(data).toString());
		} catch (Exception e) {
			data.put("code", "200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		} finally {
			s.close();
			sf.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// 修改密码
	// user post
	// user_id,password,new_password
	//
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String user_id = request.getParameter("user_id");
		String password = request.getParameter("password");
		String new_password = request.getParameter("new_password");

		System.out.println("User: " + user_id + " " + password+"--"+new_password);

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			SQLQuery sqlQuery = session
					.createSQLQuery("select * from dbo.[user] where user_id=:user_id").addEntity(User.class);
			sqlQuery.setString("user_id", user_id);
			User user = (User) sqlQuery.uniqueResult();
			// 验证
			if (user != null && user.getPassword().equals(password)) {
				SQLQuery sqlQuery2 = session
						.createSQLQuery("update dbo.[user] set password=:new_password where user_id=:user_id");
				sqlQuery2.setString("new_password", new_password);
				sqlQuery2.setString("user_id", user_id);
				sqlQuery2.executeUpdate();

				transaction.commit();
				data.put("code", "100");
				data.put("msg", "更新密码成功");
				data.put("data", "");
				out.println(JSONObject.fromObject(data).toString());

			}

		} catch (Exception e) {
			transaction.rollback();
			data.put("code", "200");
			data.put("msg", "更新密码失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			// System.out.println(line);
			sb.append(line);
		}
		System.out.println(sb.toString());
		JSONObject jo = JSONObject.fromObject(sb.toString());
		String id = null;
		String which = null;
		String value = null;
		response.setContentType("text/x-json");

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		try {
			id = jo.getString("user_id");
			which = jo.getString("which");
			value = jo.getString("value");
		} catch (Exception e) {
			data.put("code", "200");
			data.put("msg", "修改数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
			return;
		}

		System.out.println("User(update): " + which + "->" + value);

		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();

		try {
			String sql = "update user set " + which + " = " + value
					+ " where user_id =" + id;
			System.out.println(sql);
			SQLQuery query = s.createSQLQuery(sql);
			query.addEntity(User.class);
			// SQLQuery query =
			// s.createSQLQuery("update user set ? = ? where user_id = ?");
			// query.setParameter(0, which);
			// query.setParameter(1, value);
			// query.setParameter(2, id);
			query.executeUpdate();
			t.commit();

			data.put("code", "100");
			data.put("msg", "删除数据成功");
			data.put("data", "");

			out.println(JSONObject.fromObject(data).toString());

		} catch (Exception e) {
			data.put("code", "200");
			data.put("msg", "修改数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		} finally {
			s.close();
			sf.close();
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("user_id");
		System.out.println("User(delete): " + id);
		response.setContentType("text/x-json");

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

		if (id == null || id.equals("")) {
			data.put("code", "200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			return;
		}

		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();

		try {
			SQLQuery query = s
					.createSQLQuery("delete from user where user_id=?");
			query.addEntity(User.class);
			query.setParameter(0, id);
			query.executeUpdate();
			t.commit();

			data.put("code", "100");
			data.put("msg", "获取数据成功");
			data.put("data", "");

			out.println(JSONObject.fromObject(data).toString());
		} catch (Exception e) {
			data.put("code", "200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		} finally {
			s.close();
			sf.close();
		}
	}

}

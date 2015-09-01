package com.platform.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.MidiDevice.Info;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.*;

/**
 * Servlet implementation class FenceServlet
 */
@WebServlet("/fence")
public class FenceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FenceServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// fence
	// get
	// shouhuan_id,user_id
	// 返回JSONArray，fences
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String user_id = request.getParameter("user_id");
		String shouhuan_id = request.getParameter("shouhuan_id");
		System.out.println("Shouhuan_id: " + shouhuan_id);

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

		if (shouhuan_id == null || shouhuan_id.equals("")) {
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
			SQLQuery sqlQuery = s.createSQLQuery(
					"select * from dbo.[fence] where shouhuan_id=:shouhuan_id")
					.addEntity(Fence.class);
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			List<Fence> fences = sqlQuery.list();

			// List to JSONArray
			JSONArray jsonArray = new JSONArray().fromObject(fences);

			data.put("code", "100");
			data.put("msg", "获取数据成功");
			data.put("data", jsonArray.toString());

			out.println(JSONObject.fromObject(data).toString());
			System.out.println(JSONObject.fromObject(data).toString());
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
	// 添加围栏
	// post:fence_name,shouhuan_id,user_id,fence(points),type,time
	// 返回:code,data:fence_id
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String fence = request.getParameter("fence");
		String fence_name = request.getParameter("fence_name");
		String shouhuan_id = request.getParameter("shouhuan_id");
		String user_id = request.getParameter("user_id");
		String timesString=request.getParameter("time");

		// Integer id = 0;
		Integer type = 0;

		try {
			// id = Integer.valueOf(request.getParameter("fence_id"));
			type = Integer.valueOf(request.getParameter("type"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		System.out.println("Fence: " + fence + " " + fence_name + " "
				+ shouhuan_id + " " + user_id + " " + type+" "+timesString);

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();

		try {

			Fence fen = new Fence();

			fen.setFence(fence);
			// fen.setFence_id(id);
			fen.setFence_name(fence_name);
			fen.setShouhuan_id(shouhuan_id);
			fen.setUser_id(user_id);
			fen.setType(type);
			fen.setTime(timesString);

			s.save(fen);

			// 添加围栏，返回fence_id
			String fence_id = fen.getFence_id().toString();
			t.commit();

			data.put("code", "100");
			data.put("msg", "添加数据成功");
			data.put("data", fence_id);

			out.println(JSONObject.fromObject(data).toString());
			System.out.println(JSONObject.fromObject(data).toString());
		} catch (Exception e) {
			t.rollback();
			data.put("code", "200");
			data.put("msg", "添加数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		} finally {
			s.close();
			sf.close();
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	// put
	// /修改围栏围栏
	// post:fence_id,fence_name,shouhuan_id,user_id,fence(points),sign
	// 返回:code,data
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String fence_id = request.getParameter("fence_id");
		String fence = request.getParameter("fence");
		String fence_name = request.getParameter("fence_name");
		String shouhuan_id = request.getParameter("shouhuan_id");
		String user_id = request.getParameter("user_id");
		String sign = request.getParameter("sign");

		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(request.getInputStream()));
		// String line;
		// StringBuilder sb = new StringBuilder();
		// while ((line = in.readLine()) != null)
		// {
		// // System.out.println(line);
		// sb.append(line);
		// }
		// System.out.println(sb.toString());
		// JSONObject jo = JSONObject.fromObject(sb.toString());
		// String id = null;
		// String which = null;
		// String value = null;
		// response.setContentType("text/x-json");
		//
		// PrintWriter out = response.getWriter();
		// Map<String, String> data = new HashMap<String, String>();
		// try{
		// id = jo.getString("fence_id");
		// which = jo.getString("which");
		// value = jo.getString("value");
		// }catch(Exception e)
		// {
		// data.put("code","200");
		// data.put("msg", "修改数据失败");
		// data.put("data", "");
		// e.printStackTrace();
		// out.println(JSONObject.fromObject(data).toString());
		// return;
		// }

		// System.out.println("Fence(update): "+which+"->"+value);

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Map<String, String>data=new HashMap<String,String>();

		try {

			SQLQuery sqlQuery = session
					.createSQLQuery("update dbo.[fence] set fence_name=:fence_name, fence=:fence,user_id=:user_id,sign=:sign where fence_id =:fence_id");
			sqlQuery.addEntity(Fence.class);
			
			sqlQuery.setString("fence_name", fence_name);
			sqlQuery.setString("fence", fence);
			sqlQuery.setString("sign", sign);
			sqlQuery.setString("user_id", user_id);
			
			sqlQuery.executeUpdate();
			transaction.commit();

			data.put("code", "100");
			data.put("msg", "跟新数据成功");
			data.put("data", "");

			response.getWriter().println(JSONObject.fromObject(data).toString());

		} catch (Exception e) {
			data.put("code", "200");
			data.put("msg", "删除数据失败");
			data.put("data", "");
			e.printStackTrace();
			response.getWriter().println(JSONObject.fromObject(data).toString());
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */

	// delete fence
	// fence_id,user_id

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String fence_id = request.getParameter("fence_id");

		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();

		

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			SQLQuery query = session
					.createSQLQuery("delete from dbo.[fence] where fence_id=:fence_id");
			query.addEntity(Fence.class);
			query.setString("fence_id", fence_id);
			query.executeUpdate();
			transaction.commit();

			data.put("code", "100");
			data.put("msg", "删除数据成功");
			data.put("data", "");

			out.println(JSONObject.fromObject(data).toString());
			System.out.println("Fence_id(delete): " + fence_id);
		} catch (Exception e) {
			data.put("code", "200");
			data.put("msg", "删除数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		} finally {
			session.close();
			sessionFactory.close();
		}
	}
}

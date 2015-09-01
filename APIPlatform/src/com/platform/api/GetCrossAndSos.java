package com.platform.api;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.platform.model.Cross_fence;
import com.platform.model.Historyrecord;
import com.platform.model.Shouhuan_log;

@WebServlet("/getcrossandsos")
public class GetCrossAndSos extends HttpServlet {

	// 获取各种报警信息
	// getcrossandsos post user_id shouhuan_id start_time end_time
	// 返回jsonArray，code,msg,data(sign,XX,xx))
	// sign=cross,(point,time,,fence_id,InOut)
	// sign=2，3，5,(point,time) SOS5，低电报警2,手环拆除报警3

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("getcrossandsos");

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");

		String user_id = request.getParameter("user_id");
		String shouhuan_id = request.getParameter("shouhuan_id");
		// Date endDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// String end_time = dateFormat.format(endDate);
		// String start_time = "2000-01-01 00:00:00";
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");

		System.out.println(shouhuan_id +"--"+ start_time + "-----" + end_time);

		Map<String, String> data = new HashMap<String, String>();

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		// 得到穿越围栏数据
		Iterator<Cross_fence> crossIterator = null;
		try {
			SQLQuery sqlCross = session
					.createSQLQuery(
							"select c.* "
									+ "from dbo.[cross_fence] c join dbo.[fence] f "
									+ "on c.fence_id=f.fence_id "
									+ "where c.time >:start_time and c.time<:end_time and f.shouhuan_id=:shouhuan_id "
									+ "order by time desc").addEntity(
							Cross_fence.class);
			sqlCross.setString("start_time", start_time);
			sqlCross.setString("end_time", end_time);
			sqlCross.setString("shouhuan_id", shouhuan_id);
			java.util.List<Cross_fence> cross = sqlCross.list();

			crossIterator = cross.iterator();

			// while (crossIterator.hasNext()) {
			// System.out.println("cross---"+crossIterator.next().getTime());
			// }
		} catch (Exception e) {
			// TODO: handle exception
			data.put("code", "500");
			data.put("msg", "系统错误");
			data.put("data", "");

		}

		// sos的type设置成为5
		// 查找
		Iterator<Shouhuan_log> sosIterator = null;
		try {
			SQLQuery sqlSos = session
					.createSQLQuery(
							"select * from dbo.[shouhuan_log] where shouhuan_id=:shouhuan_id and (log_type=5 or log_type=2 or log_type=3) and time>:start_time and time<:end_time")
					.addEntity(Shouhuan_log.class);
			sqlSos.setString("shouhuan_id", shouhuan_id);
			sqlSos.setString("start_time", start_time);
			sqlSos.setString("end_time", end_time);
			java.util.List<Shouhuan_log> sos = sqlSos.list();

			sosIterator = sos.iterator();

			// while (sosIterator.hasNext()) {
			// System.out.println("sos---"+sosIterator.next().getTime());
			// }

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sql error");
			data.put("code", "500");
			data.put("msg", "系统错误");
			data.put("data", "");
		}

		Cross_fence cross_fence = new Cross_fence();
		// Historyrecord historyrecord = new Historyrecord();
		Shouhuan_log shouhuan_log = new Shouhuan_log();

		boolean crossFlag = true;
		boolean shouhuan_logFlag = true;
		boolean f1 = true;
		boolean f2 = true;
		// boolean flag = true;
		Date start = null;

		JSONObject jsonObject = null;// new JSONObject();

		JSONArray jsonArray = new JSONArray();

		try {
			start = dateFormat.parse("2000-01-01 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 取数据，放在JSONArray里面

		if (crossFlag) {
			//
			if (crossIterator.hasNext()) {
				cross_fence = crossIterator.next();
				crossFlag = false;
			} else {
				cross_fence.setTime(start);
				f1 = false;
			}
		}

		if (shouhuan_logFlag) {
			if (sosIterator.hasNext()) {
				shouhuan_log = sosIterator.next();
				shouhuan_logFlag = false;

			} else {
				shouhuan_log.setTime(start);
				f2 = false;
			}
		while (f1 || f2) {

			

				if (cross_fence.getTime().after(shouhuan_log.getTime())) {
					// cross
					System.out.println("cross");
					jsonObject = new JSONObject();
					jsonObject.put("sign", "cross");

					jsonObject.put("point", cross_fence.getLng() + ","
							+ cross_fence.getLat());
					jsonObject.put("fence_id", cross_fence.getFence_id());
					jsonObject.put("time",
							dateFormat.format(cross_fence.getTime()));
					jsonObject.put("InOut", cross_fence.getIn_Out());

					jsonArray.add(jsonObject);
					crossFlag = true;

				} else {
					// shouhuan_log
					System.out.println("shouhuan_log");
					jsonObject = new JSONObject();
					jsonObject.put("sign", shouhuan_log.getLog_type());

					jsonObject.put("point", shouhuan_log.getEvent());
					jsonObject.put("time",
							dateFormat.format(shouhuan_log.getTime()));

					jsonArray.add(jsonObject);
					shouhuan_logFlag = true;
				}
				if (crossFlag) {
					//
					if (crossIterator.hasNext()) {
						cross_fence = crossIterator.next();
						crossFlag = false;
					} else {
						cross_fence.setTime(start);
						f1 = false;
					}
				}

				if (shouhuan_logFlag) {
					if (sosIterator.hasNext()) {
						shouhuan_log = sosIterator.next();
						shouhuan_logFlag = false;

					} else {
						shouhuan_log.setTime(start);
						f2 = false;
					}
				}

			}
		

		}

		session.close();
		sessionFactory.close();
		// 规定某种规则+++++++++++++++++++++++++++++++++++++++++++++
		// 返回jsonArray
		data.put("code", "100");
		data.put("msg", "成功");
		data.put("data", jsonArray.toString());

		response.getWriter().println(JSONObject.fromObject(data).toString());
		System.out.println(JSONObject.fromObject(data).toString());

	}

}

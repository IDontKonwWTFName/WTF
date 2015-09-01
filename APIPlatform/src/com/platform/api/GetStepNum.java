package com.platform.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import com.platform.model.StepNumHistory;

@WebServlet("/getstepnum")
public class GetStepNum extends HttpServlet {

	// 获取运动量
	// getstepnum post user_id shouhuan_id start_time end_time
	// 返回data为jsonArray，code,msg,data(time,step_num))
		
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("stepnum");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		String user_id = request.getParameter("user_id");
		String shouhuan_id = request.getParameter("shouhuan_id");
		// Date endDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// String end_time = dateFormat.format(endDate);
		// String start_time = "2000-01-01 00:00:00";
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");

		System.out
				.println(shouhuan_id + "--" + start_time + "-----" + end_time);

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Map<String, String> data = new HashMap<String, String>();
		
		try {
			SQLQuery sqlQuery = session
					.createSQLQuery(
							"select * from dbo.[stepnumhistory] where shouhuan_id=:shouhuan_id and time>:start_time and time <:end_time")
					.addEntity(StepNumHistory.class);
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			sqlQuery.setString("start_time", start_time);
			sqlQuery.setString("end_time", end_time);
			
			List<StepNumHistory> stepNumHistories=sqlQuery.list();
			
			JSONObject jsonObject = null;// new JSONObject();

			JSONArray jsonArray = new JSONArray();
			
			for (StepNumHistory stepNumHistory :stepNumHistories){
				jsonObject=new JSONObject();
				
				jsonObject.put("time", dateFormat.format(stepNumHistory.getTime()));
				jsonObject.put("step_num", stepNumHistory.getStepNum());
				
				jsonArray.add(jsonObject);
			}
			data.put("code", "100");
			data.put("msg", "获取数据成功");
			data.put("data", jsonArray.toString());
			response.getWriter().print(JSONObject.fromObject(data).toString());
			System.out.println(data.toString());
			
		} catch (Exception e) {
			// TODO: handle exception
			data.put("code", "500");
			data.put("msg", "系统错误");
			data.put("data", "");
			response.getWriter().print(JSONObject.fromObject(data).toString());
			System.out.println(data.toString());
		}

	}

}

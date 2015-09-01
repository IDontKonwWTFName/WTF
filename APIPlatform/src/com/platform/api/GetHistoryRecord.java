package com.platform.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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

import com.platform.model.Historyrecord;

@WebServlet("/gethistoryrecord")

public class GetHistoryRecord  extends HttpServlet{
	@Override
	//得到10条以内的语音信息
	//gethistoryrecord  post 
	// user_id,shouhuan_id,start_time,end_time
	// 返回jsonArray，code,msg,data(sign,XX,xx)
	//sign=record,(url,from_id,from_type,time)
	//
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		
		String user_id = request.getParameter("user_id");
		String shouhuan_id = request.getParameter("shouhuan_id");
//		Date endDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
//		String end_time = dateFormat.format(endDate);
//		String start_time = "2000-01-01 00:00:00";
		String start_time=request.getParameter("start_time");
		String end_time =request.getParameter("end_time");
		
		System.out.println(shouhuan_id + start_time + "-----" + end_time);
		
		Map<String, String> data = new HashMap<String, String>();

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		try {
			SQLQuery sqlRecord = session
					.createSQLQuery(
							"select * from dbo.[historyrecord] "
									+ "where shouhuan_id =:shouhuan_id and (from_id =:shouhuan_id or from_id =:user_id) and time>:start_time and time<:end_time"
									+ " order by time desc").addEntity(
							Historyrecord.class);
			sqlRecord.setString("shouhuan_id", shouhuan_id);
			sqlRecord.setString("shouhuan_id", shouhuan_id);
			sqlRecord.setString("user_id", user_id);
			sqlRecord.setString("start_time", start_time);
			sqlRecord.setString("end_time", end_time);
			java.util.List<Historyrecord> historyrecord = sqlRecord.list();
			
			//从中筛选10条
			JSONObject jsonObject = null;// new JSONObject();

			JSONArray jsonArray = new JSONArray();
			int i=0;
			int flag=0;
			while (i<historyrecord.size()&&flag<10){
				
				
				jsonObject = new JSONObject();
				
				jsonObject.put("sign", "record");
				jsonObject.put("url", historyrecord.get(i).getRecord_url());
				jsonObject.put("from_id", historyrecord.get(i).getFrom_id());
				jsonObject.put("from_type", historyrecord.get(i).getFrom_type());
				jsonObject.put("time",
						dateFormat.format(historyrecord.get(i).getTime()));
				jsonObject.put("isHeard", historyrecord.get(i).getIsHeard());

				jsonArray.add(jsonObject);
				i++;
				flag++;
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
			//recordIterator = record.iterator();
			
//			while (recordIterator.hasNext()) {
//				System.out.println("record---"+recordIterator.next().getTime());
//			}

		} catch (Exception e) {
			// TODO: handle exception
			data.put("code", "500");
			data.put("msg", "系统错误");
			data.put("data", "");
		}

		
	}
}

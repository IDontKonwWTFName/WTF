package com.platform.api;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javassist.CtBehavior;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;
import javax.xml.crypto.Data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import antlr.collections.List;

import com.platform.model.Cross_fence;
import com.platform.model.Fence;
import com.platform.model.Historyrecord;
import com.platform.model.Shouhuan_log;

/**
 * * @author 作者 E-mail: * @date 创建时间：2015年7月18日 上午9:33:31 * @version 1.0 * @parameter
 * * @since * @return
 */
// 得到某时间带你以前的报警信息和位置信息
// user_id,shouhuan_id,start_time,end_time
// 使用的表：crossfence, historyrecord
// 传文件地址
// 返回jsonArray，code,msg,data(sign,XX,xx)
//sign=record,(url,from_id,from_type,time)
//sign=cross,(point,time,,fence_id,InOut)
//sign=sos,(point,time)
@WebServlet("/HistoryCrossfenceAndRecord")
public class HistoryCrossfenceAndRecordServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");

		// String user_id = request.getParameter("user_id");
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
		
		
		//得到穿越围栏数据
		Iterator<Cross_fence> crossIterator = null;
		try {
			SQLQuery sqlCross = session
					.createSQLQuery(
							"select c.* "
									+ "from dbo.[cross_fence] c join dbo.[fence] f "
									+ "on c.fence_id=f.fence_id "
									+ "where c.time >=:start_time and c.time<=:end_time and f.shouhuan_id=:shouhuan_id "
									+ "order by time desc").addEntity(
							Cross_fence.class);
			sqlCross.setString("start_time", start_time);
			sqlCross.setString("end_time", end_time);
			sqlCross.setString("shouhuan_id", shouhuan_id);
			java.util.List<Cross_fence> cross = sqlCross.list();
			crossIterator = cross.iterator();
			for (int i = 0; i < cross.size() && i < 10; i++) {
				System.out.println("cross------"
						+ cross.get(i).getTime().toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			data.put("code", "500");
			data.put("msg", "系统错误");
			data.put("data", "");
			
		}

	
		Iterator<Historyrecord> recordIterator = null;
		try {
			SQLQuery sqlRecord = session
					.createSQLQuery(
							"select * from dbo.[historyrecord] "
									+ "where shouhuan_id =:shouhuan_id and time>=:start_time and time<=:end_time"
									+ " order by time desc").addEntity(
							Historyrecord.class);
			sqlRecord.setString("shouhuan_id", shouhuan_id);
			sqlRecord.setString("start_time", start_time);
			sqlRecord.setString("end_time", end_time);
			java.util.List<Historyrecord> record = sqlRecord.list();
			recordIterator = record.iterator();
			for (int i = 0; i < record.size() && i < 10; i++) {
				System.out.println("record------"
						+ record.get(i).getTime().toString());
			}

		} catch (Exception e) {
			// TODO: handle exception
			data.put("code", "500");
			data.put("msg", "系统错误");
			data.put("data", "");
		}

		// 少于十个
		
		// sos的type设置成为5
		//查找
		Iterator<Shouhuan_log> sosIterator = null;
		try {
			SQLQuery sqlSos = session
					.createSQLQuery(
							"select * from dbo.[shouhuan_log] where shouhuan_id=:shouhuan_id and log_type=5")
					.addEntity(Shouhuan_log.class);
			sqlSos.setString("shouhuan_id", shouhuan_id);
			java.util.List<Shouhuan_log> sos = sqlSos.list();
			sosIterator = sos.iterator();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sql error");
			data.put("code", "500");
			data.put("msg", "系统错误");
			data.put("data", "");
		}

		Cross_fence cross_fence = new Cross_fence();
		Historyrecord historyrecord = new Historyrecord();
		Shouhuan_log shouhuan_log = new Shouhuan_log();
		boolean crossFlag = true;
		boolean recordFlag = true;
		boolean sosFlag = true;
		int f = 0;
		Date start = null;
	

		try {
			start = dateFormat.parse("2000-01-01 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject jsonObject = null;// new JSONObject();

		JSONArray jsonArray = new JSONArray();
		while (f < 10) {
			// 先取三个数
			if (crossIterator.hasNext()) {
				if (crossFlag) {
					cross_fence = crossIterator.next();
					crossFlag = false;
				}
			} else {
				cross_fence.setTime(start);
			}

			if (recordIterator.hasNext()) {
				if (recordFlag) {
					historyrecord = recordIterator.next();
					recordFlag = false;
				}
			} else {
				historyrecord.setTime(start);
			}

			if (sosIterator.hasNext()) {
				if (sosFlag) {
					shouhuan_log = sosIterator.next();
					sosFlag = false;
				}
			} else {
				shouhuan_log.setTime(start);
			}

			if (cross_fence.getTime().after(historyrecord.getTime())) {
				if (cross_fence.getTime().after(shouhuan_log.getTime())) {
					// cross
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
					// sos
					jsonObject = new JSONObject();
					jsonObject.put("sign", "sos");

					jsonObject.put("point", shouhuan_log.getEvent());
					jsonObject.put("time",
							dateFormat.format(shouhuan_log.getTime()));

					jsonArray.add(jsonObject);
					sosFlag = true;
				}

			} else {
				if (historyrecord.getTime().after(shouhuan_log.getTime())) {
					// record
					jsonObject = new JSONObject();
					jsonObject.put("sign", "record");

					jsonObject.put("url", historyrecord.getRecord_url());
					jsonObject.put("from_id", historyrecord.getFrom_id());
					jsonObject.put("from_type", historyrecord.getFrom_type());
					jsonObject.put("time",
							dateFormat.format(historyrecord.getTime()));

					jsonArray.add(jsonObject);
					recordFlag = true;

				} else {
					// sos
					if (shouhuan_log.getTime().after(start)) {
						jsonObject = new JSONObject();
						jsonObject.put("sign", "sos");

						jsonObject.put("point", shouhuan_log.getEvent());
						jsonObject.put("time",
								dateFormat.format(shouhuan_log.getTime()));

						jsonArray.add(jsonObject);
						sosFlag = true;

					} else {
						f=10;
						
					}

				}
			}
			f++;

		}

		

		// 规定某种规则+++++++++++++++++++++++++++++++++++++++++++++
		// 返回jsonArray
		data.put("code", "100");
		data.put("msg", "成功");
		data.put("data", jsonArray.toString());

		response.getWriter().println(JSONObject.fromObject(data).toString());
		System.out.println(JSONObject.fromObject(data).toString());

		//
		// // 从其中选取10以内个发送,r,c的值都为其下标
		// int c = 0, r = 0,s=0;
		// int flag = 0;
		// int max = 10;
		// boolean bl = true;
		// boolean jug = true;
		// //一方为空时怎么处理
		// //??????????????????????
		// if ((cross.size() + record.size()) > max) {// 如果两个总输小于10，则直接选取
		// while (flag < 10) {
		// if (bl) {// 判断某一个列表是否被取完
		// if (cross.get(c).getTime().before(record.get(r).getTime())) {//
		// 判断大小，然后选择
		// if (cross.get(c).getTime().before(sos.get(s).getTime())) {
		// flag++;
		// //判断是否为遍历完
		// if ((c >= cross.size() - 1)) {
		// bl = false;
		// } else {
		// //并且还没有取到max，则继续取
		// if (flag < max) {
		// c++;
		// }
		// }
		//
		// }else{
		// flag++;
		// //判断是否为遍历完
		// if ((s>= sos.size() - 1)) {
		// bl = false;
		// } else {
		// //并且还没有取到max，则继续取
		// if (flag < max) {
		// s++;
		// }
		// }
		//
		//
		// }
		//
		// } else {
		// if (record.get(c).getTime().before(sos.get(s).getTime())) {
		// flag++;
		// //判断是否为遍历完
		// if ((r >= record.size() - 1)) {
		// bl = false;
		// } else {
		// //并且还没有取到max，则继续取
		// if (flag < 10) {
		// r++;
		// }
		// }
		// }else {
		// flag++;
		// //判断是否为遍历完
		// if ((s >= sos.size() - 1)) {
		// bl = false;
		// } else {
		// //并且还没有取到max，则继续取
		// if (flag < 10) {
		// s++;
		// }
		// }
		// }
		//
		//
		// }
		// } else {// 某一个列表已经查完,
		// if (r == record.size() - 1) {
		// // c=max-(r-1)-1;
		// c = max - r - 2;
		// flag = max;
		//
		// }
		// if (c == cross.size() - 1) {
		// r = max - c - 2;
		// flag = max;
		// }
		// }
		//
		// }// while
		// } else {
		// c = cross.size() - 1;
		// r = record.size() - 1;
		// }
		//
		//
		//
		// // ///////得到r ,c，即列表的下标，
		// System.out.println(r + "------------------------------" + c);
		//
		// JSONArray crossJsonArray = new JSONArray();
		// JSONArray recordJsonArray = new JSONArray();
		// JSONObject crossJsonObject = null;
		// JSONObject recordJsonObject = null;
		// for (int i = 0; i <=r; i++) {
		// System.out.println(record.get(i).getTime());
		// recordJsonObject = new JSONObject();
		// recordJsonObject.put("url", record.get(i).getRecord_url());
		// recordJsonObject.put("from_id", record.get(i).getFrom_id());
		// recordJsonObject.put("from_type", record.get(i).getFrom_type());
		// recordJsonObject.put("time", record.get(i).getTime().toString());
		// recordJsonArray.add(recordJsonObject);
		// }
		// for (int i = 0; i <=c; i++) {
		// System.out.println(cross.get(i).getTime());
		// crossJsonObject = new JSONObject();
		// // crossJsonObject.put("lng", cross.get(i).getLng());
		// // crossJsonObject.put("lat", cross.get(i).getLat());
		// crossJsonObject.put("point",
		// cross.get(i).getLng()+","+cross.get(i).getLat());
		// crossJsonArray.add(crossJsonObject);
		// }

	}

}

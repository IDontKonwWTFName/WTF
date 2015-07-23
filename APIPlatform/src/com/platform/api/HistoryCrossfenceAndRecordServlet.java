package com.platform.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javassist.CtBehavior;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import antlr.collections.List;

import com.platform.model.Cross_fence;
import com.platform.model.Fence;

/**
 * * @author 作者 E-mail: * @date 创建时间：2015年7月18日 上午9:33:31 * @version 1.0 * @parameter
 * * @since * @return
 */
// 得到某时间带你以前的报警信息和位置信息
// user_id,shouhuan_id,start_time,end_time
// 使用的表：crossfence, historyrecord
// 传文件地址
@WebServlet("/HistoryCrossfenceAndRecord")
public class HistoryCrossfenceAndRecordServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");

		// String user_id= request.getParameter("user_id");
		String shouhuan_id = request.getParameter("shouhuan_id");
		Date s = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String end_time = dateFormat.format(s);
		String start_time = "2000-01-01 00:00:00";
		// String start_time = request.getParameter("start_time");
		// String end_time = request.getParameter("end_time");

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		// ,dbo.[cross_fence]
		Cross_fence cross_fence = new Cross_fence();
		// String hql ="select * from Cross_Fence  where time >=? and time <=?";
		// session.createSQLQuery(hql);
		// 取得穿越围栏信息
		String crossfernceSql = "select time from dbo.[cross_fence] c join dbo.[fence] f on c.fence_id=f.fence_id where c.time >="
				+ "'"
				+ start_time
				+ "'"
				+ " and c.time <="
				+ "'"
				+ end_time
				+ "' and f.shouhuan_id = "
				+ "'"
				+ shouhuan_id
				+ "'"
				+ " order by time desc";

		SQLQuery sqlQuery = session.createSQLQuery(crossfernceSql);
		java.util.List<Date> cross = sqlQuery.list();
		// 少于10个
		for (int i = 0; i < cross.size() && i < 10; i++) {
			System.out.println(cross.get(i).toString());
		}
		String recordSql = "select time from dbo.[historyrecord] where shouhuan_id = '"
				+ shouhuan_id
				+ "' and time >= '"
				+ start_time
				+ "' and time <= '" + end_time + "'" + " order by time desc";
		SQLQuery sql = session.createSQLQuery(recordSql);
		java.util.List<Date> record = sql.list();
		// 少于十个
		for (int i = 0; i < record.size() && i < 10; i++) {
			System.out.println(record.get(i).toString());
		}
		// int c=0;
		// if (cross.size()>5){
		// c=5;
		// }
		// else{
		// c=cross.size();
		// }
		// int r=0;
		// if (record.size()>5){
		// r=5;
		// }else{
		// r=record.size();
		// }
		// 从其中选取10以内个发送,r,c的值都为其下标
		int c = 0, r = 0;
		int flag = 0;
		int max = 10;
		boolean bl = true;
		boolean jug = true;
		if ((cross.size() + record.size()) > max) {// 如果两个总输小于10，则直接选取
			while (flag < 10) {
				if (bl) {//判断某一个列表是否被取完
					if (cross.get(c).before(record.get(r))) {// 判断大小，然后选择
						flag++;
						if ((c >= cross.size()-1)) {
							bl = false;
						} else {
						       if (flag<max){
								c++;
						       }
							
						}
					} else {
						flag++;
						if ((r >= record.size()-1)) {
							bl = false;
						} else {
							if (flag<10){
								r++;
							}
						}

					}
				} else {// 某一个列表已经查完,
					if (r == record.size() - 1) {
						//c=max-(r-1)-1;
						c =max - r -2;
						flag = max;

					} if (c==cross.size()-1) {
						r = max - c - 2;
						flag = max;
					}
				}

			}// while
		} else {
			c = cross.size() - 1;
			r = record.size() - 1;
		}
		// ///////得到r ,c，即列表的下标
		System.out.println(r + "------------------------------" + c);
		for (int i = 0; i <= r; i++) {
			System.out.println(record.get(i).toString());
		}
		for (int i = 0; i <= c; i++) {
			System.out.println(cross.get(i).toString());
		}
		
		/////将下标的time和手环读取出来读取为url或者点json
		SQLQuery locction = session.createSQLQuery(" select * from dbo.[historylocation] where time = "
				+ " ");
		
		//SQLQuery 
		
		
		
		
		

	}

}

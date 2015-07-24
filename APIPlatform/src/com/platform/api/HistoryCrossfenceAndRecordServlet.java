package com.platform.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javassist.CtBehavior;

import javax.json.JsonArray;
import javax.json.JsonObject;
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

import antlr.collections.List;

import com.platform.model.Cross_fence;
import com.platform.model.Fence;
import com.platform.model.Historyrecord;

/**
 * * @author ���� E-mail: * @date ����ʱ�䣺2015��7��18�� ����9:33:31 * @version 1.0 * @parameter
 * * @since * @return
 */
// �õ�ĳʱ�������ǰ�ı�����Ϣ��λ����Ϣ
// user_id,shouhuan_id,start_time,end_time
// ʹ�õı���crossfence, historyrecord
// ���ļ���ַ
//����jsonArray��code,msg,location,record
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
//		Cross_fence cross_fence = new Cross_fence();
		// String hql ="select * from Cross_Fence  where time >=? and time <=?";
		// session.createSQLQuery(hql);
		// ȡ�ô�ԽΧ����Ϣ 
		String crossfernceSql = "select c.* from dbo.[cross_fence] c join dbo.[fence] f on c.fence_id=f.fence_id where c.time >="
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

		SQLQuery sqlQuery = session.createSQLQuery(crossfernceSql).addEntity(Cross_fence.class);
		java.util.List<Cross_fence> cross = sqlQuery.list();
		// ����10��
		for (int i = 0; i < cross.size() && i < 10; i++) {
			System.out.println(cross.get(i).getTime().toString());
		}
		
		//historyrecord
		String recordSql = "select * from dbo.[historyrecord] where shouhuan_id = '"
				+ shouhuan_id
				+ "' and time >= '"
				+ start_time
				+ "' and time <= '" + end_time + "'" + " order by time desc";
		SQLQuery sql = session.createSQLQuery(recordSql).addEntity(Historyrecord.class);
		java.util.List<Historyrecord> record = sql.list();
		// ����ʮ��
		for (int i = 0; i < record.size() && i < 10; i++) {
			System.out.println(record.get(i).getTime().toString());
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
		// ������ѡȡ10���ڸ�����,r,c��ֵ��Ϊ���±�
		int c = 0, r = 0;
		int flag = 0;
		int max = 10;
		boolean bl = true;
		boolean jug = true;
		if ((cross.size() + record.size()) > max) {// �����������С��10����ֱ��ѡȡ
			while (flag < 10) {
				if (bl) {//�ж�ĳһ���б��Ƿ�ȡ��
					if (cross.get(c).getTime().before(record.get(r).getTime())) {// �жϴ�С��Ȼ��ѡ��
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
				} else {// ĳһ���б��Ѿ�����,
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
		// ///////�õ�r ,c�����б����±�
		System.out.println(r + "------------------------------" + c);
		
		JSONArray crossJsonArray = new JSONArray(); 
		JSONArray recordJsonArray =new JSONArray();
		JSONObject crossJsonObject=null;
		JSONObject recordJsonObject=null;
		for (int i = 0; i <= r; i++) {
			System.out.println(record.get(i).getTime());
			recordJsonObject=new JSONObject();
			recordJsonObject.put("url",record.get(i).getRecord_url() );
			recordJsonArray.add(recordJsonObject);
		}
		for (int i = 0; i <= c; i++) {
			System.out.println(cross.get(i).getTime());
			crossJsonObject=new JSONObject();
			crossJsonObject.put("lng", cross.get(i).getLng());
			crossJsonObject.put("lat", cross.get(i).getLat());
			crossJsonArray.add(crossJsonObject);
		}
		 
		Map< String, String> data = new HashMap<String, String>();
 		
		//�涨ĳ�ֹ���+++++++++++++++++++++++++++++++++++++++++++++
		//����jsonArray
		data.put("code", "100");
		data.put("msg", "�ɹ�");
		data.put("location", crossJsonArray.toString());
		data.put("record",recordJsonArray.toString());
		
		response.getWriter().println(data.toString());
		
		

	}

}
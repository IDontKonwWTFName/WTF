package com.a.aPay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import antlr.debug.Event;

import com.pingplusplus.model.Webhooks;
import com.platform.model.Payment;
import com.platform.model.Service;

import net.sf.json.JSONObject;

/**
 * * @author 作者 E-mail: * @date 创建时间：2015年8月7日 下午8:45:27 * @version 1.0 * @parameter
 * * @since * @return
 */
@WebServlet("/chargesucceded")
// 支付成功
// 写入数据库
// 接收event
// 返回状态码
// {
// "id": "evt_ugB6x3K43D16wXCcqbplWAJo",
// "created": 1427555101,
// "livemode": true,
// "type": "charge.succeeded",
// "data": {
// "object": {
// "id": "ch_Xsr7u35O3m1Gw4ed2ODmi4Lw",
// "object": "charge",
// "created": 1427555076,
// "livemode": true,
// "paid": true,
// "refunded": false,
// "app": "app_1Gqj58ynP0mHeX1q",
// "channel": "upacp",
// "order_no": "123456789",
// "client_ip": "127.0.0.1",
// "amount": 100,
// "amount_settle": 0,
// "currency": "cny",
// "subject": "Your Subject",
// "body": "Your Body",
// "extra": {},
// "time_paid": 1427555101,
// "time_expire": 1427641476,
// "time_settle": null,
// "transaction_no": "1224524301201505066067849274",
// "refunds": {
// "object": "list",
// "url": "/v1/charges/ch_L8qn10mLmr1GS8e5OODmHaL4/refunds",
// "has_more": false,
// "data": []
// },
// "amount_refunded": 0,
// "failure_code": null,
// "failure_msg": null,
// "metadata": {},
// "credential": {},
// "description": null
// }
// },
// "object": "event",
// "pending_webhooks": 0,
// "request": "iar_qH4y1KbTy5eLGm1uHSTS00s"
// }
public class ChargeSucceededServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			System.out.println(key + " " + value);
		}
		// 获得 http body 内容
		BufferedReader reader = request.getReader();
		StringBuffer buffer = new StringBuffer();
		String string;
		while ((string = reader.readLine()) != null) {
			buffer.append(string);
		}
		reader.close();
		// 解析异步通知数据
		com.pingplusplus.model.Event event = Webhooks.eventParse(buffer
				.toString());

		if ("charge.succeeded".equals(event.getType())) {
			// 支付成功通知
			SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			//得到metadata里面的内容

			JSONObject jsonObject = JSONObject.fromObject(event.getData().get(
					"object"));
			JSONObject metadata = JSONObject.fromObject(jsonObject
					.get("metadata"));
			//----service
			SQLQuery sqlQuery = session
					.createSQLQuery("select * from dbo.[service] where service_type="+metadata.getString("service_type")).addEntity(Service.class);
			Service service=(Service) sqlQuery.uniqueResult();
			
			
			//----Max
			SQLQuery sqlMaxEnd_time = session.createSQLQuery(
 					"select * from dbo.[payment] where shouhuan_id=:shouhuan_id and end_time=(select max(end_time) from dbo.[payment] where shouhuan_id=:shouhuan_id)")
 					.addEntity(Payment.class);
			sqlMaxEnd_time.setString("shouhuan_id", metadata.getString("shouhuan_id"));
 			Payment payment_old=(Payment) sqlMaxEnd_time.uniqueResult();
			
			
			int service_worktime = service.getService_workime();

			Date now = new Date();
			//if ()
			GregorianCalendar today = new GregorianCalendar();
			today.add(Calendar.MONTH, service_worktime);
			Date end_time = today.getTime();
			
			System.out.println(end_time+"-------------------");

			Payment payment = new Payment();
			payment.setShouhuan_id(metadata.getString("shouhuan_id"));
			payment.setUser_id(metadata.getString("user_id"));
			payment.setService_type(Integer.valueOf(metadata
					.getString("service_type")));

			payment.setBegin_time(now);
			payment.setEnd_time(end_time);
			
			session.save(payment);
			transaction.commit();

			response.setStatus(200);
		}
		// else if ("refund.succeeded".equals(event.getType())) {
		// response.setStatus(200);
		// }
		else {
			response.setStatus(500);
		}

		// BufferedReader bufferedReader =new BufferedReader(new
		// InputStreamReader(request.getInputStream(),"utf-8"));
		// String line=null;
		// StringBuilder sb=new StringBuilder();
		// while ((line=bufferedReader.readLine())!=null) {
		// sb.append(line);
		// }
		// JSONObject jsonObject=JSONObject.fromObject(sb.toString());

	}
}

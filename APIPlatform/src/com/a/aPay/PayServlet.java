package com.a.aPay;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.a.aPay.ChargeExample;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.platform.model.Payment;
import com.platform.model.Service;

/**
 * * @author 作者 E-mail: * @date 创建时间：2015年8月7日 下午2:01:05 * @version 1.0 * @parameter
 * * @since * @return
 */
@WebServlet("/pay")
public class PayServlet extends HttpServlet {
	@Override
	// pay
	// get
	// shouhuan_id,user_id
	// JSONARray 返回: service_type,end_time
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		String shouhuan_id = request.getParameter("shouhuan_id");

		System.out.println("Payment_id: " + shouhuan_id);

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
					"select * from payment where shouhuan_id=:shouhuan_id")
					.addEntity(Payment.class);
			// Payment info = (Payment) query.uniqueResult();
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			List<Payment> payment = sqlQuery.list();
			data.put("code", "100");
			data.put("msg", "获取数据成功");
			data.put("data", JSONArray.fromObject(payment).toString());

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

	@Override
	// 支付
	// pay post
	// user_id,shouhuan_id,service_type,
	// 返回Charge
	//
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//配置
		Pingpp.apiKey = "sk_test_qX9u9G4avL88rf9K0G5yDSKG";
		String appId = "app_fHuXvD4arnT0DCqP";
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		//支付信息
		Map<String, String> data = new HashMap<String, String>();
		String user_id = request.getParameter("user_id");
		String shouhuan_id = request.getParameter("shouhuan_id");
		String service_type = request.getParameter("service_type");

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();

		SQLQuery sqlQuery = session.createSQLQuery(
				"select * from dbo.[service] where sevice_type:=service_type ")
				.addEntity(Service.class);
		sqlQuery.setString("service_type", service_type);
		Service service = (Service) sqlQuery.uniqueResult();

		// pay信息
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("user_id", user_id);
		jsonObject.put("shouhuan_id", shouhuan_id);
		jsonObject.put("service_type", service_type);
		//order_no
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		String dateString = simpleDateFormat.format(date);
		Random random = new Random();
		int r = random.nextInt() % 100;
		String order_no = user_id + dateString + r;

		Charge charge = null;
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		chargeMap.put("amount", service.getService_price() * 100);
		chargeMap.put("currency", "cny");
		chargeMap.put("subject", service.getService_type());
		chargeMap.put("body", service.getService_describe());
		// user_id+时间

		chargeMap.put("order_no", order_no);
		chargeMap.put("channel", "alipay");
		chargeMap.put("client_ip", "127.0.0.1");
		chargeMap.put("metadata", jsonObject.toString());
		Map<String, String> app = new HashMap<String, String>();
		app.put("id", appId);
		chargeMap.put("app", app);
		try {
			// 发起交易请求
			charge = Charge.create(chargeMap);
			System.out.println(charge);
			data.put("code", "100");
			data.put("msg", "zhifu");
			data.put("data", charge.toString());

			response.getWriter().print(JSONObject.fromObject(data).toString());
			System.out.println(charge.toString());
		} catch (PingppException e) {
			e.printStackTrace();
			data.put("code", "500");
			data.put("msg", "失败");
			data.put("data", "");

		}

		// 达到charge

	}

}

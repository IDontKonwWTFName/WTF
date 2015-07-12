package com.platform.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.*;

/**
 * Servlet implementation class PaymentServlet
 */
@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		String id = request.getParameter("payment_id");
		
		System.out.println("Payment_id: "+id);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		HttpSession session1 = request.getSession();
		User userInfo = (User)session1.getAttribute("user"); 
		if(id==null || id.equals("") || userInfo==null)
		{
			data.put("code","200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			return;
		}
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
	
		try{
			SQLQuery query = s.createSQLQuery("select * from payment where payment_id="+id).addEntity(Payment.class);
			Payment info = (Payment) query.uniqueResult();
			
			data.put("code","100");
			data.put("msg", "获取数据成功");
			data.put("data", JSONObject.fromObject(info).toString());
			
			out.println(JSONObject.fromObject(data).toString());
		}catch(Exception e)
		{
			data.put("code","200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		}finally
		{
			s.close();
			sf.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		Integer id = 0;
		Integer type = 0;
		String usid = request.getParameter("user_id");
		String shid = request.getParameter("shouhuan_id");
		String btime = request.getParameter("begin_time");
		String etime = request.getParameter("end_time");
		
		try{
			id = Integer.valueOf(request.getParameter("payment_id"));
			type = Integer.valueOf(request.getParameter("service_type"));
		}catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("User_info: "+id+" "+type+" "+usid+" "+shid+" "+btime+" "+etime);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		HttpSession session1 = request.getSession();
		User userInfo = (User)session1.getAttribute("user");
		if(userInfo==null)
		{
			data.put("code","200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			return;
		}
		try{
			
			Payment pay = new Payment();
			pay.setBegin_time(btime);
			pay.setEnd_time(etime);
			pay.setPayment_id(id);
			pay.setService_type(type);
			pay.setShouhuan_id(shid);
			pay.setUser_id(usid);
			
			s.save(pay);
			t.commit();
			
			data.put("code","100");
			data.put("msg", "添加数据成功");
			data.put("data", "");
			
			out.println(JSONObject.fromObject(data).toString());
		}catch(Exception e)
		{
			t.rollback();
			data.put("code","200");
			data.put("msg", "添加数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		}finally
		{
			s.close();
			sf.close();
		}
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));  
	    String line;  
	    StringBuilder sb = new StringBuilder();
	    while ((line = in.readLine()) != null)
	    {  
//	        System.out.println(line);
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
		HttpSession session1 = request.getSession();
		User userInfo = (User)session1.getAttribute("user");
		if(userInfo==null)
		{
			data.put("code","200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			return;
		}
		try{
	    	id = jo.getString("payment_id");
			which = jo.getString("which");
			value = jo.getString("value");
	    }catch(Exception e)
	    {
	    	data.put("code","200");
			data.put("msg", "修改数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
			return;
	    }
		
		System.out.println("Payment(update): "+which+"->"+value);
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		try{
			String sql = "update payment set "+which+" = "+value+" where payment_id ="+id;
			System.out.println(sql);
			SQLQuery query = s.createSQLQuery(sql);
			query.addEntity(Payment.class);
//			SQLQuery query = s.createSQLQuery("update user set ? = ? where user_id = ?");
//			query.setParameter(0, which);
//			query.setParameter(1, value);
//			query.setParameter(2, id);
			query.executeUpdate();
			t.commit();
			
			data.put("code","100");
			data.put("msg", "删除数据成功");
			data.put("data", "");
			
			out.println(JSONObject.fromObject(data).toString());
			
		}catch(Exception e)
		{
			data.put("code","200");
			data.put("msg", "修改数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		}
		finally
		{
			s.close();
			sf.close();
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		String id = request.getParameter("payment_id");
		System.out.println("Payment_id(delete): "+id);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		HttpSession session1 = request.getSession();
		User userInfo = (User)session1.getAttribute("user"); 
		if(id==null || id.equals("") || userInfo==null)
		{
			data.put("code","200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			out.println(JSONObject.fromObject(data).toString());
			return;
		}
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
	
		try{
			SQLQuery query = s.createSQLQuery("delete from payment where payment_id=?");
			query.addEntity(Payment.class);
			query.setParameter(0, id);
			query.executeUpdate();
			t.commit();
			
			data.put("code","100");
			data.put("msg", "获取数据成功");
			data.put("data", "");
			
			out.println(JSONObject.fromObject(data).toString());
		}catch(Exception e)
		{
			data.put("code","200");
			data.put("msg", "获取数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		}finally
		{
			s.close();
			sf.close();
		}
	}

}

package com.platform.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.platform.model.*;

/**
 * Servlet implementation class UserLogServlet
 */
@WebServlet("/userlog")
public class UserLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLogServlet() {
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
		String logid = request.getParameter("user_log_id");
		
		System.out.println("User_log_id: "+logid);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		
		if(logid==null || logid.equals(""))
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
			SQLQuery query = s.createSQLQuery("select * from user_log where user_log_id="+logid).addEntity(User_log.class);
			User_log info = (User_log) query.uniqueResult();
			
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
	//log_type:  1:注册,2:意见反馈
	//userlog post
	//user_id, log_type,event
	//
	//
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/x-json");
        
		String user_id = request.getParameter("user_id");
		String event = request.getParameter("event");
		
		Integer log_type = 0;
		
		try{
			
			log_type = Integer.valueOf(request.getParameter("log_type"));
		}catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String time = dateFormat.format(now);
		
		System.out.println("User_info: "+user_id+" "+time+" "+event);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		try{
			
			User_log log = new User_log();
			
			log.setEvent(event);
			log.setLog_type(log_type);
			log.setTime(time);
			log.setUser_id(user_id);
			
			session.save(log);
			transaction.commit();
			
			data.put("code","100");
			data.put("msg", "添加数据成功");
			data.put("data", "");
			
			out.println(JSONObject.fromObject(data).toString());
		}catch(Exception e)
		{
			transaction.rollback();
			data.put("code","200");
			data.put("msg", "添加数据失败");
			data.put("data", "");
			e.printStackTrace();
			out.println(JSONObject.fromObject(data).toString());
		}finally
		{
			session.close();
			sessionFactory.close();
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
	    try{
	    	id = jo.getString("user_log_id");
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
		
		System.out.println("User_log(update): "+which+"->"+value);
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		try{
			String sql = "update user_log set "+which+" = "+value+" where user_log_id ="+id;
			System.out.println(sql);
			SQLQuery query = s.createSQLQuery(sql);
			query.addEntity(User_log.class);
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
		String id = request.getParameter("user_log_id");
		System.out.println("User_log(delete): "+id);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		
		if(id==null || id.equals(""))
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
			SQLQuery query = s.createSQLQuery("delete from user_log where user_log_id=?");
			query.addEntity(User_log.class);
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

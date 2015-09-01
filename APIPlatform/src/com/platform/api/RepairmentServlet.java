package com.platform.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

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
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.baidu.yun.core.annotation.R;
import com.platform.model.*;

/**
 * Servlet implementation class RepairmentServlet
 */
@WebServlet("/repairment")
public class RepairmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RepairmentServlet() {
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
		String shouhuan_id = request.getParameter("shouhuan_id");
		
		System.out.println("Shouhuan_id: "+shouhuan_id);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		
		if(shouhuan_id==null || shouhuan_id.equals(""))
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
			SQLQuery query = s.createSQLQuery("select * from repairment where shouhuan_id="+shouhuan_id).addEntity(Repairment.class);
			List<Repairment> rs = query.list();
			JSONObject jsonObject=null;
			JSONArray jsonArray=new JSONArray();
			
			for(Repairment r : rs){
				jsonObject=new JSONObject();
				jsonObject.put("repair_info", r.getRepair_info());
				jsonObject.put("repair_payment", r.getRepair_payment());
				jsonObject.put("repair_time", r.getRepair_time());
				jsonObject.put("repairment_id", r.getRepairment_id());
				
				jsonArray.add(jsonObject);
			}
			
			data.put("code","100");
			data.put("msg", "获取数据成功");
			data.put("data", jsonArray.toString());
			
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
		Integer id = null;
		Integer pay = null;
		String info = request.getParameter("repair_info");
		String rpid = request.getParameter("repair_id");
		String shid = request.getParameter("shouhuan_id");
		Date time = null;
		try{
			time = new Date(request.getParameter("repair_time"));
		}catch(Exception e)
		{
			System.out.println("Repairment: 日期格式错误");
			e.printStackTrace();
			time = new Date();
		}
		
		try{
			id = Integer.valueOf(request.getParameter("repairment_id"));
			pay = Integer.valueOf(request.getParameter("repairment_payment"));
		}catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("User_info: "+id+" "+pay+" "+info+" "+rpid+" "+time);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		try{
			
			Repairment rep = new Repairment();
			rep.setRepair_info(info);
			rep.setRepair_payment(pay);
			rep.setRepairment_id(id);
			rep.setShouhuan_id(shid);
			rep.setRepair_time(time);
			
			s.save(info);
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
	    try{
	    	id = jo.getString("repairment_id");
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
		
		System.out.println("Repairment(update): "+which+"->"+value);
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		try{
			String sql = "update repairment set "+which+" = "+value+" where repairment_id ="+id;
			System.out.println(sql);
			SQLQuery query = s.createSQLQuery(sql);
			query.addEntity(Repairment.class);
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
		String repairment_id = request.getParameter("repairment_id");
		String shouhuan_id = request.getParameter("shouhuan_id");
		if(repairment_id != null || !repairment_id.equals("") ) System.out.println("Repairment_id(delete): "+repairment_id);
		if(shouhuan_id != null || !shouhuan_id.equals("") ) System.out.println("shouhuan_id(delete): "+shouhuan_id);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		
		if(repairment_id==null || repairment_id.equals("") || shouhuan_id==null || shouhuan_id.equals(""))
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
			SQLQuery query;
			if(repairment_id != null || !repairment_id.equals("")){
				query = s.createSQLQuery("delete from repairment where repairment_id=?");
				query.addEntity(Repairment.class);
				query.setParameter(0, repairment_id);
			}else{
				query = s.createSQLQuery("delete from repairment where shouhuan_id=?");
				query.addEntity(Repairment.class);
				query.setParameter(0, shouhuan_id);
			}
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

package com.platform.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.*;

import java.util.*;
import java.io.*;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.platform.model.Relation;
import com.platform.model.Shouhuan;
import com.platform.model.User;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		response.setContentType("text/json");
		JSONObject jo = new JSONObject();
		Map<String,String> user = new HashMap<String, String>();
		user.put("name","APIPlatform");
		user.put("version","0.61");
		user.put("author", "�ֻ�");
		user.put("email", "@������");
		jo = JSONObject.fromObject(user);
		System.out.println(jo.toString());
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/x-json");
		String user_id = request.getParameter("user_id");
		String passwd = request.getParameter("passwd");
		System.out.println("Login: "+user_id+" "+passwd);
		
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();  
        Session session = sessionFactory.openSession();  
        
		PrintWriter out = response.getWriter();
		JSONObject msg;
		Map<String, String> data = new HashMap<String, String>();
		
		try{  
            //׼������   
			SQLQuery query = session.createSQLQuery("select * from dbo.[user] where user_id=:user_id").addEntity(User.class);
			query.setString("user_id", user_id);
			User user = (User)query.uniqueResult();
            if(user != null && user.getPassword().equals(passwd))
            {
            //��¼�ɹ��󷵻����й����ֻ�ID
            	SQLQuery sqlQuery =session.createSQLQuery("select s.* from dbo.[relation] r join dbo.[shouhuan] s on r.shouhuan_id=s.shouhuan_id where  r.user_id=:user_id and r.power>0").addEntity(Shouhuan.class);
            	sqlQuery.setString("user_id", user_id);
            	List<Shouhuan> shouhuans=sqlQuery.list();
            	JSONObject  jsonObject =null;
            	JSONArray jsonArray =new JSONArray();
//            	JSONArray jsonArray=new JSONArray().fromObject(shouhuans);
            	for (Shouhuan shouhuan : shouhuans) {
            		jsonObject=new JSONObject();
					jsonObject.put("shouhuan_id", shouhuan.getShouhuan_id());
					jsonObject.put("name",shouhuan.getName());
					jsonArray.add(jsonObject);
				}
            	data.put("code","100");
    			data.put("msg","��֤�ɹ�");
    			data.put("data",jsonArray.toString());
    			
            } else {
            	data.put("code","200");
    			data.put("msg","�û������������");
    			data.put("data","");
            }
        }catch(Exception err){  
	        err.printStackTrace();  
	        data.put("code","500");
			data.put("msg","�ڲ�����������");
			data.put("data","");
	    }finally{  
	        session.close();  
	        sessionFactory.close();
	    } 
		msg = JSONObject.fromObject(data);
		out.println(msg.toString());
		System.out.println(msg.toString());
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
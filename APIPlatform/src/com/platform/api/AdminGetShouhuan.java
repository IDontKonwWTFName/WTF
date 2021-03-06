package com.platform.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.cfg.Configuration;

import com.platform.model.Shouhuan;
@WebServlet("/admingetshouhuan")
/** * @author  作者 E-mail: * @date 创建时间：2015年7月25日 上午8:53:56 * @version 1.0 * @parameter  * @since  * @return  */
public class AdminGetShouhuan extends HttpServlet {

	@Override
	
	//每个人的id，性别和注册时间
	//admingetshouhuan  get
	//
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	response.setContentType("text/x-json");
	
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	Session session =sessionFactory.openSession();
	Map<String, String> data =new HashMap<String, String>();
	
	try {
		SQLQuery sqlQuery =session.createSQLQuery("select * from dbo.[shouhuan] ").addEntity(Shouhuan.class);
		List< Shouhuan> shouhuans =sqlQuery.list();
		JSONObject jsonObject=null;
		JSONArray jsonArray=new JSONArray();
		
		for (Shouhuan s : shouhuans) {
			jsonObject=new JSONObject();
			jsonObject.put("shouhuan_id", s.getShouhuan_id());
//			jsonObject.put("name", s.getName());
//			jsonObject.put("nickname",s.getNickname());
//			jsonObject.put("birthday",s.getBirthday() );
			jsonObject.put("registrationdate",s.getRegistrationdate());
			jsonObject.put("sex", s.getSex());
//			jsonObject.put("curentversion", s.getCurrentversion());
//			jsonObject.put("tempororyright", s.getTemporaryright());
			
			jsonArray.add(jsonObject);
		}
		
		
		data.put("code", "100");
		data.put("msg", "get 数据成功");
		data.put("data", jsonArray.toString());
		response.getWriter().println(JSONObject.fromObject(data).toString());
		System.out.println(data.toString()); 
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
	
		
	}
}


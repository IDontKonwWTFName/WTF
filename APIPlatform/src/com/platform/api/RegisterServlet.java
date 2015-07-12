package com.platform.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.border.EmptyBorder;

import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import antlr.collections.List;

import com.platform.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	

	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {
			//��ȡע��id��passwd
		   request.setCharacterEncoding("utf-8");
		   response.setCharacterEncoding("utf-8");
		    String id=request.getParameter("user_id");
			String passwd=request.getParameter("passwd");
			System.out.println(id+"is coming!");
			//�����Ƿ��Ѿ�ע��
			SessionFactory sessionFactory= new Configuration().configure().buildSessionFactory();
			Session session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			Map<String,String>data =new HashMap<String,String>(); 
			try {
				
				SQLQuery sqlQuery= session.createSQLQuery("select * from dbo.[user] where user_id = "+id).addEntity(User.class);
			
				//������ص�userΪ�գ������ע��
				if (sqlQuery.uniqueResult() == null){
					User user=new User();
					user.setUser_id(id);
					user.setPassword(passwd);
					
					session.save(user);
					transaction.commit();
					
					data.put("code", "100");
					data.put("msg", "ע��ɹ��ɹ�/sucesess!");
					data.put("data","");
					response.getWriter().println(JSONObject.fromObject(data).toString());
				}else{
				//���򷵻�
				data.put("code", "200");
				data.put("msg", "�˺���ע�ᣡalready!");
				data.put("data","");
				response.getWriter().println(JSONObject.fromObject(data).toString());
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				 e.printStackTrace(); 
				data.put("code", "500");
				data.put("msg", "��ȡ����ʧ��/failed");
				data.put("data","");
				//response.getWriter().println(JSONObject.fromObject(data).toString().getBytes("utf-8"));
				response.getWriter().write(JSONObject.fromObject(data).toString());
			}finally{
				session.close();
				sessionFactory.close();
			}
			
	}
}

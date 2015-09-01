package com.platform.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.platform.model.*;
/**
 * Servlet implementation class getUserInfoServlet
 */
@WebServlet("/userinfo")
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoServlet() {
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
		String userid = request.getParameter("user_id");
		
		System.out.println("User_id: "+userid);
		response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
		
		if(userid==null || userid.equals(""))
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
			SQLQuery query = s.createSQLQuery("select * from user_info where user_id="+userid).addEntity(User_info.class);
			User_info info = (User_info) query.uniqueResult();
			//System.out.println(info.getBirthday());
			
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
	//userinfo  post
	//文件+jsonObject{user_id:xx}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// 设置编码
		response.setContentType("text/json");

		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 判断是是否是Multipart
		if (isMultipart) {
			System.out.println("is multipart");
			// 获取路径

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
			factory.setSizeThreshold(1024 * 1024);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");

			Map<String, String> data = new HashMap<String, String>();
			Map<String, String> getdata = new HashMap<String, String>();
			JSONObject jsonObject = null;
			
			
			SessionFactory sf = new Configuration().configure()
					.buildSessionFactory();
			Session session = sf.openSession();
			Transaction t = session.beginTransaction();
			try {
				List<FileItem> items = upload.parseRequest(request);

				

				for (FileItem item : items) {
					if (item.isFormField()) { // username="username"
						// 设置字符串,取得字符串JSON
						String name = item.getFieldName();
						String value = item.getString("utf-8");
						jsonObject = JSONObject.fromObject(value);
						
						// shouhuan.set
						System.out.println(name + " = " + value);
					} else {
						// 文件
						// 将文件写入磁盘
						//String realPath = this.getServletContext().getRealPath("WEB-INF/data/HeadIcon");
						String realPath = "C:/Users/军/Desktop/data/HeadIcon/";
						System.out.println("url:" + realPath);
						// 创建文件
						File dir = new File(realPath);

						if (!dir.exists()) {
							dir.mkdir();
						}
						// 得到文件对象,写图片文件时，
						//
						String name = item.getName();
						//String path = jsonObject.getString("id")+ name.substring(name.lastIndexOf("."));
						String path = jsonObject.getString("user_id")+ name.substring(name.lastIndexOf("."));
						item.write(new File(dir, path));
						// User_info user_info=new User_info();
						// 将path写入数据库
						jsonObject.put("path", path);
					}
				}
				
				SQLQuery sqlQuery=session.createSQLQuery("update dbo.[userinfo] set headiconurl=:path where user_id=:user_id");
				sqlQuery.setString("path", jsonObject.getString("path"));
				sqlQuery.setString("user_id", jsonObject.getString("user_id"));
				sqlQuery.executeUpdate();
				t.commit();
				
				data.put("code", "100");
				data.put("msg", "跟新数据成功");
				data.put("data", "");
				response.getWriter().println(JSONObject.fromObject(data).toString());

			} catch (Exception e) {
				e.printStackTrace();
				data.put("code", "200");
				data.put("msg", "跟新数据失败");
				data.put("data", "");
			}
			finally{
				session.close();
				sf.close();
				System.out.println(data.toString());
			}
		}
		
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	//userinfo put
	//user_id,which,value
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));  
	    String line;  
	    StringBuilder sb = new StringBuilder();
	    while ((line = in.readLine()) != null)
	    {  
//	        System.out.println(line);
	        sb.append(line);
	    }
	 
	    //System.out.println("user_info-----update"+sb.toString());
	    System.out.println("user_info-----update"+sb);
	    JSONObject jo = JSONObject.fromObject(sb.toString());
	    String user_id = null;
	    String which = null;
	    String value = null;
	    response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
	    try{
	    	user_id = jo.getString("user_id");
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
		
		System.out.println("dbo.[User_info](update): "+which+"->"+value);
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		try{
			
			SQLQuery sqlQuery = s.createSQLQuery("update dbo.[User_info] set "+which+" =:value where user_id =:user_id");
			sqlQuery.setString("value", value);
			sqlQuery.setString("user_id", user_id);
			sqlQuery.addEntity(User_info.class);
//			SQLQuery query = s.createSQLQuery("update user set ? = ? where user_id = ?");
//			query.setParameter(0, which);
//			query.setParameter(1, value);
//			query.setParameter(2, id);
			sqlQuery.executeUpdate();
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
		String id = request.getParameter("user_id");
		System.out.println("Userinfo(delete): "+id);
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
			SQLQuery query = s.createSQLQuery("delete from user_info where user_id=?");
			query.addEntity(User_info.class);
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

package com.platform.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Transaction;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.platform.model.*;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class ShouhuanServlet
 */
@WebServlet("/shouhuan")
public class ShouhuanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShouhuanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //shouhuan get
    //shouhuan_id,user_id
    //返回:birthday,mode,name,sex,rate,time,whitelist,moniter,sos,centernummber,flower,sossms,lowbat,remove,pedo,smsonoff,clock
    //
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/x-json");
        
		String shouhuan_id = request.getParameter("shouhuan_id");
		String user_id=request.getParameter("user_id");
		System.out.println("Shouhuan_id: "+shouhuan_id);
		
		
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
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
	
		try{
			SQLQuery sqlQuery = session.createSQLQuery("select * from dbo.[shouhuan] where shouhuan_id=:shouhuan_id").addEntity(Shouhuan.class);
			sqlQuery.setString("shouhuan_id", shouhuan_id);
			Shouhuan shouhuan = (Shouhuan) sqlQuery.uniqueResult();
//			JSONObject jsonObject =new JSONObject().fromObject(shouhuan);
			JSONObject jsonObject =new JSONObject();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			jsonObject.put("shouhuan_id",shouhuan_id);
			jsonObject.put("birthday",formatter.format(shouhuan.getBirthday()));
			jsonObject.put("mode", shouhuan.getMode()==null ?"":shouhuan.getMode());
			jsonObject.put("nickname", shouhuan.getNickname() == null ? "" : shouhuan.getNickname());
			jsonObject.put("registrationdate", shouhuan.getRegistrationdate() ==null ? "":shouhuan.getRegistrationdate());
			jsonObject.put("name", shouhuan.getName() == null ? "" : shouhuan.getName());
			jsonObject.put("sex",shouhuan.getSex() == null ? "" : shouhuan.getSex());
			jsonObject.put("rate", shouhuan.getRate() == null ? "" : shouhuan.getRate());
			jsonObject.put("time", shouhuan.getTime() == null ? "" : shouhuan.getTime() );
			jsonObject.put("whitelist", shouhuan.getWhitelist() == null ? "" : shouhuan.getWhitelist());
			jsonObject.put("moniter", shouhuan.getMoniter() == null ? "" : shouhuan.getMoniter());
			jsonObject.put("sos", shouhuan.getSos() == null ? "" : shouhuan.getSos());
			jsonObject.put("centernumber", shouhuan.getCenternumber() == null ? "" : shouhuan.getCenternumber());
			jsonObject.put("flower", shouhuan.getFlower() == null ? "" : shouhuan.getFlower());
			jsonObject.put("sossms", shouhuan.getSossms() == null ? "" : shouhuan.getSossms());
			jsonObject.put("lowbat", shouhuan.getLowbat() == null ? "" : shouhuan.getLowbat());
			jsonObject.put("remove", shouhuan.getRemove() == null ? "" : shouhuan.getRemove());
			jsonObject.put("pedo", shouhuan.getPedo() == null ? "" : shouhuan.getPedo());
			jsonObject.put("smsonoff", shouhuan.getSmsonoff() == null ? "" : shouhuan.getSmsonoff());
			jsonObject.put("clock",shouhuan.getClock() == null ? "" : shouhuan.getClock());
						 
			data.put("code","100");
			data.put("msg", "获取数据成功");
			data.put("data", jsonObject.toString());
			
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
			session.close();
			sessionFactory.close();
		}
	}
	
	
	@Override
	//shouhuan post
	// 头像：文件+jsonObject{shouhuan_id:XX，user_id=XX}
	//数据：jsonObject{user_id：XX,shouhuan_id:XX,修改项：XX,修改项：XX}
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
							//从jsonObject中获取shouhuan_id
							String name = item.getFieldName();
							String value = item.getString("utf-8");
							jsonObject = JSONObject.fromObject(value);
							
							// shouhuan.set
							System.out.println(name + " = " + value);
						} else {
							// 文件
							// 将文件写入磁盘
							//String realPath = this.getServletContext().getRealPath("WEB-INF/data/HeadIcon");
							String realPath = "D:/data/HeadIcon/";
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
							String path = jsonObject.getString("shouhuan_id")+ name.substring(name.lastIndexOf("."));
							item.write(new File(dir, path));
							// User_info user_info=new User_info();
							// 将path写入数据库
							jsonObject.put("path", path);
						}
					}
					
				
					SQLQuery sqlQuery=session.createSQLQuery("update dbo.[shouhuan] set headiconurl=:path where shouhuan_id=:shouhuan_id");
					sqlQuery.setString("path", jsonObject.getString("path"));
					sqlQuery.setString("shouhuan_id", jsonObject.getString("shouhuan_id"));
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
			else{
				
				//不是文件的时候，可以改多项
				Map<String, String>data =new HashMap<String,String>();
				SessionFactory sf = new Configuration().configure()
						.buildSessionFactory();
				Session session = sf.openSession();
				Transaction t = session.beginTransaction();
				
				try{
				BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));  
			    String line;  
			    StringBuilder sb = new StringBuilder();
			    while ((line = in.readLine()) != null)
			    {  
//			        System.out.println(line);
			        sb.append(line);
			    }
			    //汉字奇数
			  String sb1=new String(sb.toString().getBytes(),"utf-8");
			    //System.out.println("user_info-----update"+sb.toString());
			System.out.println("shouhuan-----update"+sb1);
			
			    JSONObject jsonObject = JSONObject.fromObject(sb1);
			    
			    String shouhuan_id=null;
			    String sql = "update dbo.[Shouhuan] set ";
				//+which+" = '"+value+"' where user_id ="+id
				//遍历JSONObject，得到key，value,并设置sql语句，注意第二对修改面前要加“，”逗号,sql语句最好加分号
				Iterator iterator=jsonObject.keys();
				String key = (String) iterator.next();
				String value = jsonObject.getString(key);
				//第一句，没有逗号
				sql = sql + key +" = "+"'"+value+"'" ;
				while (iterator.hasNext()){
					key = (String) iterator.next();
					//剔除user_id
					if(key.equals("shouhuan_id")){
					shouhuan_id=jsonObject.getString(key);
					}else{
						value = jsonObject.getString(key);
						sql = sql + " , "+key +" = "+"'"+value+"'" ;
					}
					
					
				}
				//sql=sql+" where user_id = "+"'"+id_by_session+"'";
				sql=sql+" where shouhuan_id = "+"'"+shouhuan_id+"'";
				
				SQLQuery sqlQuery=session.createSQLQuery(sql).addEntity(User_info.class);
				sqlQuery.executeUpdate();
				t.commit();
					
					data.put("code","100");
					data.put("msg", "更新数据成功");
					data.put("data", "");
					
					response.getWriter().println(JSONObject.fromObject(data).toString());
					
				}catch(Exception e)
				{
					data.put("code","200");
					data.put("msg", "修改数据失败");
					data.put("data", "");
					e.printStackTrace();
					response.getWriter().println(JSONObject.fromObject(data).toString());
				}
				finally
				{
					session.close();
					sf.close();
				}
				
			}
		}
	//shouhuan
	//put
	//shouhuan_id,value,which,user_id
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
	    
	    //
	    
		   // System.out.println(sb.toString());
		    
	    JSONObject jo = JSONObject.fromObject(sb.toString());
	    String shouhuan_id = null;
	    String which = null;
	    String value = null;
	    response.setContentType("text/x-json");
		
		PrintWriter out = response.getWriter();
		Map<String, String> data = new HashMap<String, String>();
	    try{
	    	shouhuan_id = jo.getString("shouhuan_id");
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
		
		System.out.println("Shouhuan(update): "+which+"->"+value);
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		
		try{
			SQLQuery sqlQuery = null;
			/*
			 * linsos为林力帆修改，其他情况均按false处理；
			 */
			if(which.equals("linsos")){
				String[] csStr = value.split(" ");
				sqlQuery = s.createSQLQuery("update shouhuan set sos=:sos,centernumber=:centernumber where shouhuan_id =:shouhuan_id");
				sqlQuery.setString("sos", csStr[0]);
				sqlQuery.setString("centernumber", csStr[1]);
				sqlQuery.setString("shouhuan_id", shouhuan_id);
				sqlQuery.addEntity(Shouhuan.class);
			}else{
				s.createSQLQuery("update shouhuan set "+which+" =:value where shouhuan_id =:shouhuan_id");
				sqlQuery.setString("value", value);
				sqlQuery.setString("shouhuan_id", shouhuan_id);
				sqlQuery.addEntity(Shouhuan.class);
			}
			
			
//			SQLQuery query = s.createSQLQuery("update user set ? = ? where user_id = ?");
//			query.setParameter(0, which);
//			query.setParameter(1, value);
//			query.setParameter(2, id);
			sqlQuery.executeUpdate();
			t.commit();
			
			data.put("code","100");
			data.put("msg", "修改数据成功");
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

	

	
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		request.setCharacterEncoding("utf-8");   
//        response.setCharacterEncoding("utf-8");
//		String id = request.getParameter("shouhuan_id");
//		
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String birthString = request.getParameter("birthday");
//		Date birthDate = null;
//		try {
//			birthDate = sdf.parse(birthString);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		String url = request.getParameter("headiconurl");
//		Integer sex = 0;
//		Integer tmprgt = 0;
//		Integer ver = 0;
//		String nick = request.getParameter("nickname");
//		String name = request.getParameter("name");
//		String reg = request.getParameter("registrationdate");
//		
//		try{
//			sex = Integer.valueOf(request.getParameter("sex"));
//			tmprgt = Integer.valueOf(request.getParameter("temporaryright"));
//			ver = Integer.valueOf(request.getParameter("currentversion"));
//		}catch(NumberFormatException e)
//		{
//			e.printStackTrace();
//		}
//		
//		System.out.println("Shouhuan: "+id+" "+birthString+" "+url+" "+sex+" "+tmprgt+" "+ver+" "+nick+" "+name+" "+reg);
//		response.setContentType("text/x-json");
//		
//		PrintWriter out = response.getWriter();
//		Map<String, String> data = new HashMap<String, String>();
//		SessionFactory sf = new Configuration().configure().buildSessionFactory();
//		Session s = sf.openSession();
//		Transaction t = s.beginTransaction();
//		
//		try{
//			
//			Shouhuan shouhuan = new Shouhuan();
//			shouhuan.setShouhuan_id(id);
//			shouhuan.setBirthday(birthDate);
//			shouhuan.setHeadiconurl(url);
//			shouhuan.setSex(sex);
//			shouhuan.setName(name);
//			shouhuan.setNickname(nick);
//			shouhuan.setTemporaryright(tmprgt);
//			shouhuan.setRegistrationdate(reg);
//			shouhuan.setCurrentversion(ver);
//			
//			s.save(shouhuan);
//			t.commit();
//			
//			data.put("code","100");
//			data.put("msg", "添加数据成功");
//			data.put("data", "");
//			
//			out.println(JSONObject.fromObject(data).toString());
//		}catch(Exception e)
//		{
//			t.rollback();
//			data.put("code","200");
//			data.put("msg", "添加数据失败");
//			data.put("data", "");
//			e.printStackTrace();
//			out.println(JSONObject.fromObject(data).toString());
//		}finally
//		{
//			s.close();
//			sf.close();
//		}
//}

	
//
//	/**
//	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
//	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");
		String id = request.getParameter("shouhuan_id");
		System.out.println("Shouhuan(delete): "+id);
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
			SQLQuery query = s.createSQLQuery("delete from shouhuan where shouhuan_id=?");
			query.addEntity(Shouhuan.class);
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
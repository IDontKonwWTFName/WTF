package com.platform.api;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.FilerException;
import javax.mail.Part;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.RepaintManager;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import antlr.debug.TraceAdapter;

import com.platform.model.Shouhuan;
import com.platform.model.User_info;
//���ݸ�ʽ��json��key��value�����ļ���url���÷�
//user
@WebServlet("/headicon")
public class HeadIconServlet extends HttpServlet {
	// private String getFilename
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id_by_session = "15045693947";
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// ���ñ���
		response.setContentType("text/json");

		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// �ж����Ƿ���Multipart
		if (isMultipart) {
			System.out.println("is multipart");
			// ��ȡ·��

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// ���� ����Ĵ�С�����ϴ��ļ������������û���ʱ��ֱ�ӷŵ� ��ʱ�洢��
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
						// �����ַ���,ȡ���ַ���JSON
						String name = item.getFieldName();
						String value = item.getString("utf-8");
						jsonObject = JSONObject.fromObject(value);
						
						// shouhuan.set
						System.out.println(name + " = " + value);
					} else {
						// �ļ�
						// ���ļ�д�����
						//String realPath = this.getServletContext().getRealPath("WEB-INF/data/HeadIcon");
						String realPath = "D:/data/HeadIcon/";
						System.out.println("url:" + realPath);
						// �����ļ�
						File dir = new File(realPath);

						if (!dir.exists()) {
							dir.mkdir();
						}
						// �õ��ļ�����,дͼƬ�ļ�ʱ��
						//
						String name = item.getName();
						//String path = jsonObject.getString("id")+ name.substring(name.lastIndexOf("."));
						String path = id_by_session+ name.substring(name.lastIndexOf("."));
						item.write(new File(dir, path));
						// User_info user_info=new User_info();
						// ��pathд�����ݿ�
						jsonObject.put("headiconurl", path);
					}
				}
				
				String sql = "update dbo.[User_info] set ";
				//+which+" = '"+value+"' where user_id ="+id
				//����JSONObject���õ�key��value,������sql��䣬ע��ڶ����޸���ǰҪ�ӡ���������,sql�����üӷֺ�
				Iterator iterator=jsonObject.keys();
				String key = (String) iterator.next();
				String value = jsonObject.getString(key);
				sql = sql + key +" = "+"'"+value+"'" ;
				while (iterator.hasNext()){
					key = (String) iterator.next();
					if(key.equals("user_id")){
					id_by_session=jsonObject.getString(key);
					}else{
						value = jsonObject.getString(key);
						sql = sql + " , "+key +" = "+"'"+value+"'" ;
					}
					
				}
				sql=sql+" where user_id = "+"'"+id_by_session+"'";
				
				SQLQuery sqlQuery=session.createSQLQuery(sql).addEntity(User_info.class);
				sqlQuery.executeUpdate();
				t.commit();
				
				data.put("code", "100");
				data.put("msg", "�������ݳɹ�");
				data.put("data", "");
				response.getWriter().println(JSONObject.fromObject(data).toString());

			} catch (Exception e) {
				e.printStackTrace();
				data.put("code", "200");
				data.put("msg", "��������ʧ��");
				data.put("data", "");
			}
			finally{
				session.close();
				sf.close();
				System.out.println(data.toString());
			}
		}
		else{
			
			//�����ļ���ʱ�򣬿��ԸĶ���
			Map<String, String>data =new HashMap<String,String>();
			SessionFactory sf = new Configuration().configure()
					.buildSessionFactory();
			Session session = sf.openSession();
			Transaction t = session.beginTransaction();
			try{
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		    String line;  
		    StringBuilder sb = new StringBuilder();
		    while ((line = in.readLine()) != null)
		    {  
//		        System.out.println(line);
		        sb.append(line);
		    }
		    //���ֻ���
		  String sb1=new String(sb.toString().getBytes(),"utf-8");
		    //System.out.println("user_info-----update"+sb.toString());
		System.out.println("user_info-----update"+sb1);
		    JSONObject jsonObject = JSONObject.fromObject(sb1);
		    
		    String sql = "update dbo.[User_info] set ";
			//+which+" = '"+value+"' where user_id ="+id
			//����JSONObject���õ�key��value,������sql��䣬ע��ڶ����޸���ǰҪ�ӡ���������,sql�����üӷֺ�
			Iterator iterator=jsonObject.keys();
			String key = (String) iterator.next();
			String value = jsonObject.getString(key);
			sql = sql + key +" = "+"'"+value+"'" ;
			while (iterator.hasNext()){
				key = (String) iterator.next();
				//�޳�user_id
				if(key.equals("user_id")){
				id_by_session=jsonObject.getString(key);
				}else{
					value = jsonObject.getString(key);
					sql = sql + " , "+key +" = "+"'"+value+"'" ;
				}
				
				
			}
			//sql=sql+" where user_id = "+"'"+id_by_session+"'";
			sql=sql+" where user_id = "+"'"+id_by_session+"'";
			
			SQLQuery sqlQuery=session.createSQLQuery(sql).addEntity(User_info.class);
			sqlQuery.executeUpdate();
			t.commit();
				
				data.put("code","100");
				data.put("msg", "�������ݳɹ�");
				data.put("data", "");
				
				response.getWriter().println(JSONObject.fromObject(data).toString());
				
			}catch(Exception e)
			{
				data.put("code","200");
				data.put("msg", "�޸�����ʧ��");
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

}
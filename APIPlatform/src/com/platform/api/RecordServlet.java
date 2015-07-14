package com.platform.api;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.FilerException;
import javax.mail.Part;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
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

import com.platform.model.Historyrecord;
import com.platform.model.Shouhuan;
import com.platform.model.User_info;
////�����




////
@WebServlet("/record")
public class RecordServlet extends HttpServlet {
	// private String getFilename
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id_by_session="18646098148";
		System.out.println("upload");
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");;// ���ñ���
        
		
        String value="";
        Date now =new Date();
      
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
      
        String time = dateFormat.format(now);
  
        
        System.out.println(time+"-----------------------------------------------");
    
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        //�ж����Ƿ���Multipart
		if (isMultipart) {
			System.out.println("is multipart");
			//��ȡ·��
			//String realPath =this.getServletContext().getRealPath("historyrecord/");
			String realPath =this.getServletContext().getRealPath("HistoryRecord");
			System.out.println("url:" + realPath);
            //�����ļ�
			File dir = new File(realPath);
            
			if (!dir.exists()) {
				dir.mkdir();
			}
   
			DiskFileItemFactory factory = new DiskFileItemFactory();
			 //���� ����Ĵ�С�����ϴ��ļ������������û���ʱ��ֱ�ӷŵ� ��ʱ�洢��
			factory.setSizeThreshold(1024 * 1024);
			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setHeaderEncoding("utf-8");
			
			Map<String, String> postdata =new HashMap<String,String>();
			
			String path="";
			SessionFactory sf =new Configuration().configure().buildSessionFactory();
			Session session=sf.openSession();
			Transaction t = session.beginTransaction();

			try {
				List<FileItem> items = upload.parseRequest(request);
				
				for (FileItem item : items) {
					if (item.isFormField()) { // username="username"
						//�����ַ���
						String name = item.getFieldName();
						value = item.getString("utf-8");
						System.out.println(value);
						//shouhuan.set

						//System.out.println(name + " = " + value);
					} else { // �ļ�
						//���ļ�д�����,�����¼�ĵ�ַ���� 
						String filename = item.getName();
						path=id_by_session+ time+filename.substring(filename.lastIndexOf("."));
						item.write(new File(dir, path));
						//User_info user_info=new User_info();
						//��pathд�����ݿ�
						
						
						Map<String, String> data = new HashMap<String, String>();
						try{
							data.put("code","100");
							data.put("msg", "�������ݳɹ�");
							data.put("data", "");
							response.getWriter().println(JSONObject.fromObject(data).toString());
						}catch(Exception e){
							
						}finally{
							
						}

					}

				}
			JSONObject js=JSONObject.fromObject(value);
			Boolean type = null;
			if (js.getString("from_type").equals("1")){
				type=true;
			}else {
				type=false;
			}
			Historyrecord hr=new Historyrecord();
			hr.setShouhuan_id(js.getString("shouhuan_id"));
			hr.setFrom_id(js.getString("from_id"));
			hr.setTime(now);
			hr.setRecord_url(path);
			hr.setFrom_type(type);
			session.save(hr);
			t.commit();

			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				session.close();
				sf.close();
			}
			
		}
	}

	private String dateFormat(Date now) {
		// TODO Auto-generated method stub
		return null;
	}

}

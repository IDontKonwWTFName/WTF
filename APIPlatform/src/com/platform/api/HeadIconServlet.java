package com.platform.api;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
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

@WebServlet("/headicon")
public class HeadIconServlet extends HttpServlet {
	// private String getFilename
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id_by_session="18646098148";
		System.out.println("upload");
		request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");;// 设置编码
        
        
        String id = request.getParameter("shouhuan_id");
		String birth = request.getParameter("birthday");
		//String url = request.getParameter("headiconurl");
		String url="";
		Integer sex = 0;
		Integer tmprgt = 0;
		Integer ver = 0;
		String nick = request.getParameter("nickname");
		String name = request.getParameter("name");
		String reg = request.getParameter("registrationdate");
		
		try{
			sex = Integer.valueOf(request.getParameter("sex"));
			tmprgt = Integer.valueOf(request.getParameter("temporaryright"));
			ver = Integer.valueOf(request.getParameter("currentversion"));
		}catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        //判断是是否是Multipart
		if (isMultipart) {
			System.out.println("is multipart");
			//获取路径
			String realPath = this.getServletContext().getRealPath("upload");

			System.out.println("url:" + realPath);
            //创建文件
			File dir = new File(realPath);
            
			if (!dir.exists()) {
				dir.mkdir();
			}
   
			DiskFileItemFactory factory = new DiskFileItemFactory();
			 //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
			factory.setSizeThreshold(1024 * 1024);
			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setHeaderEncoding("utf-8");
			
			Map<String, String> dataHashMap =new HashMap<String,String>();

			try {
				List<FileItem> items = upload.parseRequest(request);
				
				SessionFactory sf =new Configuration().configure().buildSessionFactory();
				Session session=sf.openSession();
				Transaction t = session.beginTransaction();
				
			

				for (FileItem item : items) {
					if (item.isFormField()) { // username="username"
						//设置字符串
//						String name = item.getFieldName();
//						String value = item.getString("utf-8");
//						dataHashMap.put(name, value);
						//shouhuan.set

						//System.out.println(name + " = " + value);
					} else { // 文件
						//将文件写入磁盘
						String filename = item.getName();
						String path=id_by_session+ name.substring(name.lastIndexOf("."));
						item.write(new File(dir, path));
						//User_info user_info=new User_info();
						//将path写入数据库
						
						
						Map<String, String> data = new HashMap<String, String>();
						try{

							url=path;
							//String sql = "update dbo.[User_info] set "+"headiconurl"+" = '"+path+"' where shouhuan_id ="+id;
							//System.out.println(sql);
							//SQLQuery query = session.createSQLQuery(sql);
							//query.addEntity(User_info.class);
//							SQLQuery query = s.createSQLQuery("update user set ? = ? where user_id = ?");
//							query.setParameter(0, which);
//							query.setParameter(1, value);
//							query.setParameter(2, id);
							//query.executeUpdate();
							t.commit();
							
							data.put("code","100");
							data.put("msg", "跟新数据成功");
							data.put("data", "");
							response.getWriter().println(JSONObject.fromObject(data).toString());
						}catch(Exception e){
							
						}finally{
							session.close();
							sf.close();
						}

					}

				}
				Shouhuan shouhuan = new Shouhuan();
				shouhuan.setShouhuan_id(id);
				shouhuan.setBirthday(birth);
				shouhuan.setHeadiconurl(url);
				shouhuan.setSex(sex);
				shouhuan.setName(name);
				shouhuan.setNickname(nick);
				shouhuan.setTemporaryright(tmprgt);
				shouhuan.setRegistrationdate(reg);
				shouhuan.setCurrentversion(ver);
				
				session.save(shouhuan);
				t.commit();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

// package com.platform.api;
//
// import java.io.File;
// import java.io.IOException;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.Collection;
//
// import javax.annotation.processing.FilerException;
// import javax.mail.Part;
// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.apache.commons.fileupload.FileItem;
// import org.apache.commons.fileupload.FileUploadException;
// import org.apache.commons.fileupload.disk.DiskFileItemFactory;
// import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
// import antlr.collections.List;
// @WebServlet("/upload")
// public class UploadServlet extends HttpServlet {
// // private String getFilename
// public void doPost(HttpServletRequest request,
// HttpServletResponse response)throws ServletException,IOException{
// System.out.println("1");
// request.setCharacterEncoding("utf-8");//设置编码
//
// // String id = request.getParameter("user_id");
// // String birthString = request.getParameter("birthday");
// // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
// //
// // java.util.Date birth = null;
// // try {
// // birth = sdf.parse(birthString);
// // } catch (ParseException e1) {
// // // TODO Auto-generated catch block
// // e1.printStackTrace();
// // }
// // //String url = request.getParameter("headiconurl");
// // String urlString="";//后面根据磁盘来写
// // Integer sex = 0;
// // String name = request.getParameter("username");
// // String num = request.getParameter("phonenumber");
// // String email = request.getParameter("email");
//
// // DiskFileItemFactory factory =new DiskFileItemFactory();
// // String path = request.getServletContext().getRealPath("/upload");
//
//
// // factory.setRepository(new File(path));
//
// // factory.setSizeThreshold(1024*1024);
//
// // ServletFileUpload upload= new ServletFileUpload(factory);
//
// try{
// //可以上传多个文件
// //Collection<Part> parts
// =(Collection<Part>)upload.parseParameterMap(request);
// Collection<javax.servlet.http.Part> parts =request.getParts();
// for(javax.servlet.http.Part part :parts)
// {
// if (part.getContentType()!=null){
// //save file
// System.out.println("2");
// String id=part.getName();
// System.out.println(id);
// part.write(getServletContext().getRealPath("/WEB-INF")+"/"+"icon"+id);
//
//
//
// }else{
// //get filed name/vlaue
// System.out.println("3");
// String partName = ((File) part).getName();
// String fieldValue=request.getParameter(partName);
// System.out.println(fieldValue);
// }
// System.out.println("4");
// }
// }catch (Exception e) {
// // TODO: handle exception
// }
// System.out.println("5");
//
//
// }
//
// }
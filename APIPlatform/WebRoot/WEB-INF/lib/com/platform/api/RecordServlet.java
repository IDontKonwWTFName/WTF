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


////
@WebServlet("/record")
public class RecordServlet extends HttpServlet {
	// private String getFilename
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id_by_session = "18646098148";
		System.out.println("upload");
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json");
		
		;// 设置编码

		String value = "";
		Date now = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy_MM_dd_HH_mm_ss");

		String time = dateFormat.format(now);

		System.out.println(time
				+ "-----------------------------------------------");

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 判断是是否是Multipart
		if (isMultipart) {
			System.out.println("is multipart");

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
			factory.setSizeThreshold(1024 * 1024);
			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setHeaderEncoding("utf-8");

			Map<String, String> postdata = new HashMap<String, String>();

			String path = "";
			SessionFactory sf = new Configuration().configure()
					.buildSessionFactory();
			Session session = sf.openSession();
			Transaction t = session.beginTransaction();

			Map<String, String> data = new HashMap<String, String>();
			try {
				List<FileItem> items = upload.parseRequest(request);
				JSONObject js = null;
				for (FileItem item : items) {
					if (item.isFormField()) { // username="username"
						// 设置字符串，获取json文件
						String name = item.getFieldName();
						value = item.getString("utf-8");
						js = JSONObject.fromObject(value);
						System.out.println(name + "+++++" + value);
						// shouhuan.set

						// System.out.println(name + " = " + value);
					} else { // 文件
						// 传输时，Json文件在前面
						// 获取路径,得提前知道卸载那个文件夹，语音文件夹,
						// String realPath
						// =this.getServletContext().getRealPath("historyrecord/");
						// String realPath
						// =this.getServletContext().getRealPath("WEB-INF/data/HistoryRecord/"+js.getString("shouhuan_id"));
						String realPath = "D:/data/HistoryRecord/"
								+ js.getString("shouhuan_id");
						System.out.println("url:" + realPath);
						// 创建文件夹
						File dir = new File(realPath);

						if (!dir.exists()) {
							dir.mkdir();
						}
						// 将文件写入磁盘,聊天记录的地址是以shouhuan_id文件夹
						String name = item.getFieldName();
						System.out.println("------------------------");
						String filename = item.getName();
						path = js.getString("from_id") + "_" + time
								+ filename.substring(filename.lastIndexOf("."));
						item.write(new File(dir, path));
						// User_info user_info=new User_info();
						// 将path写入数据库

					}

				}

				Boolean type = null;
				if (js.getString("from_type").equals("1")) {
					type = true;
				} else {
					type = false;
				}
				Historyrecord hr = new Historyrecord();
				hr.setShouhuan_id(js.getString("shouhuan_id"));
				hr.setFrom_id(js.getString("from_id"));
				hr.setTime(now);
				hr.setRecord_url(path);
				hr.setFrom_type(type);
				hr.setIsHeard(false);
				session.save(hr);
				t.commit();
				

				data.put("code", "100");
				data.put("msg", "上传数据成功");
				data.put("data", "");
				

			} catch (Exception e) {
				e.printStackTrace();
				data.put("code", "500");
				data.put("msg", "上传数据失败");
				data.put("data", "");
			} finally {
				session.close();
				sf.close();
			}
			response.getWriter().println(
					JSONObject.fromObject(data).toString());

		}
	}

	

}


package com.platform.api;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;  
import javax.annotation.processing.FilerException;
import javax.mail.Part;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
//	private String getFilename
		public void doPost(HttpServletRequest request,
				HttpServletResponse response)throws ServletException,IOException{
			    System.out.println("1");
			    request.setCharacterEncoding("utf-8");//设置编码
			    boolean isMultipart = ServletFileUpload.isMultipartContent(request); 
			   
		        if(isMultipart){  
		        	System.out.println("is multipart");
		            String realPath = this.getServletContext().getRealPath("upload");  
		              
		            System.out.println(realPath);  
		              
		            File dir = new File(realPath);  
		              
		            if(!dir.exists()){  
		                dir.mkdir();  
		            }  
		              
		            DiskFileItemFactory factory = new DiskFileItemFactory();  
		            factory.setSizeThreshold(1024*1024) ; 
		            ServletFileUpload upload = new ServletFileUpload(factory);  
		              
		            upload.setHeaderEncoding("utf-8");  
		              
		            try {  
		                List <FileItem> items = upload.parseRequest(request);  
		                   
		                for(FileItem item : items){  
		                    if(item.isFormField()){ //username="username"  
		                        String name = item.getFieldName();  
		                        String value = item.getString("utf-8");  
		                          
		                        System.out.println(name + " = " + value);  
		                    } else { //文件  
		                        String name = item.getName();  
		                        item.write(new File(dir, System.currentTimeMillis() + name.substring(name.lastIndexOf("."))));  
		                          
		                    }  
		                      
		                }  
		                  
		            } catch (Exception e) {  
		                e.printStackTrace();  
			    
			    
		}
		            }
		        }

}

//package com.platform.api;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Collection;
//
//import javax.annotation.processing.FilerException;
//import javax.mail.Part;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
//import antlr.collections.List;
//@WebServlet("/upload")
//public class UploadServlet extends HttpServlet {
////	private String getFilename
//		public void doPost(HttpServletRequest request,
//				HttpServletResponse response)throws ServletException,IOException{
//			    System.out.println("1");
//			    request.setCharacterEncoding("utf-8");//设置编码
//			    
////			    String id = request.getParameter("user_id");
////				String birthString = request.getParameter("birthday");
////				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////				
////				java.util.Date birth = null;
////				try {
////					birth = sdf.parse(birthString);
////				} catch (ParseException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////				//String url = request.getParameter("headiconurl");
////				String urlString="";//后面根据磁盘来写
////				Integer sex = 0;
////				String name = request.getParameter("username");
////				String num = request.getParameter("phonenumber");
////				String email = request.getParameter("email");
//			    
//			  //  DiskFileItemFactory factory =new DiskFileItemFactory();
//			   // String path = request.getServletContext().getRealPath("/upload");
//			    
//			    
//			   // factory.setRepository(new File(path));
//			    
//			   // factory.setSizeThreshold(1024*1024);
//			    
//			   // ServletFileUpload upload= new ServletFileUpload(factory);
//			    
//			    try{
//			    	//可以上传多个文件
//			    	//Collection<Part> parts =(Collection<Part>)upload.parseParameterMap(request);
//			    	Collection<javax.servlet.http.Part> parts =request.getParts();
//			    	for(javax.servlet.http.Part part :parts)
//			    	{
//			    		if (part.getContentType()!=null){
//			    			//save file
//			    			System.out.println("2");
//			                String id=part.getName();
//			                System.out.println(id);
//			    			part.write(getServletContext().getRealPath("/WEB-INF")+"/"+"icon"+id);		
//
//    			
//		
//			    		}else{
//			    			//get filed name/vlaue
//			    			System.out.println("3");
//			    			String partName = ((File) part).getName();
//			    			String fieldValue=request.getParameter(partName);
//			    			System.out.println(fieldValue);
//			    		}
//			    		System.out.println("4");
//			    	}
//			    }catch (Exception e) {
//					// TODO: handle exception
//				} 
//			    System.out.println("5");
//			    
//			    
//		}
//
//}
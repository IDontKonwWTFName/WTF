package tools.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import tools.getLocalhost;

public class login extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,  IOException{
		response.setContentType("text/html;charset=gb2312");
		
		boolean flag = false;
		String user_id = request.getParameter("user_id");
		String passwd = request.getParameter("passwd");
		String jsonStr = "";
		
		String pathType = "login";
  		getLocalhost glh = new getLocalhost();
  		
//  		DefaultHttpClient client = new DefaultHttpClient();
//  		HttpPost post = new HttpPost(glh.getUrl() + pathType);
//  		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//  		nvps.add(new BasicNameValuePair("user_id", user_id));
//  		nvps.add(new BasicNameValuePair("passwd", passwd));
//  		post.setEntity(new UrlEncodedFormEntity(nvps));
//  		HttpResponse resp = client.execute(post);
//  		client.getConnectionManager().shutdown();
//  		
//  		if(resp.getStatusLine().getStatusCode() == 200){
//  			jsonStr = new String(EntityUtils.toString(resp.getEntity(), "utf-8"));
//  		}
//  		
//  		if(flag){
  			response.sendRedirect("login_check.jsp?user_id=" + "");
//  		}else{
//  			response.sendRedirect("login.jsp");
//  		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}

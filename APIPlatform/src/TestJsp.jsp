<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<style type="text/css">
 	body{background-color:#CCFFFF}
 	table{
 		background-color:#FFCCCC
 	} 
</style>

  </head>
  <%
  	String name = (String)request.getAttribute("name");
  	int id = (Integer)request.getAttribute("id");
  	int order = (Integer)request.getAttribute("order");
  	String address = (String)request.getAttribute("address");
  	String phone = (String)request.getAttribute("phone");
   %>
  <body> 
  <center><h3>登录</h3></center>
  	<form action="login" method="post">
  	<center><table>
  		<tr><td>ID</td><td><input name = "shouhuan_id" value="18646098148"/></td></tr>
  		<tr><td>password</td><td><input name="passwd" value="19940210" /></td></tr> 
  		<tr><td colspan="2"><input type="submit" value="提交"/></td></tr> 
  	</table>
  	</center>
  	</form>
  </body>
</html>

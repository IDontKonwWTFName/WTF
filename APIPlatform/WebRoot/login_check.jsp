<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
    <title>My JSP 'login_check.jsp' starting page</title>
  </head>
  	<%
  		String user_id = request.getParameter("user_id");
  		session.removeAttribute("user_id");
  		session.setAttribute("user_id", user_id);
  	 %>
  <body>
  <jsp:forward page="main.jsp"/>
  </body>
</html>

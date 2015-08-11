<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<html>
  <head>
    <title>My JSP 'login_out.jsp' starting page</title>
  </head>
  <body>
    <%	//String pageType = request.getParameter("pageType");
    	//if(pageType.equals("login_out")){
    		session.removeAttribute("userId");
    	//}
    %>
    <jsp:forward page="login.jsp"/>
     
     
  </body>
</html>

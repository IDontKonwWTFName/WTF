<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>    
    <title>My JSP 'login.jsp' starting page</title>
<!-- CSS -->

<link rel="stylesheet" href="css/supersized.css">
<link rel="stylesheet" href="css/login.css">
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	<script src="js/html5.js"></script>
<![endif]-->
<script src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/tooltips.js"></script>
<script type="text/javascript" src="js/login.js"></script>

  </head>

  <body>
<div class="page-container">
	<div class="main_box">
		<div class="login_box">
			<div class="login_logo">
				<img src="images/logo.png" >
			</div>
		
			<div class="login_form">
				<form action="login" id="login_form" method="get">
					<div class="form-group">
						<label for="j_username" class="t" >帐　户：</label><SPAN class="u_logo"></SPAN> 
						 <input id="user_id" name="user_id" type="text" class="form-control x319 in" autocomplete="off" placeholder="用户名或邮箱" >
					</div>
					<div class="form-group">
						<label for="j_password" class="t">密　码：</label> 
						<input id="passwd" name="passwd" type="password" class="password form-control x319 in" placeholder="密码">
					</div>
					<div class="form-group space">
						<label class="t"></label>　　　
						<input type="submit"  id="submit_btn" class="btn btn-primary btn-sm" value="登录">
						<input type="button"  id="register_btn" class="btn btn-primary btn-sm" value="注册">
						<input type="reset" value="重置" class="btn btn-default btn-sm">
					</div>
				</form>
			</div>
		</div>
		<div class="bottom">Copyright &copy; 2014 - 2015 <a href="#">系统登陆</a></div>
	</div>
</div>

<!-- Javascript -->

<script src="js/supersized.3.2.7.min.js"></script>
<script src="js/supersized-init.js"></script>
<script src="js/scripts.js"></script>

</div>
  </body>
</html>

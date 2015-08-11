<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>    
    <title>My JSP 'setMain.jsp' starting page</title>
	<script type="text/javascript" src="js/mootools.js"></script>
	<script type="text/javascript" src="js/calendar.rc4.js"></script>
	<script type="text/javascript">
	//<![CDATA[
		window.addEvent('domready', function() { 
			myCal1 = new Calendar({ date1: 'd/m/Y' }, { direction: 1, tweak: {x: 6, y: 0} });
			myCal2 = new Calendar({ date2: 'd/m/Y' }, { classes: ['dashboard'], direction: 1, tweak: {x: 3, y: -3} });
			myCal3 = new Calendar({ date3: 'd/m/Y' }, { classes: ['i-heart-ny'], direction: 1, tweak: {x: 3, y: 0} });
		});
	//]]>
	</script>
	
	<link rel="stylesheet" type="text/css" href="css/calendar.css" media="screen" />

	<link href="css/styles.css" rel="stylesheet" type="text/css" />
     <style type="text/css">
     #container { width: 730px; height:730px; text-align:center;  float: right;margin:0 auto; }
     </style>
  </head>
  
  <body>
    <jsp:include page="menu.jsp"/>
    	<div class="mainContent">
    <aside>   
      <section class="newpic">
      <h2>星光北斗宝贝信息</h2>
      	<div class="fun">
          <ul>
           <li><a href="bindingShouhuan.jsp">绑定手环</a></li><br>
           <li><a href="setShouhuan.jsp">手环设置</a></li><br>
           <li><a href="setUser_Main.jsp">用户信息</a></li><br>
           <li><a href="reCharge.jsp">充值缴费</a></li><br>
          </ul>
        </div>
        
      </section> 
      </aside>
        <div class="blogitem">
              <h1 style="text-align:center">主页</h1>
              <hr/>
                   <div class="set2"></div>
		  <div class="set2">
			<!-- 	<button type="button" onclick="window.location.href='commonUser/setPhone.jsp'"  class="btn btn-primary btn-lg">电话设置</button>		 -->			
		  </div>	
	 </div>
   </div>
    <jsp:include page="footer.jsp"/>
  </body>
</html>

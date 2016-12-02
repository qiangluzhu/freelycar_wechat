<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
  </head> 
  <body>
    This is my JSP page. <br>
		<form action="api/reservation/statistic" method="get">
		<span>关键字</span>
		<input type="text" name="openId" ><br/>
		<input type="text" name="start" ><br/>
		<input type="text" name="number" ><br/>
		<input type="submit" value="submit">
		</form>
  </body>
</html>
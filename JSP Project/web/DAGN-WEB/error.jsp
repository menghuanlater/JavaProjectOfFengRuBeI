<%--
  Created by IntelliJ IDEA.
  User: 止水清潇
  Date: 2017-02-21
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="shortcut icon" href="imgs/head/4.jpg" type="image/x-icon"/>
</head>
<body>
<%
    String username = request.getParameter("username");
%>
    <div align="center" style="margin-top: 150px">
        <p style="font-family: 'Times New Roman','楷体';font-size: 30px">Not Found User!(未查询到<%=username%>)</p>
        <a href="welcome.jsp">Click Back(点击返回)</a>
    </div>
</body>
</html>

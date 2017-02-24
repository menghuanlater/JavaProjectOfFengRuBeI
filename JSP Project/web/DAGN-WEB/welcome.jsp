<%--
  Created by IntelliJ IDEA.
  User: 止水清潇
  Date: 2017-02-17
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GitHub开发者数据挖掘与分析</title>
    <link rel="shortcut icon" href="imgs/head/4.jpg" type="image/x-icon"/>
    <link rel="stylesheet" href="DAGH-CSS.css" type="text/css"/>
</head>
<body class="welcomeBody">
<table border="0" style="margin-left: 50px;margin-top:150px;margin-right: 50px">
<tr>
<td class="welcomeLeftDiv" id="leftDiv">
<p style="font-size: 30px;margin-left: 20px">
    WebSite Introduction
</p>
<jsp:include page="WebIntro1.jsp"/>
</td>
<td class="welcomeMiddleDiv" id="middleDiv" align="center">
<jsp:include page="login.jsp"/>
</td>
<td class="welcomeRightDiv" id="rightDiv">
<p style="font-size: 30px;margin-right: 20px;margin-left: 30px">
    WebSite Functions
</p>
<jsp:include page="WebIntro2.jsp"/>
</td>
</tr>
</table>
<!--第一个javascript脚本是用于字体闪烁
<script language="JavaScript" src="FontShine.js">
</script>-->
</body>
</html>

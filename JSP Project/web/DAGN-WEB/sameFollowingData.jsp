<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="jxl.Workbook" %>
<%@ page import="jxl.Sheet" %>
<%@ page import="jxl.read.biff.BiffException" %><%--
  Created by IntelliJ IDEA.
  User: 止水清潇
  Date: 2017-02-22
  Time: 23:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>详细共同Follow情况</title>
    <link rel="shortcut icon" href="imgs/head/4.jpg" type="image/x-icon"/>
    <link rel="stylesheet" href="DAGH-CSS.css" type="text/css"/>
</head>
<body class="outPrintBody">
<%
    String target = request.getParameter("target");
    int row = Integer.parseInt(request.getParameter("row"));
    String username = request.getParameter("username");
    String filepath = String.format("D:/githubOutPut/Follow/%s.xls",username);
    InputStream stream = new FileInputStream(filepath);
    Workbook readBook = null;
    try {
        readBook = Workbook.getWorkbook(stream);
    } catch (BiffException e) {
        e.printStackTrace();
    }
    Sheet defaultSheet = readBook.getSheet(0);
    int loop = Integer.parseInt(defaultSheet.getCell(1,row).getContents());
%>
<p class="outPrintHeadContent1">
    <%=username%>与<%=target%>共同Follow的<%=loop%>位开发者信息如下:
</p><br>
<table id="sameFollow" align="center" class="sameFollowForkTable" cellpadding="0" cellspacing="0">
</table>
<script language="JavaScript">
    var developers = new Array();
    <%
    for(int i=1;i<=loop;i++){
    %>
    developers[<%=i-1 %>] = "<%=defaultSheet.getCell(1+i,row).getContents()%>";
    <%}
    %>
    window.onload = function () {
        var tr;
        for(var i=0;i<<%=loop%>;i++)
        {
            var table = document.getElementById("sameFollow");
            if(i%4==0){
                tr = document.createElement("tr");
                table.appendChild(tr);
            }
            var random = parseInt(Math.random()*(100)+66,10);
            var node1 = document.createTextNode("开发者:"+developers[i]);
            var githubLink = document.createElement("a");//创建github链接
            var img = document.createElement("img");//创建图像
            var td1 = document.createElement("td");//创建td
            var td2 = document.createElement("td");//创建td
            githubLink.href = "https://github.com/"+developers[i];
            githubLink.target = "_blank";
            githubLink.title = "点击进入"+developers[i]+"的GitHub主页";
            githubLink.setAttribute("style","text-decoration: none;vertical-align:middle;color:#E0FFFF");
            img.setAttribute("style","width:110px;height:100px;text-align:left;vertical-align:middle");
            img.src = "imgs/follow/"+(random%66)+".jpg";
            td1.setAttribute("style","width:112px;height:115px;vertical-align:middle");
            td2.setAttribute("style","width:250px;height:115px;text-align:center;color:#FFFFFF;vertical-align:middle;" +
                "font-family: 'Times New Roman', '楷体';font-size: 20px");
            githubLink.appendChild(node1);
            td1.appendChild(img);
            td2.appendChild(githubLink);
            tr.appendChild(td1);
            tr.appendChild(td2);
        }
    }
</script>
</body>
</html>

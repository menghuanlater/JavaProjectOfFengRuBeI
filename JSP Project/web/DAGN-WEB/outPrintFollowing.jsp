<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="jxl.Workbook" %>
<%@ page import="jxl.Sheet" %>
<%@ page import="jxl.read.biff.BiffException" %>
<%--
  Created by IntelliJ IDEA.
  User: 止水清潇
  Date: 2017-02-21
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Found</title>
    <link rel="shortcut icon" href="imgs/head/4.jpg" type="image/x-icon"/>
    <link rel="stylesheet" href="DAGH-CSS.css" type="text/css"/>
</head>
<body class="outPrintBody">
<p class="outPrintHeadContent1">
    查询结果如下：
    <input type="button" onclick="DownLoad()" title="点击下载excel格式的结果文件" value="DownLoad" class="downLoad">
</p>
<%
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
    int rows = defaultSheet.getRows() - 1;//获取excel的行数
%>
<p class="outPrintHeadContent2">
    一共有<%=rows %>个开发者
</p><br>
<table id="sameFollow" align="center" class="sameFollowForkTable" cellpadding="0" cellspacing="0">
</table>
<script language="JavaScript">
    function DownLoad() {
        window.location.href = "download.jsp?filepath=<%=filepath%>&username=<%=request.getParameter("username")%>";
    }
    var userName = new Array();
    var sameFollowingCount = new Array();
    <%
    for(int i=1;i<=rows;i++){
    %>
        userName[<%=i-1%>] = "<%=defaultSheet.getCell(0,i).getContents()%>";
        sameFollowingCount[<%=i-1%>] = <%=defaultSheet.getCell(1,i).getContents()%>;
    <%}
    %>
    window.onload = function () {
        var tr;
        for(var i=0;i<Number(<%=rows %>);i++)
        {
            var table = document.getElementById("sameFollow");
            if(i%4==0){
                tr = document.createElement("tr");
                table.appendChild(tr);
            }
            var random = parseInt(Math.random()*(100)+66,10);
            var node1 = document.createTextNode("开发者:"+userName[i]);
            var node2 = document.createTextNode("共同Follow:"+sameFollowingCount[i]);
            var link = document.createElement("a");//创建链接
            var githubLink = document.createElement("a");//创建github链接
            var img = document.createElement("img");//创建图像
            var td1 = document.createElement("td");//创建td
            var td2 = document.createElement("td");//创建td
            //var pre = document.createElement("pre");
            var br = document.createElement("br");
            link.href = "sameFollowingData.jsp"+"?row="+(i+1)+"&username=<%=username%>&target="+userName[i];
            link.target = "_blank";
            link.title = "点击查看详细内容";
            githubLink.href = "https://github.com/"+userName[i];
            githubLink.target = "_blank";
            githubLink.title = "点击进入"+userName[i]+"的GitHub主页";
            link.setAttribute("style","text-decoration: none;vertical-align:middle;color:#F0BBFF");
            githubLink.setAttribute("style","text-decoration: none;vertical-align:middle;color:#E0FFFF");
            img.setAttribute("style","width:110px;height:100px;text-align:left;vertical-align:middle");
            img.src = "imgs/follow/"+(random%66)+".jpg";
            td1.setAttribute("style","width:112px;height:115px;vertical-align:middle");
            td2.setAttribute("style","width:250px;height:115px;text-align:center;color:#FFFFFF;vertical-align:middle;" +
                "font-family: 'Times New Roman', '楷体';font-size: 20px");
            //pre.setAttribute("style","font-family: 'Times New Roman', '楷体';font-size: 20px");
            //
            githubLink.appendChild(node1);
            link.appendChild(node2);
            td1.appendChild(img);
            td2.appendChild(githubLink);
            td2.appendChild(br);
            td2.appendChild(link);
            tr.appendChild(td1);
            tr.appendChild(td2);
        }
    }
</script>
</body>
</html>

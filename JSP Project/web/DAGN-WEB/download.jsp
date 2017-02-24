<%--
  Created by IntelliJ IDEA.
  User: 止水清潇
  Date: 2017-02-22
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.ServletOutputStream"%>
<%@ page contentType="application/x-xls" language="java" %>
<html>
<head>
    <title>下载</title>
<body>
<%
    out.clear();
    out = pageContext.pushBody();
    String filename = request.getParameter("username")+".xls";
    response.setHeader("Content-Disposition","inline;filename="+filename);
    File fileLoad = new File(request.getParameter("filepath"));
    long fileLength = fileLoad.length();
    String length = String.valueOf(fileLength);
    response.setHeader("Content-Length",length);
    FileInputStream input =null;
    ServletOutputStream output = null;
    try{
        input = new FileInputStream(fileLoad);
        output = response.getOutputStream();
        byte [] block = new byte[1024];
        int len;
        while((len = input.read(block))!= -1 ){
            output.write(block,0,len);
        }
    }catch(Exception e){
        e.printStackTrace();
    }finally {
        try{
            input.close();
            output.flush();
            output.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
%>
</body>
</head>
</html>

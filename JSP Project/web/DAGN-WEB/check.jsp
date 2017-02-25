<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: 止水清潇
  Date: 2017-02-17
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String url = "jdbc:mysql://localhost:3306/github?characterEncoding=utf-8&useSSL=true";
    final String name = "com.mysql.jdbc.Driver";
    final String user = "root";
    final String password = "root";
    Connection conn;
    PreparedStatement pst;
    String sql = String.format("select * from users_name where user_name='%s'",request.getParameter("username"));
    ResultSet ret = null;
    try {
        Class.forName(name);//指定连接类型
        conn = DriverManager.getConnection(url,user,password);
        pst = conn.prepareStatement(sql);
        pst.setFetchSize(Integer.MIN_VALUE);
        ret = pst.executeQuery();
    }catch(Exception e){
        e.printStackTrace();
    }
    boolean flag = ret.next();
    if(flag && request.getParameter("model").equals("following")){
%>
<jsp:forward page="outPrintFollowing.jsp"></jsp:forward>
<%
    }else if(flag && request.getParameter("model").equals("fork")){
%>
<jsp:forward page="outPrintProject.jsp"></jsp:forward>
<%
    }else{
%>
<jsp:forward page="error.jsp"/>
<%
    }
%>

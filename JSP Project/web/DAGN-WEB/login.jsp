<%--
  Created by IntelliJ IDEA.
  User: 止水清潇
  Date: 2017-02-17
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="DAGH-CSS.css" rel="stylesheet" type="text/css">
<form name="myForm" method="post" action="check.jsp" target="_self">
    <b style="color: #FF0066;font-size: 25px">GitHub开发者用户名:</b><input type="text" title="开发者用户名" name="username" class="userNameInput"><br><br>
    <b style="color: #8A2BE2;font-size: 25px">选择模式:&nbsp;&nbsp;&nbsp;</b><input type="radio" name="model" value="following"><label style="color: coral;font-size: 23px">Following</label>
    &nbsp; <input type="radio" name="model" value="fork"><label style="color: coral;font-size: 23px">Fork Projects</label><br><br>
<input type="button" value="查询" onclick="check()" class="searchButton1">
</form>
<script language="JavaScript">
    function check() {
        if(myForm.username.value==''){
            alert("Sorry,你没有输入GitHub开发者名！");myForm.username.focus();
            return;
        }
        var modelFalg = false;
        var model = document.getElementsByName("model");
        for(var i=0;i<model.length;i++){
            if(model.item(i).checked == true){
                modelFalg = true;
                break;
            }
        }
        if(modelFalg)
            myForm.submit();
        else
            alert("Sorry,你还没有选择分析模式！");
    }
</script>
<%--
  Created by IntelliJ IDEA.
  User: liuyj
  Date: 2018/10/9
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>欢迎登录后台管理系统</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="/js/jquery.js"></script>
    <script src="/js/cloud.js" type="text/javascript"></script>

    <script language="javascript">
        $(function(){
            $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            $(window).resize(function(){
                $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            })
        });
        
        function change() {
            //获得验证码图片并改变它的地址
            $("#random").attr("src", "/random.jpg?time="+new Date().toLocaleString());
        }
    </script>

</head>

<body style="background-color:#1c77ac; background-image:url(images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">



<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>


<div class="logintop">
    <span>欢迎访问尚学堂OA系统</span>
    <ul>
        <li><a href="#">回首页</a></li>
        <li><a href="#">帮助</a></li>
        <li><a href="#">关于</a></li>
    </ul>
</div>

<div class="loginbody">

    <span class="systemlogo"></span>

    <div class="loginbox loginbox2">
        <form action="/EmployeeServlet" method="post">
            <input type="hidden" name="method" value="login"/>
        <ul>
            <li><input name="empid" type="text" class="loginuser" value="xiaozhu" onclick="JavaScript:this.value=''"/></li>
            <li><input name="password" type="password" class="loginpwd" value="123456" onclick="JavaScript:this.value=''"/></li>
            <span style="color:red; font-size: 12px; font-weight: bold">${msg}</span>
            <li class="yzm">
                <span><input name="verifyCode" type="text" value="验证码" onclick="JavaScript:this.value=''"/></span><cite><img src="/random.jpg" id="random" alt="" onclick="javascript:change()" /></cite>
            </li>
            <li><input name="" type="submit" class="loginbtn" value="登录"  onclick="javascript:window.location='main.html'"  /><label><input name="" type="checkbox" value="" checked="checked" />记住密码</label><label><a href="#">忘记密码？</a></label></li>
        </ul>
        </form>
    </div>
</div>
</body>
</html>

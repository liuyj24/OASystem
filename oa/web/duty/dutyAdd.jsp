<%--
  Created by IntelliJ IDEA.
  User: liuyj
  Date: 2018/10/10
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            //给签到按钮绑定一个单击事件
            $("#signin").click(function () {
                //发送一个ajax请求, 完成签到, 并通过回调函数显示签到结果
                $.ajax({
                    url:"/DutyServlet?method=signin",
                    type:"POST",
                    dataType:"text",
                    success:function (data) {//0失败, 1成功, 2已经签到
                        //显示签到的结果
                        if(0 == data){
                            $("#result").html("签到失败");
                        }else if(1 == data){
                            $("#result").html("签到成功");
                        }else{
                            $("#result").html("已经签到, 不能重复签到");
                        }
                    }
                })
            })
            $("#signout").click(function () {
                //发送一个ajax请求, 完成签退, 通过回调函数显示结果
                $.ajax({
                    url:"/DutyServlet?method=signout",
                    success:function (data) {
                        $("#result").html(data);
                    }
                })
            })
        });
    </script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">考勤管理</a></li>
        <li><a href="#">签到签退</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>

    <ul class="forminfo">
        <li><label>&nbsp;</label><input name="" type="button" class="btn" value="签到" id="signin"/> 每天签到一次，不可重复签到</li>
        <li><label>&nbsp;</label></li>
        <li><label>&nbsp;</label></li>
        <li><label>&nbsp;</label><input name="" type="button" class="btn" value="签退" id="signout"/>可重复签退，以最后一次签退为准</li>
    </ul>
    <span id="result"></span>

</div>


</body>

</html>


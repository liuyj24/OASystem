<%--
  Created by IntelliJ IDEA.
  User: liuyj
  Date: 2018/10/6
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            $(".click").click(function(){
                $(".tip").fadeIn(200);
            });

            $(".tiptop a").click(function(){
                $(".tip").fadeOut(200);
            });

            $(".sure").click(function(){
                $(".tip").fadeOut(100);
            });

            $(".cancel").click(function(){
                $(".tip").fadeOut(100);
            });

        });
    </script>
    <script type="text/javascript">
        function delete_confirm(posid) {
            var flag = window.confirm("您是否要删除该职位?");
            if(flag){
                location.href = "/PositionServlet?method=delete&posid="+posid;
            }
        }
    </script>


</head>


<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">人事管理</a></li>
        <li><a href="#">岗位管理</a></li>
    </ul>
</div>

<div class="rightinfo">


    <div class="formtitle1"><span>岗位列表</span></div>

    <table class="tablelist" >
        <thead>
        <tr>
            <th><input name="" type="checkbox" value="" checked="checked"/></th>
            <th>编号<i class="sort"><img src="/images/px.gif" /></i></th>
            <th>岗位名称</th>
            <th>岗位描述</th>
            <th>操作</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${list}" var="pos">
        <tr>
            <td><input name="" type="checkbox" value="" /></td>
            <td>${pos.posid}</td>
            <td>${pos.pname}</td>
            <td>${pos.pdesc}</td>
            <td>  <a href="/PositionServlet?method=findById&posid=${pos.posid}" class="tablelink">修改</a>   <a href="javascript:delete_confirm(${pos.posid})" class="tablelink click"> 删除</a></td>
        </tr>
        </c:forEach>

        </tbody>
    </table>



    <div class="tip">
        <div class="tiptop"><span>提示信息</span><a></a></div>

        <div class="tipinfo">
            <span><img src="images/ticon.png" /></span>
            <div class="tipright">
                <p>是否确认对信息的修改 ？</p>
                <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
            </div>
        </div>

        <div class="tipbtn">
            <input name="" type="button"  class="sure" value="确定" />&nbsp;
            <input name="" type="button"  class="cancel" value="取消" />
        </div>

    </div>




</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>

</body>

</html>


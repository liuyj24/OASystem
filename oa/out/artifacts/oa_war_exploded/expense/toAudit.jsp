<%--
  Created by IntelliJ IDEA.
  User: liuyj
  Date: 2018/10/14
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            $(".click").click(function() {
                $(".tip").fadeIn(200);
            });
            $(".tiptop a").click(function() {
                $(".tip").fadeOut(200);
            });
            $(".sure").click(function() {
                $(".tip").fadeOut(100);
            });
            $(".cancel").click(function() {
                $(".tip").fadeOut(100);
            });
        });
    </script>

</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">报销管理</a></li>
        <li><a href="#">待审报销</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="formtitle1"><span>待审报销</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>
                <input name="" type="checkbox" value="" checked="checked" />
            </th>
            <th>报销人<i class="sort"><img src="images/px.gif" /></i></th>
            <th>报销总额</th>
            <th>报销时间</th>
            <th>总备注信息</th>
            <th>查看具体报销项</th>
            <th>查看所附图片</th>
            <th>审核</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="exp">
            <tr>
                <td>
                    <input name="" type="checkbox" value="" />
                </td>
                <td>${exp.emp.realname}</td>
                <td>${exp.totalAmount}</td>
                <td>${exp.expTime}</td>
                <td>${exp.expDesc}</td>
                <td><a href="expenseDetail.html" class="tablelink"> 查看具体报销项</a></td>
                <td> <a href="expenseImg.html" class="tablelink">查看所附图片</a></td>
                <td><a href="javascript:open('/expense/audit.jsp?expid=${exp.expid}','','width=500,height=230,left=300,top=300,location=0,scrollbars=0,resizable=0')" class="tablelink">审核</a>  <a href="auditHistory.html" class="tablelink">查看审核记录</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>

</body>

</html>

<%--
  Created by IntelliJ IDEA.
  User: liuyj
  Date: 2018/10/8
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <link href="/css/select.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="/js/jquery.idTabs.min.js"></script>
    <script type="text/javascript" src="/js/select-ui.min.js"></script>
    <script type="text/javascript" src="/editor/kindeditor.js"></script>
    <script type="text/javascript">
        $(document).ready(function(e) {
            $(".select1").uedSelect({
                width : 345
            });

        });
    </script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">人事管理</a></li>
        <li><a href="#">修改员工信息</a></li>
    </ul>
</div>

<div class="formbody">

    <div class="formtitle"><span>基本信息</span></div>
    <form action="/EmployeeServlet" method="post">
    <input type="hidden" name="method" value="update"/>
    <ul class="forminfo">
        <li>
            <label>用户名</label>
            <input name="empid" type="text" class="dfinput" value="${emp.empid}" readonly="readonly" /><i>必须唯一，也可以根据真实姓名自动生成</i></li>
        <li>
        <li>
            <label>真实姓名</label>
            <input name="realname" type="text" class="dfinput" value="${emp.realname}"/><i></i></li>
        <li>
            <label>性别</label><cite>

            <c:if test="${'男' == emp.sex}">
                <input name="sex" type="radio" value="男" checked="checked" />男&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="sex" type="radio" value="女" />女<i>也可以根据身份证号自动获取</i></cite>
            </c:if>
            <c:if test="${'女' == emp.sex}">
                <input name="sex" type="radio" value="男" />男&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="sex" type="radio" value="女" checked="checked" />女<i>也可以根据身份证号自动获取</i></cite>
            </c:if>

        </li>
        <li>
            <label>出生日期</label>
            <input name="birthday" type="text" class="dfinput" value="${emp.birthday}" /><i>也可以根据身份证号自动获取</i></li>
        <li>
        <li>
            <label>入职时间</label>
            <input name="hiredate" type="text" class="dfinput" value="${emp.hireDate}" /><i></i></li>

        <li>
            <label>离职时间</label>
            <input name="leavedate" type="text" class="dfinput" value="${emp.leaveDate}" /><i></i></li>
        <li>
            <label>是否在职</label><cite>
            <c:if test="${1 == emp.onDuty}">
                <input name="onDuty" type="radio" value="1" checked="checked" />是&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="onDuty" type="radio" value="0" />否</cite>
            </c:if>
            <c:if test="${0 == emp.onDuty}">
                <input name="onDuty" type="radio" value="1" />是&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="onDuty" type="radio" value="0" checked="checked" />否</cite>
            </c:if>
        </li>
        <li>
            <label>所属部门<b>*</b></label>
            <div class="vocation">
                <select class="select1" name="deptno">
                    <c:forEach items="${deptList}" var="dept">
                        <c:if test="${dept.deptno == emp.dept.deptno}">
                            <option value="${dept.deptno}" selected="selected">${dept.deptname}</option>
                        </c:if>
                        <c:if test="${dept.deptno != emp.dept.deptno}">
                            <option value="${dept.deptno}" >${dept.deptname}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>

        </li>
        <li>
            <label>员工类型</label><cite>
            <c:if test="${1 == emp.emptype}">
                <input name="empType" type="radio" value="1" checked="checked" />基层员工&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="empType" type="radio" value="2" />各级管理人员</cite>
            </c:if>
            <c:if test="${2 == emp.emptype}">
                <input name="empType" type="radio" value="1" />基层员工&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="empType" type="radio" value="2" checked="checked" />各级管理人员</cite>
            </c:if>
        </li>
        <li>
            <label>从事岗位<b>*</b></label>
            <div class="vocation">
                <select class="select1" name="posid">
                    <c:forEach items="${posList}" var="pos">
                        <c:if test="${pos.posid == emp.pos.posid}">
                            <option value="${pos.posid}" selected="selected">${pos.pname}</option>
                        </c:if>
                        <c:if test="${pos.posid != emp.pos.posid}">
                            <option value="${pos.posid}" >${pos.pname}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </li>
        <li>
        <li>
            <label>直接上级<b>*</b></label>
            <div class="vocation">
                <select class="select1" name="mrgid">
                    <c:forEach items="${mrgList}" var="mrg">
                        <c:if test="${mrg.empid == emp.mgr.empid}">
                            <option selected="selected" value="${mrg.empid}">${mrg.realname}</option>
                        </c:if>
                        <c:if test="${mrg.empid != emp.mgr.empid}">
                            <option value="${mrg.empid}>${mrg.realname}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            &nbsp;&nbsp;<input name="" type="text" class="dfinput"  placeholder="也可以在此输入首字母帮助显示"/></li>
        </li>
        <li>
            <label>联系方式</label>
            <input name="phone" type="text" class="dfinput" value="${emp.phone}" />
        </li>
        <li>
            <label>QQ号</label>
            <input name="qq" type="text" class="dfinput" value="${emp.qq}" />
        </li>
        <li>
            <label>紧急联系人信息</label>
            <textarea name="emercContactPerson" cols="" rows="" class="textinput" >${emp.emerContactperson}</textarea>
        </li>
        <li>
            <label>身份证号</label>
            <input name="idcard" type="text" class="dfinput" value="${emp.idcard}" />
        </li>
        <li>
            <label>&nbsp;</label>
            <input name="" type="submit" class="btn" value="确认保存" />
        </li>
    </ul>
    </form>

</div>

</body>

</html>

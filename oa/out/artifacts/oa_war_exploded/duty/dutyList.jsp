<%--
  Created by IntelliJ IDEA.
  User: liuyj
  Date: 2018/10/11
  Time: 14:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <link href="/css/select.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="/js/jquery.js"></script>

    <script type="text/javascript" src="/js/jquery.idTabs.min.js"></script>
    <script type="text/javascript" src="/js/select-ui.min.js"></script>
    <script type="text/javascript" src="/editor/kindeditor.js"></script>
    <script type="text/javascript">
        $(document).ready(function(e) {
            $(".select1").uedSelect({
                width : 200
            });

        });
    </script>
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
        $(function () {
            //加载完页面后发送ajax请求获取部门列表信息
            $.ajax({
                url:"/DutyServlet?method=findAllDept",
                success:function (jsonStr) {
                    //获得所有部门的数组
                    eval("var arr = "+jsonStr);
                    //拼接option字符串
                    var optionStr = '<option value="0">--所有人--</option>';
                    for(var i = 0; i < arr.length; i++){
                        optionStr += "<option value="+ arr[i].deptno+">"+ arr[i].deptname +"</option>";
                    }
                    $("#deptno").html(optionStr);//等待测试
                }
            });

            //给查询按钮绑定点击事件
            $("#btn1").click(function () {
                var empid = $("#empid").val();
                var deptno = $("#deptno").val();
                var signTime = $("#signTime").val();

                //通过ajax获得创建请求并获得响应数据
                $.ajax({
                    url:"/DutyServlet?method=findDuty",
                    type:"POST",
                    data:{"empid":empid, "deptno":deptno, "signTime":signTime},
                    dataType:"text",
                    success:function(result) {
                        //把json数据转换为js对象
                        eval("var arr = "+result);

                        //拼接考勤信息列表
                        var dutyStr = "";
                        for(var j = 0; j < arr.length; j++){
                            dutyStr +=
                            '<tr>'+
                            '<td>'+
                            '<input name="" type="checkbox" value="" />'+
                            '</td>'+
                            '<td>'+arr[j].empid+'</td>'+
                            '<td>'+arr[j].emp.realname+'</td>'+
                            '<td>'+arr[j].emp.dept.deptname+'</td>'+
                            '<td>'+arr[j].dtDate+'</td>'+
                            '<td>'+arr[j].signinTime+'</td>'+
                            '<td>'+arr[j].signoutTime+'</td>'+
                            '</tr>';
                        }
                        $("#tbody").html(dutyStr);
                    }
                });

            });

            //给导出按键绑定事件
            $("#btn2").click(function () {
                var empid = $("#empid").val();
                var deptno = $("#deptno").val();
                var signTime = $("#signTime").val();

                location.href = "/DutyServlet?" +
                    "method=exportXLS&empid="+empid+"&deptno="+deptno+"&signTime="+signTime;
            });
        });
    </script>

</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">考勤管理</a></li>
        <li><a href="#">考勤管理</a></li>
    </ul>
</div>

<div class="rightinfo">

    <ul class="prosearch">
        <li>
            <label>查询：</label><i>用户名</i>
            <a>
                <input name="" type="text" class="scinput" id="empid" />
            </a><i>所属部门</i>
            <a>
                <select class="select1" id="deptno">
                </select>
            </a>
            <i>考勤时间</i>
            <a>
                <input name="" type="text" class="scinput" id="signTime" />
            </a>
            <a>
                <input name="" type="button" class="sure" id="btn1" value="查询" />

            </a>
            <a>
                <input name="" type="button" class="scbtn2" id="btn2" value="导出"/>

            </a>

        </li>


    </ul>

    <div class="formtitle1"><span>考勤管理</span></div>

    <table class="tablelist">
        <thead>
        <tr>
            <th>
                <input name="" type="checkbox" value="" checked="checked" />
            </th>
            <th>用户名<i class="sort"><img src="/images/px.gif" /></i></th>
            <th>真实姓名</th>
            <th>所属部门</th>
            <th>出勤日期</th>
            <th>签到时间</th>
            <th>签退时间</th>
        </tr>
        </thead>
        <tbody id="tbody">
        <%--<tr>--%>
            <%--<td>--%>
                <%--<input name="" type="checkbox" value="" />--%>
            <%--</td>--%>
            <%--<td>gaoqi</td>--%>
            <%--<td>高淇</td>--%>
            <%--<td>总裁办</td>--%>
            <%--<td>2007-10-1</td>--%>
            <%--<td>08:50</td>--%>
            <%--<td>18:05</td>--%>
        <%--</tr>--%>

        </tbody>
    </table>

    <div class="pagin">
        <div class="message">共<i class="blue">1256</i>条记录，当前显示第&nbsp;<i class="blue">2&nbsp;</i>页</div>
        <ul class="paginList">
            <li class="paginItem"><a href="javascript:;"><span class="pagepre"></span></a></li>
            <li class="paginItem"><a href="javascript:;">1</a></li>
            <li class="paginItem current"><a href="javascript:;">2</a></li>
            <li class="paginItem"><a href="javascript:;">3</a></li>
            <li class="paginItem"><a href="javascript:;">4</a></li>
            <li class="paginItem"><a href="javascript:;">5</a></li>
            <li class="paginItem more"><a href="javascript:;">...</a></li>
            <li class="paginItem"><a href="javascript:;">10</a></li>
            <li class="paginItem"><a href="javascript:;"><span class="pagenxt"></span></a></li>
        </ul>
    </div>

    <div class="tip">
        <div class="tiptop"><span>提示信息</span>
            <a></a>
        </div>

        <div class="tipinfo">
            <span><img src="/images/ticon.png" /></span>
            <div class="tipright">
                <p>是否确认对信息的修改 ？</p>
                <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
            </div>
        </div>

        <div class="tipbtn">
            <input name="" type="button" class="sure" value="确定" />&nbsp;
            <input name="" type="button" class="cancel" value="取消" />
        </div>

    </div>

</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>

</body>

</html>

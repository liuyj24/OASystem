<%--
  Created by IntelliJ IDEA.
  User: liuyj
  Date: 2018/10/14
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>审核报销单</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript">

        $(function () {
            //页面加载完成时给按键绑定事件
            $("#btn").click(function () {
               //获取表单项的值
                var expid = $("#expid").val();//报销单编号
                var auditDesc = $("#auditDesc").val();//审核意见
                var resultArr = $("input[name=result]");//获取审核结果
                var result = "";
                for(var i = 0; i < resultArr.length; i++){
                    var flag = $(resultArr[i]).attr("checked");//获取被选中的审核结果
                    if(flag){
                        result = $(resultArr[i]).val();
                        break;
                    }
                }
                //使用ajax向服务器发送请求
                $.ajax({
                    url:"/ExpenseServlet?method=audit",
                    type:"POST",
                    data:{expid:expid, result:result, audiDesc:auditDesc},
                    dataType:"text",
                    success:function (data) {//data为服务器返回的数据
                        alert(data);
                        if(data = "success"){
                            //刷新父窗口
                            window.opener.location.reload();
                            //关闭当前窗口
                            window.close();
                        }else{
                            alert("审核失败");
                        }
                    }
                });
            });
        });
    </script>
</head>

<body>

<div class="formtitle"><span>审核报销单</span></div>
<form action="#" >
    <ul class="forminfo">
        <li>
            <label>报销单编号</label>
            <input name="expid" id="expid" type="text" class="dfinput" value="${param.expid}" readonly="readonly" />
        </li>
        <li>
            <label>审核结果</label>
            <input name="result" type="radio" value="通过"/>通过
            <input name="result" type="radio" value="拒绝"/>拒绝
            <input name="result" type="radio" value="打回"/>打回
        </li>
        <li>
            <label>审核意见</label>
            <input name="" id="auditDesc" type="text" class="dfinput" />
        </li>
        <li>
            <label>&nbsp;</label>
            <input name="" id="btn" type="button" class="btn" value="确认保存" />
        </li>
    </ul>
</form>
</body>

</html>

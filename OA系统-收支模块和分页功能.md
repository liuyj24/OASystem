# OA系统-收支模块和分页功能

### 收支管理-Echarts入门
1. 到Echarts的官网下载js文件, 现在下载3版本可能稳定点, 并放复制到项目中
2. 引入Echarts, 和引入jQuery差不多, 两个都一起引入, 
3. 然后按照官网中给出的实例, 自己选择合适的图表, 并按照官网的步骤进行代码组装
4. 收入和统计功能自己完成, 老师已经给出了收入和支出的一些测试数据, 我们要把他们显示到图表中, 在incomestatics.jsp中使用ajax向服务器发送请求获取填充:url填写servlet的getBarData方法


### 使用Echarts柱状图显示收入统计信息-业务层和dao层
1. 在getBarDate()方法中, 我们调用业务层获取jsonStr, 最后把jsonStr响应回去
2. 老师分组求和的sql语句  
`SELECT * FROM ictype, SUM(amount) FROM income GROUP BY ictype`
3. 拼接图标所需要的json字符串是难点, 使用StringBuilder拼接, 并使用for循环进行拼接, 有点难度

### 收支管理-财务添加支出
1. 为了完成在财务进行审核后, 增加一条支出信息, 创建支出的实体类payment, 注意:pmId支付编号序列自增, payEmpId支付人编号(财务), expEmpId报销人员工编号, 最后加上支付人,报销人两个emp和报销单exp属性
2. 写好paymentDao中的save方法, 保存支付信息的方法(拼接参数的时候注意:时间需要年月日时分秒格式, 使用时间戳`new TimeStamp (Date.getTime())`)
3. 完善财务审核报销单的代码:1)修改报销单时把状态设置为已打款, 并把下一级的审核人设置为null 2)添加支出记录, 在配置参数的时候一定要和servlet结合着配置,减少出错的情况
 

### 收支管理-查看支出(未完成, 主要写sql多表查询语句)
1. 先确定要查哪些表, 目前确定至少是三张表payment, expenseitem, expense, 为了显示人员的真实姓名,加多一张员工表employee
2. 查找的部分写好后, 在写条件部分, 注意写日期的条件部分, 我们希望查找两个日期中间的时间, mysql数据库中日期比较
```
按照视图层查找支出的sql语句
SELECT item.*, emp2.realname, pm.paytime
FROM payment pm
JOIN expense EXP 
ON exp.expid=pm.expid
JOIN expenseitem item
ON exp.expid = item.expid
JOIN employee emp1
ON emp1.empid=pm.empid
JOIN employee emp2
ON emp2.empid=pm.payempid
WHERE 1=1 
AND pm.payempid = 'xiaoji'
AND item.type=1
AND DATE_FORMAT(pm.paytime, '%Y-%m-%d') >= '2018-10-15'
AND DATE_FORMAT(pm.paytime, '%Y-%m-%d') <= '2018-10-15'
```

### 收支管理-使用饼图显示支出
1. 到Echarts官网找饼图显示的实例
2. 下载饼图的显示代码, 创建paymentstatic.jsp, 在页面加载完毕后调用ajax请求, 并在回调函数中执行饼图的代码画出饼图, 记得`eval("var arr=" + data)`, 这一步是把饼图代码中arr给换掉, 换成自己的数组, 同时在servlet中给出一个同样格式的json字符串, 看能不能在jsp中正确显示, 能正确显示再去数据库中查找数据并发送到jsp
3. InOutServlet->InOutService->(getPieData)->PaymentDao
4. 查询需要的数据比较简单, 只要支出的类型和支出的金额
5. 套用查找支出的sql语句, 修改开头只查询`select item.type, sum(item.amount)`  
结尾`group by item.type`, 再删除一些冗余的代码
6. 在地址栏直接访问servlet, 成功则直接到前台访问, 饼状图中的图例的值要和查询出来的value的值对应


### 收支管理-使用饼图显示指定时间段的支出
1. 先调整前台代码, 把下拉框显示出来, 下拉框的onchange属性绑定一个方法changePie(this.value)
2. 创建一个changePie方法, 接收一个参数, 然后把原来发送ajax请求获取后台支出信息的代码复制到changePie方法中
3. 页面加载完后不再直接调用ajax代码, 而是调用changePie(0), 传入参数0, 默认参数0是不追加任何条件, 也就是能查到所有的数据
4. ajax给servlet传递的参数就是0(或其他数字), servlet中也只接收这个val值, 并且默认值是0
5. 在dao中通过几个if-else if判断val的值, 并给原sql添加上不同的条件, 但是比如说要查当前月, 当前年, 这个字符串不好写, 使用工具类获得当前月,年等信息(DateUtil.java), 然后就可以比较时间获取数据了


### 项目总结
见项目总结篇
### 分页意义和实现思路
1. 不直接在项目中讲解, 另外新起一个小的项目从0开始讲解
2. 把项目导入到idea中
3. 分页的作用:
- 数据量大,一页容不下
- 后台查询部分数据而不是全部数据
- 降低宽带使用, 提高访问速度

### 理解PageBean
1. 导入工具类PageBean
2. 分页的三个基本属性
- 每页几条记录size,      可以有默认值5
- 当前页号index,         可以有默认值1
- 记录总数totalCount,    不可以有默认值,需要查询数据库获得真实的记录数
其他的属性:一共多少页, 上一页, 下一页可以通过3个基本参数计算得出
3. PageBean中名为numbers的int类型数组存放的是当前可选择显示的页, 比如当前页码为10, 能直接按的页码就是7-8-9-10-11-12-13等


### 基本分页的后台操作
> Servlet中的步骤

1. 接收从页面传入的页码index(String)
2. 创建index(int), 默认值为1, 然后再转换, 捕捉NumberFormatException
3. 创建PageBean对象, 设置index的值
4. 调用业务层的findStu方法传入PageBean, 注意不需要给出返回值stuList, 因为查询得到的值都保存在pagebean中
5. 最后直接把PageBean保存到request域中
---
> Service中的步骤
1. 查询数据库表获得记录总数
```
对步骤1有两种查询思路
1) select * from student;//笨
2) select count(*) from student;//好
```
2. 使用记录总数计算PageBean中的其他属性(totalCount, totalPageCount, numbers)
```
//当我们执行代码
pageBean.setTotalCount(totalCount);
//的时候, 就已经把上述的三个属性赋值了,(内部代码实现)
```
3. 调用DAO层获取指定页的学生数据, 并放入pageBean的list属性中
```
/*
每页size=5条记录
第几页  起始记录号>=    结束记录号<
1       0               5
2       5               10
3       10              15
index   (index-1)*size  index*size
*/
List<Student> list = this.stuDao.findStu();
pageBean.setList(list);
//int start = (pageBean.getIndex()-1)*pageBean.getSize();
//int end = pageBean.getIndex()*pageBean.getSize();
//上面两行代码已经封装好
int start = pageBean.getStartRow();
int end = pageBean.getEndRow();
List<Student> list = this.stuDao.findStu(start, end);
pageBean.setList(list);
```
分页的时候要利用到行号, Mysql本身不支持行号动能, 需要手动实现 [参照此博客](https://blog.csdn.net/zhihang19941024/article/details/76919318)
```
//获得行号
SELECT @rowno:=@rowno+1 as rowno,r.* from t_article r,(select @rowno:=0) t
//给现有的sql增加行号
SELECT @rowno:=@rowno+1 as rowno, O.* from (old_sql) O,(select @rowno:=0) t
//利用mysql的行号进行分页
select P.* from (SELECT @rowno:=@rowno+1 as rowno, O.* from (old_sql) O,(select @rowno:=0) t) P where rowno between ? and ?
```
### 基本分页的后台操作
1. 拿到分页最下面一栏的字样, 到jsp中编写前端代码, <tr>标签中基本的是colspan="11" align="center"
2. 给首页和末页加上超链接, 访问Servlet, index不同
3. 页码序列12345...使用foreach标签,item="${pageBean.numbers}" var="num",  同时在foreach中使用两个if标签, 如果给选中的页码加上[]符号
4. 给上一页和下一页也加上超链接, 但是会有越界问题
5. 使用if标签, 如果当前页是首页, "上一页"就不可点, 下一页也是同样的道理, 尾页是pageBean.totalPageCount
6. 写下拉列表, 使用C:forEach标签, begin属性值为1, end属性值为pageBean.totalPageCount, 最后给select绑定onchange函数, 函数中跳转去访问Servlet, 为了记忆选择的页码, 也加上两端判断if标签

### 基本分页完善
1. 优化查询记录总数, 不是把所有记录都查出来封装到集合中再获得大小, 而是直接只查询数据库表中的记录数
2. MySql中使用行号查询比Orcale简单一点, 使用limit, 当然也可以使用上面提到的手动实现行号
3. 编写每页几条记录的功能, 也是使用下拉框, 给onchange属性绑定changeSize函数, 在函数中访问servlet顺便带上size参数, 把size参数保存到pageBean中, 添加记忆
4. 出现的问题:选择了每页显示几条记录后, 在切换到其他页码的时候不会带上这个size参数  
解决:给所有访问Servlet的方法都带上size参数, 顺便把changePage和changeSize方法合成同一个方法change
5. 新问题:访问servlet的路径出现了太多次, 解决:凡是访问servlet的都调用change方法并传入两个参数, 不单止解决了现有问题, 还方便了以后的带参数查询


### 带查询条件的分页
1. 给查询条件加上表单标签, 传递name和minScore参数
2. 在servlet原来的基础上中接收以上两个参数, 最后要把两个参数保存回request中, 让页面回显这两个参数
3. service层中使用方法重载(findStu接收以上两个参数), 此时service层中不再是获得记录总数, 而是获得符合条件的记录总数, 按行号查询的地方如果用orcale的话也要把两个参数传进去, Mysql要不要还不知道
4. 查询符合条件记录总数的时候, 名字使用模糊查询
5. (Orcale)按行号查询符合条件的学生信息的时候, 在最里面一层查找语句中添加条件, 后面对行号的处理都是基于一开始的查询所得的表(order by写在where语句的后面)

### 完善带条件的分页
1. 当我们按条件进行查询后, 在点击其他页码, 不会把查询条件带上, 因为我们点击页码的时候只带上了index和size参数
> 方式一
```
在change方法中, 不直接改变当前地址栏的地址了, 而是通过找到表单项, 把index和size添加到表单提交的路径中
function change(index, size){
    document.forms[0].action="...?index="+index+...
    document.forms[0].submit;
    //document.forms[0]是获得第一个表单项
}
```
> 方式二

```
在表单中添加隐藏字段index, size, 然后在js的change方法中给两个参数赋值
<input type="hidden" ...>
---
function change(index, size){
    document.forms[0].index.value = index;
    document.forms[0].size.value = size;
    document.forms[0].submit;
}
```
2. 学会谷歌浏览器的断电调试方法, F12->sources->把行号点亮即可
3. 对分页更完善的展望:
- 比如当点击删除的时候, 如果当前页面的数据大于1, 删除完毕后还要停留在当前页面
- 如果删除的时候, 当前页面只剩下一条数据, 那么删除完这条数据后, 应该跳转到前面一页








# 员工管理项目总结
> 在学习完简单的Web开发后做的小项目, 里面以巩固jsp与servlet的使用为核心, 没有使用框架(还没学), 完成度为70%, 独立完成所有后台核心业务开发.

### JSP/Servlet核心技能
- Servlet常用API和JSP内置对象, 比如:获取参数,转发,重定向
- JSP四个作用域, 结合EL和JSTL使用
- 绝对路径, 相对路径, 根路径, 目前除了写转发的时候"/"相对的是当前项目路径, 否则都是对应根目录路径 
- 转发和重定向, 避免参数重复提交或者是两个方法中要获取同样名字的参数时选择重定向, 要把request中的参数转发到其他地方则用转发
- Servlet的合并:BaseServlet(待重新了解掌握)
- JSTL/EL, 使用JSTL需要导包, 掌握基本的逻辑语句, 也就是在jsp中使用java代码

##### JSP/Servlet核心技能-补充
- 把字符串格式的日期转化为java中的Date对象, util.Date->sql.Date

### MVC模式
1. 视图层:JSP -JSTL/EL
2. 控制层:Servlet
3. 模型层:接口+实现类Impl
- 业务层:JavaBean  
- 数据访问层:DAO+JDBC  

4. 各层的功能分配:  
- 实体类,DAO层基本和数据库表对应
- 业务层, 控制层, 视图层更倾向于按照模块划分
- 事务在业务层中使用(ThreadLocal)

### ajax
- 异步访问, 局部刷新, 提高用户体验
- 控制层直接返回json字符串, 而不是转发和重定向
- 使用jQuery写ajax代码

### 数据库相关
- 数据库表设计
- 单表操作, 多表操作
- 多表sql查询语句
- PowerDesigner的使用:数据库表设计, 用例图, 序列图, 类图, 业务流程图
##### 数据库相关-补充
1. 使用c3p0数据库连接池, 更好地管理连接
2. 把多表查询的结果手动封装成一个个有关联的对象
3. mysql中可以设置主键自增, 也可以手动实现orcale中的序列

### 辅助技术 
- JUnit, 验证码
- 富文本编辑器kindEditor, 日历插件My97DatePicker
- 使用POI将数据导出为xls文档
- 使用Echarts显示图表数据
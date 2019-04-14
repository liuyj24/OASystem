package com.xxx.servlet;

import com.xxx.entity.Department;
import com.xxx.entity.Employee;
import com.xxx.entity.Position;
import com.xxx.service.DepartmentService;
import com.xxx.service.EmployeeService;
import com.xxx.service.PositionService;
import com.xxx.service.impl.DepartmentServiceImpl;
import com.xxx.service.impl.EmployeeServiceImpl;
import com.xxx.service.impl.PositionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployeeServlet extends BaseServlet {

    EmployeeService employeeService = new EmployeeServiceImpl();

    /**
     * 根据员工的参数查找员工, 可以什么参数都没有查找全部员工信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findEmp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收查询表单数据
        String empid  = request.getParameter("empid");

        String sDeptno = request.getParameter("deptno");//拿到员工的部门编号, 如果为空字符串或者没有值, 就不进行转换了
        int deptno = 0;
        if(sDeptno != null && !sDeptno.equals("")){
            deptno = Integer.parseInt(sDeptno);
        }

        String sOnDuty = request.getParameter("onDuty");//拿到员工的部门编号, 如果为空字符串或者没有值, 就不进行转换了
        int onDuty = 1;//这里可能欠完善, 目前默认是只查询在职的员工
        if(sOnDuty != null && !sOnDuty.trim().equals("")){
            onDuty = Integer.parseInt(sOnDuty);
        }

        //如果没有填写日期的话, 就不进行日期格式转换了
        String shireDate = request.getParameter("hireDate");
        Date hireDate = null;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(null != shireDate && !shireDate.trim().equals("")){
            try {
                hireDate = df.parse(shireDate);//转化的是字符串
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //封装成一个Employee对象
        Employee emp = new Employee();
        emp.setEmpid(empid);
        emp.setOnDuty(onDuty);
        emp.setHireDate(hireDate);
        //dept属性
        Department dept = new Department();
        dept.setDeptno(deptno);
        emp.setDept(dept);
        //把emp对象保存到request域中以便回显
        request.setAttribute("emp", emp);
        request.setAttribute("hireDate", shireDate);//在jsp中不能方便地直接按格式显示Date,顺便把字符串格式的Date也传过去


        //查询员工信息
        List<Employee> list = employeeService.find(emp);
        request.setAttribute("empList", list);

        //查询所有部门信息
        DepartmentServiceImpl ds = new DepartmentServiceImpl();
        List<Department> deptList = ds.findAll();
        request.setAttribute("deptList", deptList);

        //转发到响应的页面
        request.getRequestDispatcher("/system/empList.jsp").forward(request, response);
    }

    //准备作废的findAll方法
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查询员工信息
        List<Employee> list = employeeService.findAll();
        request.setAttribute("empList", list);

        //查询所有部门信息
        DepartmentServiceImpl ds = new DepartmentServiceImpl();
        List<Department> deptList = ds.findAll();
        request.setAttribute("deptList", deptList);

        //转发到响应的页面
        request.getRequestDispatcher("/system/empList.jsp").forward(request, response);
    }

    /**
     * 添加员工前的准备操作
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取部门列表
        DepartmentServiceImpl ds = new DepartmentServiceImpl();
        List<Department> deptList = ds.findAll();
        request.setAttribute("deptList", deptList);

        //获取岗位列表
        PositionService ps = new PositionServiceImpl();
        List<Position> posList = ps.findAll();
        request.setAttribute("posList", posList);

        //获取领导列表
        List<Employee> mrgList = employeeService.findByType(2);//按类型查找职员, 1为普通员工, 2为管理层
        request.setAttribute("mrgList", mrgList);

        //把结果转发到empAdd.jsp
        request.getRequestDispatcher("/system/empAdd.jsp").forward(request, response);
    }

    /**
     * 添加员工
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得视图层数据
        String empid = request.getParameter("empid");
        String password = "123456";

        //对象类型的数据
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        Department dept = new Department();
        dept.setDeptno(deptno);
        int posid = Integer.parseInt(request.getParameter("posid"));
        Position pos = new Position();
        pos.setPosid(posid);
        String mgrid = request.getParameter("mgrid");
        Employee mgr = new Employee();
        mgr.setEmpid(mgrid);
        String realname = request.getParameter("realname");
        String sex = request.getParameter("sex");

        //日期类型的数据
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        Date hiredate = null;
        Date leavedate = null;
        String sbirthday = request.getParameter("birthday");
        try {
            birthday = df.parse(sbirthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String shiredate = request.getParameter("hiredate");
        try {
            hiredate = df.parse(shiredate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sleavedate = request.getParameter("leavedate");
        try {
            leavedate = df.parse(sleavedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int onDuty = Integer.parseInt(request.getParameter("onDuty"));
        int empType = Integer.parseInt(request.getParameter("empType"));
        String phone = request.getParameter("phone");
        String qq = request.getParameter("qq");
        String emercContactPerson = request.getParameter("emercContactPerson");
        String idcard = request.getParameter("idcard");

        Employee emp = new Employee(empid, password, dept, pos, mgr, realname, sex, birthday, hiredate,
                leavedate, onDuty, empType, phone, qq, emercContactPerson, idcard);

        //调用业务层完成操作
        int flag = employeeService.add(emp);

        //转发到响应得页面
        if(flag > 0){
            response.sendRedirect("/EmployeeServlet?method=findEmp");
        }else{
            request.getRequestDispatcher("/system/empAdd.jsp").forward(request, response);
        }
    }

    /**
     * 删除员工
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String empid = request.getParameter("empid");
        //调用service层完成删除
        int flag = employeeService.delete(empid);
        //最后重定向到findEmp, 不用转发, 避免员工的empid又被findEmp方法获取到产生歧义
        response.sendRedirect("/EmployeeServlet?method=findEmp");
    }

    /**
     * 更新员工前的准备
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获得员工的empid
        String empid = request.getParameter("empid");

        //调用service层, 通过empid查询数据库获得emp的数据
        Employee emp = employeeService.findById(empid);
        request.setAttribute("emp", emp);//把获得的对象封装到reques域中

        //获得部门信息
        DepartmentServiceImpl ds = new DepartmentServiceImpl();
        List<Department> deptList = ds.findAll();
        request.setAttribute("deptList", deptList);

        //获得岗位信息
        PositionServiceImpl ps = new PositionServiceImpl();
        List<Position> posList = ps.findAll();
        request.setAttribute("posList", posList);

        //获取领导列表
        List<Employee> mrgList = employeeService.findByType(2);//按类型查找职员, 1为普通员工, 2为管理层
        request.setAttribute("mrgList", mrgList);

        //把获得的信息转发到empUpdate.jsp显示
        request.getRequestDispatcher("/system/empUpdate.jsp").forward(request, response);
    }

    /**
     * 更改员工信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获得视图层数据
        String empid = request.getParameter("empid");
        String password = "123456";

        //对象类型的数据
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        Department dept = new Department();
        dept.setDeptno(deptno);
        int posid = Integer.parseInt(request.getParameter("posid"));
        Position pos = new Position();
        pos.setPosid(posid);
        String mgrid = request.getParameter("mgrid");
        Employee mgr = new Employee();
        mgr.setEmpid(mgrid);
        String realname = request.getParameter("realname");
        String sex = request.getParameter("sex");

        //日期类型的数据
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        Date hiredate = null;
        Date leavedate = null;
        String sbirthday = request.getParameter("birthday");
        try {
            birthday = df.parse(sbirthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String shiredate = request.getParameter("hiredate");
        try {
            hiredate = df.parse(shiredate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sleavedate = request.getParameter("leavedate");
        try {
            leavedate = df.parse(sleavedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int onDuty = Integer.parseInt(request.getParameter("onDuty"));
        int empType = Integer.parseInt(request.getParameter("empType"));
        String phone = request.getParameter("phone");
        String qq = request.getParameter("qq");
        String emercContactPerson = request.getParameter("emercContactPerson");
        String idcard = request.getParameter("idcard");

        Employee emp = new Employee(empid, password, dept, pos, mgr, realname, sex, birthday, hiredate,
                leavedate, onDuty, empType, phone, qq, emercContactPerson, idcard);

        //调用业务层完成操作
        int flag = employeeService.update(emp);

        //转发到响应得页面
        if(flag > 0){
            response.sendRedirect("/EmployeeServlet?method=findEmp");
        }else{
            request.getRequestDispatcher("/system/empAdd.jsp").forward(request, response);
        }
    }

    /**
     * 查看员工基本信息前准备员工信息的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toShow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获得员工的empid
        String empid = request.getParameter("empid");

        //调用service层, 通过empid查询数据库获得emp的数据
        Employee emp = employeeService.findById(empid);
        request.setAttribute("emp", emp);//把获得的对象封装到reques域中

        //把获得的信息转发到empUpdate.jsp显示
        request.getRequestDispatcher("/system/empInfo.jsp").forward(request, response);
    }

    /**
     * 员工登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获得员工的empid和密码
        String empid = request.getParameter("empid");
        String password = request.getParameter("password");

//        //获得验证码并比较
//        String verfyCode = request.getParameter("verifyCode");
//        String randStr = (String) request.getSession().getAttribute("randStr");
//        if(null == verfyCode || !verfyCode.equals(randStr)){
//            request.setAttribute("msg", "对不起,验证码错误");
//            request.getRequestDispatcher("/login.jsp").forward(request, response);
//            return;
//        }

        //调用service层, 完成登陆验证
        Employee emp = employeeService.login(empid, password);

        //根据结果跳转到不同的页面显示
        if(null != emp){//登陆成功
            request.getSession().setAttribute("emp", emp);
            request.getRequestDispatcher("/main.jsp").forward(request, response);
        }else{
            request.setAttribute("msg", "登陆失败, 用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    /**
     * 退出登陆的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //注销当前的session
        request.getSession().invalidate();

        //重定向到login.jsp
        response.sendRedirect("/login.jsp");
    }
}

package com.xxx.servlet;

import com.xxx.entity.Department;
import com.xxx.service.impl.DepartmentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DepartmentServlet extends BaseServlet {
    DepartmentServiceImpl departmentService = new DepartmentServiceImpl();

    /**
     * 添加部门
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收视图层的表单数据,并封装表单数据
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        String deptname = request.getParameter("deptname");
        String location = request.getParameter("location");
        Department dept = new Department(deptno, deptname, location);

        //调用业务层完成添加操作
        int success = departmentService.add(dept);

        //根据结果跳转到不同的页面
        if(success > 0){
            response.sendRedirect(request.getContextPath() + "/DepartmentServlet?method=findAll");
        }else{
            request.setAttribute("msg", "添加失败");
            request.getRequestDispatcher("/system/deptAdd.jsp").forward(request, response);
        }
    }

    /**
     * 通过部门id查找部门
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取视图层的数据
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        //调用业务层完成添加操作
        Department dept = departmentService.findById(deptno);
        request.setAttribute("dept", dept);

        //根据结果跳转到不同的页面
        request.getRequestDispatcher("/system/deptUpdate.jsp").forward(request, response);
    }

    /**
     * 查找所有部门
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用业务层完成添加操作
        List<Department> list = departmentService.findAll();
        request.setAttribute("deptList", list);

        //根据结果跳转到不同的页面
        request.getRequestDispatcher("/system/deptList.jsp").forward(request, response);
    }

    /**
     * 更新部门信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取视图层的数据
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        String deptname = request.getParameter("deptname");
        String location = request.getParameter("location");
        Department dept = new Department(deptno, deptname, location);

        //调用业务层完成添加操作
        int flag = departmentService.update(dept);

        //根据结果跳转到不同的页面
        if(flag > 0){
            response.sendRedirect(request.getContextPath() + "/DepartmentServlet?method=findAll");
        }else{
            request.setAttribute("msg", "添加失败");
            request.setAttribute("dept", dept);
            request.getRequestDispatcher("/system/deptUpdate.jsp").forward(request, response);
        }
    }

    /**
     * 删除部门信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取视图层参数
        int deptno = Integer.parseInt(request.getParameter("deptno"));

        //调用业务层完成添加操作
        int flag = departmentService.delete(deptno);

        //根据结果跳转到不同的页面
        request.getRequestDispatcher("/DepartmentServlet?method=findAll").forward(request, response);
    }
}

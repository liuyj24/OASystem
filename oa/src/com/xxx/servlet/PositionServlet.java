package com.xxx.servlet;

import com.xxx.entity.Position;
import com.xxx.service.PositionService;
import com.xxx.service.impl.PositionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PositionServlet extends BaseServlet{
    PositionServiceImpl positionService = new PositionServiceImpl();

    /**
     * 添加员工岗位的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得视图层的数据
        int posid = Integer.parseInt(request.getParameter("posid"));
        String pname = request.getParameter("pname");
        String pdesc = request.getParameter("pdesc");
        Position pos = new Position(posid, pname, pdesc);

        //调用业务层完成操作
        int flag = positionService.add(pos);

        //转发到其他地方
        if(flag > 0){
            //添加成功则重定向到positionList.jsp
            response.sendRedirect("/PositionServlet?method=findAll");
        }else {
            //失败则重定向回positionAdd.jsp,并显示错误信息
            request.getRequestDispatcher("/position/positionAdd.jsp");
        }
    }

    /**
     * 查询所有员工岗位
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //调用业务层完成操作
        List<Position> list = positionService.findAll();

        //转发到其他地方
        request.setAttribute("list", list);
        request.getRequestDispatcher("/position/positionList.jsp").forward(request, response);
    }

    /**
     * 删除某个员工岗位
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取视图层的数据
        int posid = Integer.parseInt(request.getParameter("posid"));

        //调用业务层完成操作
        int flag = positionService.delete(posid);

        //转发到其他地方
        response.sendRedirect("/PositionServlet?method=findAll");
    }

    /**
     * 通过岗位的id查找岗位信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取视图层的数据
        int posid = Integer.parseInt(request.getParameter("posid"));

        //调用业务层完成操作
        Position pos = positionService.findById(posid);

        //转发到其他地方
        request.setAttribute("pos", pos);
        request.getRequestDispatcher("/position/positionUpdate.jsp").forward(request, response);
    }

    /**
     * 更新岗位信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取视图层的数据
        int posid = Integer.parseInt(request.getParameter("posid"));
        String pname = request.getParameter("pname");
        String pdesc = request.getParameter("pdesc");
        Position pos = new Position(posid, pname, pdesc);

        //调用业务层完成操作
        int flag = positionService.update(pos);

        //转发到其他地方
        if(flag > 0){
            //添加成功则重定向到positionList.jsp
            response.sendRedirect("/PositionServlet?method=findAll");
        }else {
            //失败则重定向回positionAdd.jsp,并显示错误信息
            request.getRequestDispatcher("/position/positionUpdate.jsp");
        }
    }

}

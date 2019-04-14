package com.xxx.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xxx.entity.Department;
import com.xxx.entity.Duty;
import com.xxx.entity.Employee;
import com.xxx.service.DutyService;
import com.xxx.service.impl.DepartmentServiceImpl;
import com.xxx.service.impl.DutyServiceImpl;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

public class DutyServlet extends BaseServlet {
    DutyService dutyService = new DutyServiceImpl();
    /**
     * 考勤登陆的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void signin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //从session获得当前签到对象emp, 并获得他的empid
        Employee emp = (Employee) request.getSession().getAttribute("emp");
        String empid = emp.getEmpid();

        //调用业务层操作完成签到, 0失败, 1成功, 2已经签到
        int n = dutyService.signin(empid);

        //不需要页面跳转, 直接返回内容
        response.getWriter().println(n);
    }

    /**
     * 签退
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void signout (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //从session获得当前签到对象emp, 并获得他的empid
        Employee emp = (Employee) request.getSession().getAttribute("emp");
        String empid = emp.getEmpid();

        //调用业务层操作完成签到, 0失败, 1成功, 2已经签到
        int n = dutyService.signout(empid);

        //不需要页面跳转, 直接返回内容
        response.setContentType("text/html;charset=utf-8");//设置响应内容的格式和字符集
        PrintWriter out = response.getWriter();
        if(0 == n){
            out.println("签退失败");
        }else if(1 == n){
            out.println("签退成功");
        }else{
            out.println("尚未签到");
        }
    }

    /**
     * 查找部门信息并显示在dutyList的部门选择框中
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAllDept(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获得所有的部门信息
        DepartmentServiceImpl ds = new DepartmentServiceImpl();
        List<Department> deptList = ds.findAll();

        //把部门信息封装成json字符串返回给dutyList.jsp
        response.setContentType("text/html;charset=utf-8");//要写回中文, 设置好
        response.getWriter().println(new Gson().toJson(deptList));
    }

    /**
     * 根据员工的条件查询签到信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findDuty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获得三个查询条件
        String empid = request.getParameter("empid");
        String sdeptno = request.getParameter("deptno");
        String signTimeStr = request.getParameter("signTime");

        //防止获得空的参数
        int deptno = 0;
        try{
            deptno = Integer.parseInt(sdeptno);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        Date signinTime = null;
        try{
            signinTime = Date.valueOf(signTimeStr);
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        //调用业务层完成查询操作
        List<Duty> list = dutyService.findDuty(empid, deptno, signinTime);

        //返回json字符串
        response.setContentType("text/html;charset=utf-8");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        response.getWriter().println(gson.toJson(list));
    }

    /**
     * 把员工的考勤信息导出为xls文件
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exportXLS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获得三个查询条件
        String empid = request.getParameter("empid");
        String sdeptno = request.getParameter("deptno");
        String signTimeStr = request.getParameter("signTime");

        //防止获得空的参数
        int deptno = 0;
        try{
            deptno = Integer.parseInt(sdeptno);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        Date signinTime = null;
        try{
            signinTime = Date.valueOf(signTimeStr);
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        //调用业务层完成查询操作
        List<Duty> list = dutyService.findDuty(empid, deptno, signinTime);

        //保存xls文件到客户端
        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("考勤表一");
        //第一行是标题栏, 把
        CellRangeAddress region = new CellRangeAddress(
                0, // first row, 第0行
                0, // last row, 第0行
                0, // first column, 第0列
                2 // last column, 第2列, 第0-2列合并
        );
        sheet.addMergedRegion(region);

        HSSFRow hssfRow = sheet.createRow(0);
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("考勤列表");

        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headCell.setCellStyle(cellStyle);//设置为居中

        // 添加表头行
        hssfRow = sheet.createRow(1);
        // 添加表头内容
        headCell = hssfRow.createCell(0);
        headCell.setCellValue("用户名");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(1);
        headCell.setCellValue("真实姓名");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(2);
        headCell.setCellValue("所属部门");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(3);
        headCell.setCellValue("出勤日期");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(4);
        headCell.setCellValue("签到时间");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(5);
        headCell.setCellValue("签退时间");
        headCell.setCellStyle(cellStyle);

        // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
            hssfRow = sheet.createRow((int) i + 2);
            Duty duty = list.get(i);

            // 创建单元格，并设置值
            HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(duty.getEmpid());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(1);
            cell.setCellValue(duty.getEmp().getRealname());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(2);
            cell.setCellValue(duty.getEmp().getDept().getDeptname());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(3);
            cell.setCellValue(duty.getDtDate());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(4);
            cell.setCellValue(duty.getSigninTime());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(5);
            cell.setCellValue(duty.getSignoutTime());
            cell.setCellStyle(cellStyle);
        }
        // 保存Excel文件
        try {
            //设置相应的内容为xls文件
            response.setContentType("application/vnd.ms-excel");
            //把xls文件设置在响应头中, 设置为以附件形式下载, 文件名叫duty.xls
            response.setHeader("Content-disposition", "attachment;filename=duty.xls");
            //获得写到客户端的输出流
            OutputStream outputStream = response.getOutputStream();//获得相应至客户端的输出流
            //写到客户端
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

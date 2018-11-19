package com.xxx.servlet;

import com.xxx.entity.Auditing;
import com.xxx.entity.Employee;
import com.xxx.entity.Expense;
import com.xxx.entity.ExpenseItem;
import com.xxx.service.ExpenseService;
import com.xxx.service.impl.ExpenseServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * 添加报销单
 */
public class ExpenseServlet extends BaseServlet {
    ExpenseService expenseService = new ExpenseServiceImpl();

    /**
     * 查看待审核报销单前的准备工作
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toAudit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //从session中获取empid
        Employee emp = (Employee) request.getSession().getAttribute("emp");
        String empid = emp.getEmpid();

        //根据empid查找对应的expense报销单
        List<Expense> list = expenseService.findByAuditId(empid);
        request.setAttribute("list", list);

        //把结果返回到myAudit页面
        request.getRequestDispatcher("/expense/toAudit.jsp").forward(request, response);
    }

    /**
     * 添加报销单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        接收视图层的数据
        1. 先接收明细信息, 封装好再添加进报销单对象中
        2. 封装报销单对象
         */
        String[] typeArr = request.getParameterValues("type");//明细的类型
        String[] amountArr = request.getParameterValues("amount");//明细的花费
        String[] itemDescArr = request.getParameterValues("itemDesc");//明细的描述
        double totalAmount = 0;
        List<ExpenseItem> itemList = new ArrayList<>();//封装明细的集合
        for(int i = 0; i < typeArr.length; i++){
            Double itemAmount = null;//防止没有传入明细的花费, 出现NumberFormatException
            try {
                itemAmount = Double.parseDouble(amountArr[i]);
            }catch (RuntimeException e){
                e.printStackTrace();
            }
            ExpenseItem ei = new ExpenseItem(typeArr[i], itemAmount, itemDescArr[i]);
            totalAmount += itemAmount;
            itemList.add(ei);//把封装好的明细放进集合中
        }
        //获取报销单的参数
        String empid = request.getParameter("empid");
        Date expTime = new Date();//当前时间就是创建报销单的时间
        String expDesc = request.getParameter("expDesc");
        String nextAuditorId = request.getParameter("nextAuditorId");
        Expense expense = new Expense(empid, totalAmount, expTime, expDesc, nextAuditorId);
        expense.setItemList(itemList);//把明细集合存放进去;
        //补充
        expense.setLastResult("新创建的");
        expense.setStatus("0");
        //调用业务层保存报销单信息
        try{
            expenseService.save(expense);
            //成功则跳转到myExpense.jsp
            request.getRequestDispatcher("/myExpense.html").forward(request, response);
        }catch (RuntimeException e){
            e.printStackTrace();
            //转发回expenseAdd.jsp
            request.setAttribute("msg", "添加失败!");
            request.getRequestDispatcher("/expense/expenseAdd.jsp").forward(request, response);
        }

    }

    /**
     * 审核报销单(业务层复杂)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void audit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获取审核表单的值
        String sexpid = request.getParameter("expid");
        int expid = Integer.parseInt(sexpid);
        String result = request.getParameter("result");
        String auditDesc = request.getParameter("audiDesc");
        //把参数封装到一个audit对象中
        Employee emp = (Employee) request.getSession().getAttribute("emp");
        String empid = emp.getEmpid();//获得当前登陆对象的empid
        Date time = new Date();//先不设置日期的格式了,到了dao层还要转换为sql.Date格式
        Auditing audit = new Auditing(expid, empid, result, auditDesc, time);
        //把审核人的信息也传进去
        audit.setEmp(emp);
        //把报销单的信息也传进去
        Expense exp = expenseService.findById(expid);
        audit.setExp(exp);

        //调用业务层完成审核操作
        PrintWriter out = response.getWriter();
        try {
            expenseService.audit(audit);
            //成功
            out.println("success");

        }catch (RuntimeException e){
            e.printStackTrace();
            //失败
            out.println("fail");
        }
    }
}

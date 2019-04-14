package com.xxx.entity;

import java.util.Date;
import java.util.List;

/**
 * 报销单类
 */
public class Expense {
    private int expid;//报销单编号, 设置为序列自增
    private String empid;//报销人用户名
    private double totalAmount;//报销单总金额
    private Date expTime;//报销时间
    private String expDesc;//报销总备注信息
    private String nextAuditor;//下一个审核人的id
    private String lastResult;//最新的审核结果
    private String status;//报销单的状态:0新创建, 1审核中, 2审核结束通过, 3审核拒绝, 4审核打回, 5已打款
    private Employee nextAuditorEmp;//下一个审核人
    private Employee emp;//报销人
    private List<ExpenseItem> itemList;//报销明细的集合

    public Expense() {
    }

    public Expense(int expid, String empid, double totalAmount, Date expTime, String expDesc, String nextAuditor, String lastResult, String status) {
        this.expid = expid;
        this.empid = empid;
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.nextAuditor = nextAuditor;
        this.lastResult = lastResult;
        this.status = status;
    }

    public Expense(String empid, double totalAmount, Date expTime, String expDesc, String nextAuditor) {
        this.empid = empid;
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.nextAuditor = nextAuditor;
    }

    public Expense(int expid, String empid, double totalAmount, Date expTime, String expDesc, String nextAuditor, String lastResult, String status, Employee nextAuditorEmp, Employee emp, List<ExpenseItem> itemList) {
        this.expid = expid;
        this.empid = empid;
        this.totalAmount = totalAmount;
        this.expTime = expTime;
        this.expDesc = expDesc;
        this.nextAuditor = nextAuditor;
        this.lastResult = lastResult;
        this.status = status;
        this.nextAuditorEmp = nextAuditorEmp;
        this.emp = emp;
        this.itemList = itemList;
    }

    public int getExpid() {
        return expid;
    }

    public void setExpid(int expid) {
        this.expid = expid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getExpTime() {
        return expTime;
    }

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    public String getExpDesc() {
        return expDesc;
    }

    public void setExpDesc(String expDesc) {
        this.expDesc = expDesc;
    }

    public String getNextAuditor() {
        return nextAuditor;
    }

    public void setNextAuditor(String nextAuditor) {
        this.nextAuditor = nextAuditor;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getNextAuditorEmp() {
        return nextAuditorEmp;
    }

    public void setNextAuditorEmp(Employee nextAuditorEmp) {
        this.nextAuditorEmp = nextAuditorEmp;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public List<ExpenseItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ExpenseItem> itemList) {
        this.itemList = itemList;
    }
}
package com.xxx.entity;

import java.util.Date;

public class Payment {
    private int pid;//支出的编号
    private String payEmpid;//支出人(财务本人)的员工id
    private float amount;//支出的金额
    private Date payTime;//支出的时间
    private int expid;//报销单的编号
    private String empid;//报销人的员工id

    private Employee payEmp;//支出人
    private Employee expEmp;//报销人
    private Expense exp;//报销单

    public Payment() {
    }

    public Payment(int pid, String payEmpid, float amount, Date payTime, int expid, String empid, Employee payEmp, Employee expEmp, Expense exp) {
        this.pid = pid;
        this.payEmpid = payEmpid;
        this.amount = amount;
        this.payTime = payTime;
        this.expid = expid;
        this.empid = empid;
        this.payEmp = payEmp;
        this.expEmp = expEmp;
        this.exp = exp;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPayEmpid() {
        return payEmpid;
    }

    public void setPayEmpid(String payEmpid) {
        this.payEmpid = payEmpid;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public Employee getPayEmp() {
        return payEmp;
    }

    public void setPayEmp(Employee payEmp) {
        this.payEmp = payEmp;
    }

    public Employee getExpEmp() {
        return expEmp;
    }

    public void setExpEmp(Employee expEmp) {
        this.expEmp = expEmp;
    }

    public Expense getExp() {
        return exp;
    }

    public void setExp(Expense exp) {
        this.exp = exp;
    }
}

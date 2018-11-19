package com.xxx.entity;

import java.util.Date;

public class Auditing {
    private int auditid;//审核的编号
    private int expid;//审核的报销单的编号
    private String empid;//审核人的id
    private String result;//审核的结果
    private String auditDesc;//审核结果的说明
    private Date time;//审核的时间
    private Expense exp;//审核的报销单
    private Employee emp;//审核的人

    public Auditing(int expid, String empid, String result, String auditDesc, Date time) {
        this.expid = expid;
        this.empid = empid;
        this.result = result;
        this.auditDesc = auditDesc;
        this.time = time;
    }

    public Auditing(int auditid, int expid, String empid, String result, String auditDesc, Date time, Expense exp, Employee emp) {
        this.auditid = auditid;
        this.expid = expid;
        this.empid = empid;
        this.result = result;
        this.auditDesc = auditDesc;
        this.time = time;
        this.exp = exp;
        this.emp = emp;
    }

    public int getAuditid() {
        return auditid;
    }

    public void setAuditid(int auditid) {
        this.auditid = auditid;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Expense getExp() {
        return exp;
    }

    public void setExp(Expense exp) {
        this.exp = exp;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }
}

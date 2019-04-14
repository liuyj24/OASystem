package com.xxx.entity;

import java.util.Date;

/**
 * 员工考勤类
 */
public class Duty {
    private int dtID;
    private String empid;
    private Date dtDate;
    private String signinTime;
    private String signoutTime;
    private Employee emp;

    public Duty(int dtID, String empid, Date dtDate, String signinTime, String signoutTime, Employee emp) {
        this.dtID = dtID;
        this.empid = empid;
        this.dtDate = dtDate;
        this.signinTime = signinTime;
        this.signoutTime = signoutTime;
        this.emp = emp;
    }

    public Duty() {
    }

    public int getDtID() {
        return dtID;
    }

    public void setDtID(int dtID) {
        this.dtID = dtID;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public Date getDtDate() {
        return dtDate;
    }

    public void setDtDate(Date dtDate) {
        this.dtDate = dtDate;
    }

    public String getSigninTime() {
        return signinTime;
    }

    public void setSigninTime(String signinTime) {
        this.signinTime = signinTime;
    }

    public String getSignoutTime() {
        return signoutTime;
    }

    public void setSignoutTime(String signoutTime) {
        this.signoutTime = signoutTime;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }
}

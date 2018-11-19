package com.xxx.entity;

import java.util.Date;

public class Employee {
    private String empid;//员工编号
    private String password;//员工密码
    private Department dept;//员工部门
    private Position pos;//员工的岗位
    private Employee mgr;//员工的上级
    private String realname;//员工的真实姓名
    private String sex;//性别
    private Date birthday;//生日
    private Date hireDate;//雇佣日期
    private Date leaveDate;//离职日期
    private int onDuty;//是否在职:0-离职,1-在职
    private int emptype;//员工类型:1.普通员工,2.管理人员:含经理,总监,总裁等 3.管理员
    private String phone;//联系电话
    private String qq;//qq号码
    private String emerContactperson;//紧急联系人信息
    private String idcard;//身份证号

    public Employee() {
    }

    public Employee(String empid, String password, Department dept,
                    Position pos, Employee mgr, String realname,
                    String sex, Date birthday, Date hireDate,
                    Date leaveDate, int onDuty, int emptype,
                    String phone, String qq,
                    String emerContactperson, String idcard) {
        this.empid = empid;
        this.password = password;
        this.dept = dept;
        this.pos = pos;
        this.mgr = mgr;
        this.realname = realname;
        this.sex = sex;
        this.birthday = birthday;
        this.hireDate = hireDate;
        this.leaveDate = leaveDate;
        this.onDuty = onDuty;
        this.emptype = emptype;
        this.phone = phone;
        this.qq = qq;
        this.emerContactperson = emerContactperson;
        this.idcard = idcard;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Employee getMgr() {
        return mgr;
    }

    public void setMgr(Employee mgr) {
        this.mgr = mgr;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public int getOnDuty() {
        return onDuty;
    }

    public void setOnDuty(int onDuty) {
        this.onDuty = onDuty;
    }

    public int getEmptype() {
        return emptype;
    }

    public void setEmptype(int emptype) {
        this.emptype = emptype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmerContactperson() {
        return emerContactperson;
    }

    public void setEmerContactperson(String emerContactperson) {
        this.emerContactperson = emerContactperson;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empid='" + empid + '\'' +
                ", password='" + password + '\'' +
                ", dept=" + dept +
                ", pos=" + pos +
                ", mgr=" + mgr +
                ", realname='" + realname + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", hireDate=" + hireDate +
                ", leaveDate=" + leaveDate +
                ", onDuty=" + onDuty +
                ", emptype=" + emptype +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", emerContactperson='" + emerContactperson + '\'' +
                ", idcard='" + idcard + '\'' +
                '}';
    }
}

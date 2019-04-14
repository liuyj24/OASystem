package com.xxx.service.impl;

import com.xxx.dao.DutyDao;
import com.xxx.dao.impl.DutyDaoImpl;
import com.xxx.entity.Duty;
import com.xxx.service.DutyService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DutyServiceImpl implements DutyService {
    DutyDao dutyDao = new DutyDaoImpl();

    @Override
    public int signin(String empid) {
        //判断用户是否已经签到, 需要两个参数empid和today(今天的日期, 每天能签一次到)
        Date date = new Date();//拿到现在的时间
        java.sql.Date today = new java.sql.Date(date.getTime());//利用getTime()的共性创建用于数据库的Date
        boolean flag = dutyDao.find(empid, today);//查看今天是否已经签到

        //根据用户是否签到进行操作
        if(flag){
            return 2;
        }else{
            //没有签到,完成签到
            Duty duty = new Duty();
            duty.setEmpid(empid);//员工id
            duty.setDtDate(today);//今天的日期
            //设置签到的时分秒
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            String signintime = df.format(today);
            duty.setSigninTime(signintime);

            //调用dao层进行签到
            return dutyDao.save(duty);//成功会返回1,失败返回0
        }
    }

    @Override
    public int signout(String empid) {
        //判断用户是否已经签到, 需要两个参数empid和today(今天的日期, 每天能签一次到)
        Date date = new Date();//拿到现在的时间
        java.sql.Date today = new java.sql.Date(date.getTime());//利用getTime()的共性创建用于数据库的Date
        boolean flag = dutyDao.find(empid, today);//查看今天是否已经签到

        //根据用户是否签到进行操作
        if(!flag){
            return 2;//尚未签到
        }else{
            //已经签到, 完成签退
            Duty duty = new Duty();
            duty.setEmpid(empid);//员工id
            duty.setDtDate(today);//今天的日期

            //设置签退的时分秒
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            String signOutTime = df.format(today);
            duty.setSignoutTime(signOutTime);

            //调用dao层进行签退
            return dutyDao.update(duty);//成功会返回1,失败返回0
        }
    }

    @Override
    public List<Duty> findDuty(String empid, int deptno, java.sql.Date signinTime) {
        return dutyDao.findDuty(empid, deptno, signinTime);
    }
}

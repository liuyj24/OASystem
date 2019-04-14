package com.xxx.service;

import com.xxx.entity.Duty;

import java.sql.Date;
import java.util.List;

public interface DutyService {
    int signin(String empid);

    int signout(String empid);

    List<Duty> findDuty(String empid, int deptno, Date signinTime);
}

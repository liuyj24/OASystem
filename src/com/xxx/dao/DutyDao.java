package com.xxx.dao;

import com.xxx.entity.Duty;

import java.sql.Date;
import java.util.List;

public interface DutyDao {
    boolean find(String empid, Date today);

    int save(Duty duty);

    int update(Duty duty);

    List<Duty> findDuty(String empid, int deptno, Date signinTime);
}

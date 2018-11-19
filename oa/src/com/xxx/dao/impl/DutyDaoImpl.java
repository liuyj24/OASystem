package com.xxx.dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import com.xxx.dao.DutyDao;
import com.xxx.entity.Department;
import com.xxx.entity.Duty;
import com.xxx.entity.Employee;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DutyDaoImpl implements DutyDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 查看有没有签到
     * @param empid
     * @param today
     * @return
     */
    @Override
    public boolean find(String empid, Date today) {
        boolean flag = false;//默认没有签到
        try{
            String sql = "select * from duty where emprid=? and dtdate=?";
            Object[] params = {empid, today};
            Duty duty = qr.query(sql, new BeanHandler<Duty>(Duty.class), params );
            if(null != duty){//已经签到过了
                flag = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 保存考勤信息
     * @param duty
     * @return
     */
    @Override
    public int save(Duty duty) {
        try{
            String sql = "insert into duty(emprid, dtdate, signintime, signouttime) values(?,?,?,?)";
            Object[] params = {duty.getEmpid(), duty.getDtDate(), duty.getSigninTime(), null};
            return qr.update(sql, params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 更新考勤信息
     * @param duty
     * @return
     */
    @Override
    public int update(Duty duty) {
        try{
            String sql = "update duty set signouttime=? where emprid=?";
            Object[] params = {duty.getSignoutTime(), duty.getEmpid()};
            return qr.update(sql, params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据条件查找所有考勤信息
     * @param empid
     * @param deptno
     * @param signinTime
     * @return
     */
    @Override
    public List<Duty> findDuty(String empid, int deptno, Date signinTime) {
        try{
            StringBuilder sql = new StringBuilder(
                    "SELECT du.*, e.realname, de.deptname" +
                    " FROM duty du" +
                    " JOIN employee e" +
                    " ON emprid=empid" +
                    " JOIN dept de" +
                    " ON de.deptno = e.deptno" +
                    " WHERE 1=1"
            );
            //给sql添加条件语句
            if(null != empid && !empid.trim().equals("")){
                sql.append(" and du.emprid='"+ empid +"'");
            }
            if(0 != deptno){
                sql.append(" and de.deptno='"+ deptno +"'");
            }
            if(null != signinTime){
                sql.append(" and du.dtdate='"+ signinTime +"'");
            }
            List<Map<String, Object>> list = qr.query(sql.toString(), new MapListHandler());

            //封装好每个duty
            List<Duty> dutyList = new ArrayList<>();
            for(Map<String, Object> map : list){
                Department dept = new Department();
                dept.setDeptname((String) map.get("deptname"));

                Employee emp = new Employee();
                emp.setRealname((String) map.get("realname"));
                emp.setDept(dept);

                Duty duty = new Duty();
                duty.setEmpid((String) map.get("emprid"));
                duty.setDtDate((java.util.Date) map.get("dtdate"));
                duty.setSigninTime((String) map.get("signintime"));
                duty.setSignoutTime((String) map.get("signouttime"));
                duty.setEmp(emp);

                dutyList.add(duty);//把duty添加到集合中
            }
            return  dutyList;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}

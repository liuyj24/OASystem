package com.xxx.dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import com.xxx.dao.ExpenseDao;
import com.xxx.entity.Employee;
import com.xxx.entity.Expense;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpenseDaoImpl implements ExpenseDao {
    QueryRunner qr = new TxQueryRunner();

    @Override
    public int nextExpid() {
        try{
            String sql = "SELECT nextval('expid') FROM DUAL";
            Number number = (Number) qr.query(sql, new ScalarHandler());
            return number.intValue();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存报销单
     * @param expense
     */
    @Override
    public int save(Expense expense) {
        try {
            String sql = "insert into expense values(?,?,?,?,?,?,?,?)";
            Object[] params = {expense.getExpid(), expense.getEmpid(), expense.getTotalAmount(),
            new Date(expense.getExpTime().getTime()), expense.getExpDesc(), expense.getNextAuditor(),
            expense.getLastResult(), expense.getStatus()};
            return qr.update(sql, params);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据审核人的id查找报销单
     * @param empid
     * @return
     */
    @Override
    public List<Expense> findByAuditId(String empid) {
        try{
            //涉及到多表查询, 需要把emp.realname也查出来
            String sql = "SELECT ex.*, em.realname FROM expense ex JOIN employee em ON ex.empid=em.empid WHERE nextauditor=?";
            //return qr.query(sql, new BeanListHandler<Expense>(Expense.class), empid); //会报异常执行不了

            //老师的思路是,查完后再把审核人audit的id封装起来放到expense中, 我觉得不需要, 查出来后只要封装成expense集合, 显示审核人的时候显示当前登陆的用户就性了
            //但最后我的方法报了异常执行不了
            List<Map<String, Object>> list = qr.query(sql, new MapListHandler(), empid);
            List<Expense> expList = new ArrayList<>();
            for(Map<String, Object> map : list){
                Expense exp = new Expense(
                    (int)map.get("expid"),
                    (String) map.get("empid"),
                    (float)map.get("totalamount"),
                    (java.util.Date) map.get("exptime"),
                    (String) map.get("expdesc"),
                    (String) map.get("nextauditor"),
                    (String) map.get("lastResult"),
                    (String) map.get("status")
                );
                Employee emp = new Employee();
                emp.setRealname((String) map.get("realname"));
                exp.setEmp(emp);
                expList.add(exp);
            }
            return expList;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新报销单的方法
     * @param exp
     * @return
     */
    @Override
    public int update(Expense exp) {
        try{
            String sql = "update expense set nextauditor=?, lastResult=?, status=? where expid=?";
            Object[] params = {exp.getNextAuditor(), exp.getLastResult(), exp.getStatus(), exp.getExpid()};
            return qr.update(sql, params);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据报销单的id查找报销单
     * @param expid
     * @return
     */
    @Override
    public Expense finById(int expid) {
        try{
            String sql = "select * from expense where expid=?";
            return qr.query(sql, new BeanHandler<Expense>(Expense.class), expid);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

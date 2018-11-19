package com.xxx.dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import com.xxx.dao.DepartmentDao;
import com.xxx.entity.Department;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoImp implements DepartmentDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加部门
     * @param dept
     * @return
     */
    @Override
    public int add(Department dept) {
        try {
            String sql = "insert into dept values(?,?,?)";
            Object[] params = {dept.getDeptno(), dept.getDeptname(), dept.getLocation()};
            return qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询所有部门
     * @return
     */
    public List<Department> findAll() {
        try{
            String sql = "select * from dept";
            return qr.query(sql, new BeanListHandler<Department>(Department.class));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定id删除部门
     * @param deptno
     * @return
     */
    public int delete(int deptno) {
        try{
            String sql = "delete from dept where deptno=?";
            return qr.update(sql, deptno);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据部门id查找部门
     * @param deptno
     * @return
     */
    public Department findById(int deptno) {
        try{
            String sql = "select * from dept where deptno = ?";
            return qr.query(sql, new BeanHandler<Department>(Department.class), deptno);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新部门信息
     * @param dept
     * @return
     */
    public int update(Department dept) {
        try{
            String sql = "update dept set deptname=?,location=? where deptno=?";
            Object[] params = {dept.getDeptname(), dept.getLocation(), dept.getDeptno()};
            return qr.update(sql, params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}

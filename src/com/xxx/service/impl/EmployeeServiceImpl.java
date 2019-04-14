package com.xxx.service.impl;

import com.xxx.dao.EmployeeDao;
import com.xxx.dao.impl.EmployeeDaoImpl;
import com.xxx.entity.Employee;
import com.xxx.service.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDao employeeDao = new EmployeeDaoImpl();
    @Override
    public int add(Employee emp) {
        return employeeDao.add(emp);
    }

    @Override
    public List<Employee> findByType(int i) {
        return employeeDao.findByType(i);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public List<Employee> find(Employee emp) {
        return employeeDao.find(emp);
    }

    @Override
    public int delete(String empid) {
        return employeeDao.delete(empid);
    }

    @Override
    public Employee findById(String empid) {
        return employeeDao.findById(empid);
    }

    @Override
    public int update(Employee emp) {
        return employeeDao.update(emp);
    }

    @Override
    public Employee login(String empid, String password) {
        /**
         * 1.先判断用户名是否正确
         * 2.如果用户名正确再判断密码是否正确
         */
        Employee emp = employeeDao.findById(empid);
        if(null != emp){
            //用户名正确,判断密码是否正确
            if(emp.getPassword().equals(password)){
                return emp;//用户名和密码都正确
            }else{
                return null;//用户名正确, 密码错误
            }
        }else {
            return null;//用户名错误
        }
    }
}

package com.xxx.dao;

import com.xxx.entity.Employee;

import java.util.List;

public interface EmployeeDao {
    public int add(Employee emp);

    List<Employee> findByType(int i);

    List<Employee> findAll();

    List<Employee> find(Employee emp);

    int delete(String empid);

    Employee findById(String empid);

    int update(Employee emp);
}

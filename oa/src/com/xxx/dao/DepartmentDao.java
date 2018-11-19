package com.xxx.dao;

import com.xxx.entity.Department;

import java.util.List;

public interface DepartmentDao {

    public int add(Department dept);

    public List<Department> findAll();

    public int delete(int deptno);

    public Department findById(int deptno);

    public int update(Department dept);
}

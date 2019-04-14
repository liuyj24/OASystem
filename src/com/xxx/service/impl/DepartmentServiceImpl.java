package com.xxx.service.impl;

import com.xxx.dao.impl.DepartmentDaoImp;
import com.xxx.entity.Department;
import com.xxx.service.DepartmentService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    DepartmentDaoImp departmentDao = new DepartmentDaoImp();

    @Override
    public int add(Department dept) {
        return departmentDao.add(dept);
    }

    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    public int delete(int deptno) {
        return departmentDao.delete(deptno);
    }

    public Department findById(int deptno) {
        return departmentDao.findById(deptno);
    }

    public int update(Department dept) {
        return departmentDao.update(dept);
    }
}

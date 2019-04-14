package com.xxx.service;

import com.xxx.entity.Auditing;
import com.xxx.entity.Expense;

import java.util.List;

public interface ExpenseService {
    void save(Expense expense);

    List<Expense> findByAuditId(String empid);

    void audit(Auditing audit);

    Expense findById(int expid);
}

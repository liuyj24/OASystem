package com.xxx.dao;

import com.xxx.entity.Expense;

import java.util.List;

/**
 * 报销单
 */
public interface ExpenseDao {

    int nextExpid();

    int save(Expense expense);

    List<Expense> findByAuditId(String empid);

    int update(Expense exp);

    Expense finById(int expid);
}

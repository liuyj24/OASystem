package com.xxx.service.impl;

import cn.itcast.jdbc.JdbcUtils;
import com.xxx.dao.AuditingDao;
import com.xxx.dao.ExpenseDao;
import com.xxx.dao.ExpenseItemDao;
import com.xxx.dao.impl.AuditingDaoImpl;
import com.xxx.dao.impl.ExpenseDaoImpl;
import com.xxx.dao.impl.ExpenseItemDaoImpl;
import com.xxx.dao.impl.PaymentDaoImpl;
import com.xxx.entity.Auditing;
import com.xxx.entity.Expense;
import com.xxx.entity.ExpenseItem;
import com.xxx.entity.Payment;
import com.xxx.service.ExpenseService;
import com.xxx.util.Constants;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class ExpenseServiceImpl implements ExpenseService {
    ExpenseDao expenseDao = new ExpenseDaoImpl();
    ExpenseItemDao expenseItemDao = new ExpenseItemDaoImpl();
    AuditingDao auditingDao = new AuditingDaoImpl();
    PaymentDaoImpl paymentDao = new PaymentDaoImpl();

    /**
     * 保存报销单的方法
     * @param expense
     */
    @Override
    public void save(Expense expense) {
        //获取expense表中expid下一自增的值
        int expid = expenseDao.nextExpid();

        //开启事务
        try {
            //开启事务
            JdbcUtils.beginTransaction();

            //完成业务层操作
            //保存expense
            expense.setExpid(expid);//设置报销单的编号
            expenseDao.save(expense);//保存报销单

            //保存明细
            List<ExpenseItem> itemList = expense.getItemList();
            for(ExpenseItem ei : itemList){
                ei.setExpid(expid);//设置报销单的编号
                expenseItemDao.save(ei);//保存报销单明细
            }

            //提交事务
            JdbcUtils.commitTransaction();

        } catch (Exception e) {
            e.printStackTrace();
            //出现异常, 进行回滚
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
        }
    }

    /**
     * 根据用户的id查询他需要处理的报销单
     * @param empid
     * @return
     */
    @Override
    public List<Expense> findByAuditId(String empid) {
        return expenseDao.findByAuditId(empid);
    }

    /**
     * 完成报销单审核的工作
     * @param audit
     */
    @Override
    public void audit(Auditing audit) {
        try {
            //开启事务
            JdbcUtils.beginTransaction();
            //审核是否通过
            if("通过".equals(audit.getResult())){//是否通过
                if(audit.getEmp().getPos().getPosid() == 3){//是否是财务
                    //是财务
                    //添加支出记录
                    Payment payment = new Payment();
                    payment.setAmount((float) audit.getExp().getTotalAmount());
                    payment.setPayEmpid(audit.getEmpid());
                    payment.setPayTime(new Date());
                    payment.setExpid(audit.getExpid());
                    payment.setEmpid(audit.getExp().getEmpid());
                    payment.setPayEmp(audit.getEmp());
                    payment.setExp(audit.getExp());
                    //调用dao层,保存支出信息
                    paymentDao.save(payment);
                    //修改报销单状态
                    Expense exp = new Expense();
                    exp.setExpid(audit.getExpid());
                    exp.setStatus(Constants.EXPENSE_STATUS_GET_MOENY);//已经打款
                    exp.setLastResult(audit.getResult());
                    exp.setNextAuditor(null);//打款后这个报销单就算完成了, 上级处理人设置为null;
                    expenseDao.update(exp);
                }else{
                    //金额大于2500吗
                    if(audit.getExp().getTotalAmount() > 2500){
                        //金额大于2500
                        //当前审核人是总裁吗?
                        if(Constants.BOSS_ID.equals(audit.getEmp().getEmpid())){
                            //添加审核记录
                            auditingDao.save(audit);
                            //修改报销单状态
                            Expense exp = new Expense();
                            exp.setExpid(audit.getExpid());
                            exp.setStatus(Constants.EXPENSE_STATUS_PASS);//审核通过
                            exp.setLastResult(audit.getResult());
                            exp.setNextAuditor(Constants.MANAGER_ID);//下一个审核的人应该是财务部的员工的id
                            expenseDao.update(exp);
                        }else{
                            //添加审核记录
                            auditingDao.save(audit);
                            //修改报销单状态
                            Expense exp = new Expense();
                            exp.setExpid(audit.getExpid());
                            exp.setStatus(Constants.EXPENSE_STATUS_AUDITING);//审核进行中
                            exp.setLastResult(audit.getResult());
                            exp.setNextAuditor(Constants.BOSS_ID);//下一个审核的人应该是财务部的员工的id
                            expenseDao.update(exp);
                        }
                    }else{
                        //金额小于2500, 当前人员不是财务
                        //添加审核记录
                        auditingDao.save(audit);
                        //修改报销单状态
                        Expense exp = new Expense();
                        exp.setExpid(audit.getExpid());
                        exp.setStatus(Constants.EXPENSE_STATUS_PASS);//审核通过
                        exp.setLastResult(audit.getResult());
                        exp.setNextAuditor(Constants.MANAGER_ID);//下一个审核的人应该是财务部的员工的id
                        expenseDao.update(exp);
                    }
                }
            }else{
                //没有通过,拒绝或者打回
                //添加审核记录
                auditingDao.save(audit);
                //修改报销单状态
                Expense exp = new Expense();//创建一个Expense对象, 把要修改的信息都封装在里面
                exp.setExpid(audit.getExpid());//设置expid
                if("打回".equals(audit.getResult())){
                    exp.setStatus(Constants.EXPENSE_STATUS_REPLUSE);//打回
                }else{
                    exp.setStatus(Constants.EXPENSE_STATUS_REFUSE);//拒绝
                }
                exp.setLastResult(audit.getResult());//设置审核最后的结果
                exp.setNextAuditor(null);//没有通过审核的报销单没有上级处理者了
                expenseDao.update(exp);
            }
            //提交事务
            JdbcUtils.commitTransaction();

        } catch (SQLException e) {
            //回滚事务
            try {
                JdbcUtils.rollbackTransaction();
                throw new RuntimeException(e);
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
        }
    }

    @Override
    public Expense findById(int expid) {
        return expenseDao.finById(expid);
    }
}

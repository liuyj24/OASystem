package com.xxx.util;

/**
 * 常量类
 */
public class Constants {

    private String status;//报销单的状态:0新创建, 1审核中, 2审核结束通过, 3审核拒绝, 4审核打回, 5已打款

    public static final String EXPENSE_STATUS_NEW = "0";//新创建
    public static final String EXPENSE_STATUS_AUDITING = "1";//审核中
    public static final String EXPENSE_STATUS_PASS = "2";//审核结束通过
    public static final String EXPENSE_STATUS_REFUSE = "3";//审核拒绝
    public static final String EXPENSE_STATUS_REPLUSE = "4";//审核打回
    public static final String EXPENSE_STATUS_GET_MOENY = "5";//已打款

    public static final String BOSS_ID = "xiaozhu";//总裁的id
    public static final String MANAGER_ID = "xiaoji";//财务经理的id
}

package com.xxx.entity;

public class ExpenseItem {
    private int itemid;//明细条目的编号, 设置为自增
    private int expid;//报销单的编号, 顺带一个报销单的属性
    private String type;//报销单明细的类型:通信费用, 办公耗材, 水电费等
    private double amount;//该明细的花费
    private String itemDesc;//明细的说明
    private Expense exp;

    public ExpenseItem() {
    }

    public ExpenseItem(String type, double amount, String itemDesc) {
        this.type = type;
        this.amount = amount;
        this.itemDesc = itemDesc;
    }

    public ExpenseItem(int itemid, int expid, String type, double amount, String itemDesc, Expense exp) {
        this.itemid = itemid;
        this.expid = expid;
        this.type = type;
        this.amount = amount;
        this.itemDesc = itemDesc;
        this.exp = exp;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getExpid() {
        return expid;
    }

    public void setExpid(int expid) {
        this.expid = expid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Expense getExp() {
        return exp;
    }

    public void setExp(Expense exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "ExpenseItem{" +
                "itemid=" + itemid +
                ", expid=" + expid +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", itemDesc='" + itemDesc + '\'' +
                ", exp=" + exp +
                '}';
    }
}

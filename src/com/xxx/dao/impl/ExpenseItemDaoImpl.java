package com.xxx.dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import com.xxx.dao.ExpenseItemDao;
import com.xxx.entity.ExpenseItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class ExpenseItemDaoImpl implements ExpenseItemDao {
    QueryRunner qr = new TxQueryRunner();
    /**
     * 往数据库中添加报销单明细
     * @param ei
     */
    @Override
    public int save(ExpenseItem ei) {
        try{
            //先获得itemid
            String sql1 = "SELECT nextval_item_seq('itemid') FROM DUAL";
            Number itemid = (Number) qr.query(sql1, new ScalarHandler());
            ei.setItemid(itemid.intValue());
            //保存明细
            String sql = "insert into expenseitem values(?,?,?,?,?)";
            Object[] params = {ei.getItemid(), ei.getExpid(), ei.getType(), ei.getAmount(), ei.getItemDesc()};
            return qr.update(sql, params);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

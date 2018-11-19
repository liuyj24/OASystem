package com.xxx.dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import com.xxx.dao.PaymentDao;
import com.xxx.entity.Payment;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;
import java.sql.Timestamp;

public class PaymentDaoImpl implements PaymentDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 保存支出信息
     * @param payment
     * @return
     */
    @Override
    public int save(Payment payment) {
        try {
            String sql = "insert into payment(payempid, amount, paytime, expid, empid) values(?,?,?,?,?)";
            Object[] params = {
                    payment.getPayEmpid(),
                    payment.getAmount(),
                    new Timestamp(payment.getPayTime().getTime()),
                    payment.getExpid(),
                    payment.getEmpid()
            };
            return qr.update(sql, params);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

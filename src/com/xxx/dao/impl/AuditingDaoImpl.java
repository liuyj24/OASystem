package com.xxx.dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import com.xxx.dao.AuditingDao;
import com.xxx.entity.Auditing;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;


public class AuditingDaoImpl implements AuditingDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 保存审核信息
     * @param audit
     * @return
     */
    @Override
    public int save(Auditing audit) {
        try {
            //先获得auditid自增序列
            int auditid = (int) qr.query("SELECT nextval_audit_seq('auditid') FROM DUAL;", new ScalarHandler());
            //设置参数
            Object[] params = {
                auditid,
                audit.getExpid(),
                audit.getEmpid(),
                audit.getResult(),
                audit.getAuditDesc(),
                 new java.sql.Timestamp(audit.getTime().getTime())//使用时间戳可以表示yyyy-MM-dd hh-mm-ss
            };
            String sql = "insert into auditing values(?, ?, ?, ?, ?, ?)";
            return qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

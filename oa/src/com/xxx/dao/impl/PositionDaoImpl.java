package com.xxx.dao.impl;

import cn.itcast.jdbc.TxQueryRunner;
import com.xxx.dao.PositionDao;
import com.xxx.entity.Position;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class PositionDaoImpl implements PositionDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 查找所有岗位
     * @return
     */
    @Override
    public List<Position> findAll() {
        try {
            String sql = "select * from position";
            return qr.query(sql, new BeanListHandler<Position>(Position.class));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加岗位
     * @param pos
     * @return
     */
    @Override
    public int add(Position pos) {
        try {
            String sql = "insert into position values(?,?,?)";
            Object[] params = {pos.getPosid(), pos.getPname(), pos.getPdesc()};
            return qr.update(sql, params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除岗位
     * @param posid
     * @return
     */
    @Override
    public int delete(int posid) {
        try {
            String sql = "delete from position where posid=?";
            return qr.update(sql, posid);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据岗位id查找岗位
     * @param posid
     * @return
     */
    @Override
    public Position findById(int posid) {
        try {
            String sql = "select * from position where posid=?";
            return qr.query(sql, new BeanHandler<Position>(Position.class), posid);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新岗位信息
     * @param pos
     * @return
     */
    @Override
    public int update(Position pos) {
        try {
            String sql = "update position set pname=?,pdesc=? where posid=?";
            Object[] params = {pos.getPname(), pos.getPdesc(), pos.getPosid()};
            return qr.update(sql, params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}

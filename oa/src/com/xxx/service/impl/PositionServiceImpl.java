package com.xxx.service.impl;

import com.xxx.dao.impl.PositionDaoImpl;
import com.xxx.entity.Position;
import com.xxx.service.PositionService;

import java.util.List;

public class PositionServiceImpl implements PositionService {
    PositionDaoImpl positionDao = new PositionDaoImpl();

    @Override
    public List<Position> findAll() {
        return positionDao.findAll();
    }

    @Override
    public int add(Position pos) {
        return positionDao.add(pos);
    }

    @Override
    public int delete(int posid) {
        return positionDao.delete(posid);
    }

    @Override
    public Position findById(int posid) {
        return positionDao.findById(posid);
    }

    @Override
    public int update(Position pos) {
        return positionDao.update(pos);
    }
}

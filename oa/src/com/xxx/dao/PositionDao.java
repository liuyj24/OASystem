package com.xxx.dao;

import com.xxx.entity.Position;

import java.util.List;

public interface PositionDao {
    public List<Position> findAll();

    public int add(Position pos);

    int delete(int posid);

    Position findById(int posid);

    int update(Position pos);
}

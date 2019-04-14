package com.xxx.service;

import com.xxx.entity.Position;

import java.util.List;

public interface PositionService {

    public List<Position> findAll();

    public int add(Position pos);

    int delete(int posid);

    Position findById(int posid);

    int update(Position pos);
}

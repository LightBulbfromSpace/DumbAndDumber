package com.example.db;

import java.sql.Connection;

abstract public class Repository<T> {
    protected Connection conn = DatabaseUtil.connect();

    abstract public Integer add(T entity);

    abstract public void delete(int id);
}

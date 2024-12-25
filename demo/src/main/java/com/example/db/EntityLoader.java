package com.example.db;

import java.sql.Connection;
import java.util.ArrayList;

abstract public class EntityLoader<T> {

    protected Connection conn = DatabaseUtil.connect();

    abstract public T loadById(int id);

    abstract public ArrayList<T> loadAll();
}


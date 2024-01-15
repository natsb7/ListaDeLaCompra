package com.nataliasep.listadelacompra.Interfaces;

import java.util.List;
import java.util.Map;

public abstract class DAO <T> {

    protected final String tableName;

    public DAO(String tableName) {
        this.tableName = tableName;
    }

    public abstract T findById(long id);
    public abstract List<T> findAll();
    public abstract List<T> findBy(Map<String, String> condition);
    public abstract boolean update(T e);
    public abstract long insert(T e);
    public abstract boolean delete(T e);
}

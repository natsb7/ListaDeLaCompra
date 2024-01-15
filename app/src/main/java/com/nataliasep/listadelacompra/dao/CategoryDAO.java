package com.nataliasep.listadelacompra.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.nataliasep.listadelacompra.Interfaces.DAO;
import com.nataliasep.listadelacompra.Models.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAO extends DAO<Category> {
    private static final String TABLE_NAME = "category";
    private final SQLiteDatabase db;
    private final Map<String, Integer> columnIndex;

    public CategoryDAO(SQLiteDatabase db) {
        super(TABLE_NAME);
        this.db = db;
        columnIndex = new HashMap<>();
        fillColumnIndex();
    }

    private void fillColumnIndex() {
        try (Cursor c = db.rawQuery("SELECT * FROM " + tableName, null)) {
            String [] columnNames = c.getColumnNames();
            for (int i = 0; i < columnNames.length; i++) {
                columnIndex.put(columnNames[i], i);
            }
        }
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Cursor c = db.query(tableName, null, null, null, null, null, null)) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(columnIndex.get("id"));
                    String name = c.getString(columnIndex.get("name"));
                    String img = c.getString(columnIndex.get("img"));
                    categories.add(new Category(id, name, img));
                } while (c.moveToNext());
            }
        }
        return categories;
    }

    @Override
    public Category findById(long id) {
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor c = db.query(tableName, null, "id = ?",  selectionArgs, null, null, null)) {
            if (c.moveToFirst()) {
                String name = c.getString(columnIndex.get("name"));
                String img = c.getString(columnIndex.get("img"));
                return new Category(id, name, img);
            }
        }
        return null;
    }

    @Override
    public List<Category> findBy(Map<String, String> condition) {
        String[] selectionArgs = new String[condition.keySet().size()];
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String key : condition.keySet()) {
            if (sb.length() == 0) {
                sb.append(key).append("=? ");
            } else {
                sb.append("AND ").append(key).append(" =? ");
            }
            selectionArgs[count++] = condition.get(key);  // a la posicion 1 le corresponde el valor de la clave key con la q estamos trabajando
        }
        List<Category> categories = new ArrayList<>();
        try (Cursor c = db.query(tableName, null, sb.toString(),  selectionArgs, null, null, null)) {
            if (c.moveToFirst()) {
                do {
                    long id = c.getInt(columnIndex.get("id"));
                    String name = c.getString(columnIndex.get("name"));
                    String img = c.getString(columnIndex.get("img"));
                    categories.add(new Category(id, name, img));
                } while (c.moveToNext());
            }
        }
        return categories;
    }

    @Override
    public boolean update(Category e) {
        String[] args = new String[] {String.valueOf(e.getId())};
        ContentValues values = new ContentValues();
        values.put("name", e.getName());
        values.put("img", e.getImg());
        return db.update(tableName, values, "id=?", args) == 1;
    }

    @Override
    public long insert(Category e) {
        ContentValues values = new ContentValues();
        values.put("name", e.getName());
        values.put("img", e.getImg());
        // El método insert devuelve el identificador de la fila insertada o
        // -1 en caso de que se haya producido un error
        return db.insert(tableName,null, values);
    }

    @Override
    public boolean delete(Category e) {
        String[] args = new String[] {String.valueOf(e.getId())};
        return db.delete(tableName, "id=?", args) == 1;
    }
}


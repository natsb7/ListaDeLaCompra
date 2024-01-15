package com.nataliasep.listadelacompra.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nataliasep.listadelacompra.Interfaces.DAO;
import com.nataliasep.listadelacompra.Models.Category;
import com.nataliasep.listadelacompra.Models.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDAO extends DAO<Item> {

    private static final String TABLE_NAME = "item";
    private final SQLiteDatabase db;
    private final Map<String, Integer> columnIndex;

    public ItemDAO(SQLiteDatabase db) {
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
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (Cursor c = db.query(tableName, null, null, null, null, null, null)) {
            if (c.moveToFirst()) {
                do {
                    long id = c.getInt(columnIndex.get("id"));
                    String name = c.getString(columnIndex.get("name"));
                    int category = c.getInt(columnIndex.get("category"));
                    String img = c.getString(columnIndex.get("img"));
                    items.add(new Item(id, name, category, img));
                } while (c.moveToNext());
            }
        }
        return items;
    }

    @Override
    public Item findById(long id) {
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor c = db.query(tableName, null, "id = ?",  selectionArgs, null, null, null)) {
            if (c.moveToFirst()) {
                String name = c.getString(columnIndex.get("name"));
                int category = c.getInt(columnIndex.get("category"));
                String img = c.getString(columnIndex.get("img"));
                return new Item(id,name, category, img);
            }
        }
        return null;
    }

    @Override
    public List<Item> findBy(Map<String, String> condition) {
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
        List<Item> items = new ArrayList<>();
        try (Cursor c = db.query(tableName, null, sb.toString(),  selectionArgs, null, null, null)) {
            if (c.moveToFirst()) {
                do {
                    long id = c.getInt(columnIndex.get("id"));
                    String name = c.getString(columnIndex.get("name"));
                    int category = c.getInt(columnIndex.get("category"));
                    String img = c.getString(columnIndex.get("img"));
                    items.add(new Item(id, name, category, img));
                } while (c.moveToNext());
            }
        }
        return items;
    }

    @Override
    public boolean update(Item e) {
        String[] args = new String[] {String.valueOf(e.getId())};
        ContentValues values = new ContentValues();
        values.put("name", e.getName());
        values.put("category", e.getId());
        values.put("img", e.getImg());
        return db.update(tableName, values, "id=?", args) == 1;
    }

    @Override
    public long insert(Item e) {
        ContentValues values = new ContentValues();
        values.put("name", e.getName());
        values.put("category", e.getId());
        values.put("img", e.getImg());
        // El método insert devuelve el identificador de la fila insertada o
        // -1 en caso de que se haya producido un error
        return db.insert(tableName,null, values);
    }

    @Override
    public boolean delete(Item e) {
        String[] args = new String[] {String.valueOf(e.getId())};
        return db.delete(tableName, "id=?", args) == 1;
    }
}

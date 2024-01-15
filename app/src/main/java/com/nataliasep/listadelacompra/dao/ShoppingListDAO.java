package com.nataliasep.listadelacompra.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nataliasep.listadelacompra.Interfaces.DAO;


import java.util.ArrayList;
import java.util.HashMap;

import com.nataliasep.listadelacompra.Models.Item;
import com.nataliasep.listadelacompra.Models.ItemList;
import com.nataliasep.listadelacompra.Models.ShoppingList;

import java.util.List;
import java.util.Map;

public class ShoppingListDAO extends DAO<ShoppingList> {

    private static final String TABLE_NAME = "list";
    private final SQLiteDatabase db;
    private final Map<String, Integer> columnIndex;

    public ShoppingListDAO(SQLiteDatabase db) {
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
    public List<ShoppingList> findAll() {
        List<ShoppingList> lists = new ArrayList<>();
        try (Cursor c = db.query(tableName, null, null, null, null, null, null)) {
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(columnIndex.get("id"));
                    String name = c.getString(columnIndex.get("name"));
                    String date = c.getString(columnIndex.get("date"));
                    //List itemList = new ArrayList<>();
                    lists.add(new ShoppingList(id, name, date/*, itemList*/));
                } while (c.moveToNext());
            }
        }
        return lists;
    }

    @Override
    public ShoppingList findById(long id) {
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor c = db.query(tableName, null, "id = ?",  selectionArgs, null, null, null)) {
            if (c.moveToFirst()) {
                String name = c.getString(columnIndex.get("name"));
                String date = c.getString(columnIndex.get("date"));
                ArrayList itemList = new ArrayList<>();
                return new ShoppingList(id, name, date, itemList);
            }
        }
        return null;
    }

    @Override
    public List<ShoppingList> findBy(Map<String, String> condition) {
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
        List<ShoppingList> lists = new ArrayList<>();
        try (Cursor c = db.query(tableName, null, sb.toString(),  selectionArgs, null, null, null)) {
            if (c.moveToFirst()) {
                do {
                    long id = c.getInt(columnIndex.get("id"));
                    String name = c.getString(columnIndex.get("name"));
                    String date = c.getString(columnIndex.get("date"));
                    ArrayList itemList = new ArrayList<>();
                    lists.add(new ShoppingList(id, name, date, itemList));
                } while (c.moveToNext());
            }
        }
        return lists;
    }

    @Override
    public boolean update(ShoppingList e) {
        String[] args = new String[] {String.valueOf(e.getId())};
        ContentValues values = new ContentValues();
        values.put("name", e.getName());
        values.put("date", e.getDate());
        //values.put("item_list", e.getItemsList());
        return db.update(tableName, values, "id=?", args) == 1;
    }

    @Override
    public long insert(ShoppingList e) {
        ContentValues values = new ContentValues();
        values.put("name", e.getName());
        //values.put("item_list", e.getItemsList());
        // El método insert devuelve el identificador de la fila insertada o
        // -1 en caso de que se haya producido un error
        return db.insert(tableName,null, values);
    }

    @Override
    public boolean delete(ShoppingList e) {
        String[] args = new String[] {String.valueOf(e.getId())};
        return db.delete(tableName, "id=?", args) == 1;
    }

    public int countProductsFromShoppingList(long listId) {
        String[] selectionArgs = {String.valueOf(listId)};
        try (Cursor c = db.rawQuery("SELECT COUNT(id) FROM product_list WHERE fk_list_id = ?", selectionArgs)) {
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
        }
        return 0;
    }

    public List<ItemList> getProductsFromShoppingList(int listId) {
        List<ItemList> listItems = new ArrayList<>();
        String[] selectionArgs = {String.valueOf(listId)};
        try (Cursor c = db.rawQuery("SELECT i.*, il.is_purchased FROM item i JOIN item_list il ON i.id = il.fk_item WHERE il.fk_list = ?",
                selectionArgs)) {
            if (c.moveToFirst()) {
                long id =  c.getLong(columnIndex.get("id"));
                String name = c.getString(columnIndex.get("name"));
                String img = c.getString(columnIndex.get("img"));
                int isPurchased = c.getInt(columnIndex.get("is_purchased"));
            }
        }
        return listItems;
    }
}

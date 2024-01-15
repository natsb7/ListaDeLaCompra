package com.nataliasep.listadelacompra.Models;

import java.util.ArrayList;

public class ShoppingList {
    private long id;
    private String name;
    private String date;
    private ArrayList<ItemList> itemsList; ;

    public ShoppingList() {
    }
    public ShoppingList(String name){
        this.name = name;
    }

    public ShoppingList(long id, String name, String date){
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public ShoppingList(long id, String name, String date, ArrayList<ItemList> itemsList) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.itemsList = itemsList;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<ItemList> getItemsList() {
        return itemsList;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setItemsList(ArrayList<ItemList> itemsList) {
        this.itemsList = itemsList;
    }



}

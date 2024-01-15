package com.nataliasep.listadelacompra.Models;

public class Item {

    private long id;
    private String name;
    private int fk_category;
    private String img;

    public Item() {
    }

    public Item(long id, String name, int category, String img) {
        this.id = id;
        this.name = name;
        this.fk_category = category;
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCategory() {
        return fk_category;
    }

    public String getImg() {
        return img;
    }

}

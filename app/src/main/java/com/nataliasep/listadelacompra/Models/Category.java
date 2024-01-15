package com.nataliasep.listadelacompra.Models;

public class Category {

    private long id;

    private String name;

    private String img;



    public Category(long id, String name, String img){
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public Category(long id) {
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getImg(){
        return img;
    }
}

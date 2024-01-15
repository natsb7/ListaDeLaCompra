package com.nataliasep.listadelacompra.Models;

public class User {
    private long id;
    private String username;

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}

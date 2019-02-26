package com.app.usertreatzasia.entities;

/**
 * Created by ahmedsyed on 11/30/2017.
 */

public class MerchanEnt {

    int id;
    String name;

    public MerchanEnt(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

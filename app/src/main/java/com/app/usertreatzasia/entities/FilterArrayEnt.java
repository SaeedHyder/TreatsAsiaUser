package com.app.usertreatzasia.entities;

/**
 * Created by ahmedsyed on 12/2/2017.
 */

public class FilterArrayEnt {

    String name;
    int id;

    public FilterArrayEnt(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

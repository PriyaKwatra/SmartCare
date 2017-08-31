package com.example.acer.finder;

/**
 * Created by acer on 27.08.2017.
 */

public class Geometry {

    String icon;
    String id;
    String name;
    LocationClass location;

    public Geometry(String icon, String id, String name) {
        this.icon = icon;
        this.id = id;
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.example.acer.finder;

/**
 * Created by acer on 27.08.2017.
 */

public class Results {


    String icon;
    String id;
    String name;
    String place_id;

    public Results(String icon, String id, String name, String place_id) {
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.place_id = place_id;
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

    public String getPlace_id() {
        return place_id;
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

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}

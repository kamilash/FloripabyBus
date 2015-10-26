package com.example.root.floripabybus.model;

import java.io.Serializable;


public class Route implements Serializable {
    private String name;
    private String shortName;
    private String id;

    public Route(String name, String shortName, String id) {
        this.name = name;
        this.shortName = shortName;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

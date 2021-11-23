package com.ajtech.shopassistant.Models;

public class Product {
    private String id, name, category, desc, position;

    public Product(String id, String name, String category, String position, String desc){
        this.id = id;
        this.name = name;
        this.category = category;
        this.desc = desc;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getDesc() {
        return desc;
    }
}

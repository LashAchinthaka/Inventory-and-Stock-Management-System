package com.inventory.inventory.model;

public class Product extends Item {
    private String category;

    public Product() {
    }

    public Product(String id, String name, int quantity, double price, String category) {
        super(id, name, quantity, price);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
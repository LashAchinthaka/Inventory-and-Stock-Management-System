package com.inventory.inventory.model;

import jakarta.validation.constraints.*;

public class Item {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_-]{3,10}$", message = "ID must be 3-10 characters (letters/numbers/_/-)")
    private String id;

    @NotBlank
    @Size(min = 3, max = 50, message = "Name must be 3-50 characters")
    private String name;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 10000, message = "Quantity too large")
    private int quantity;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private double price;

    // Constructor
    public Item() {
    }

    public Item(String id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
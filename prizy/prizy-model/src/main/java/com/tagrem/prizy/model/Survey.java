package com.tagrem.prizy.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;

public class Survey {

    private String id;
    @JsonIgnore
    private Product product;
    private Store store;
    private double price;
    private String notes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static Comparator<Survey> priceComparator() {
        return Comparator.comparingDouble(value -> value.price);
    }
}

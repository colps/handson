package com.tagrem.prizy.model;


public class Barcode {
    private String id;
    private String description;

    public Barcode() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

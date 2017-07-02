package com.tagrem.prizy.model;


import java.util.ArrayList;
import java.util.List;

public class Product {

    private String id;
    private Barcode code;
    private List<Survey> surveys = new ArrayList<>();
    private double averagePrice;
    private double highestPrice;
    private double lowestPrice;
    private double idealPrice;


    public Barcode getCode() {
        return code;
    }

    public void setCode(Barcode code) {
        this.code = code;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void addSurvey(Survey s) {
        surveys.add(s);
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSurveyCount(){
        return surveys.size();
    }

    public double getIdealPrice() {
        return idealPrice;
    }

    public void setIdealPrice(double idealPrice) {
        this.idealPrice = idealPrice;
    }

    public List<Survey> getSurveys() {
        return surveys;
    }
}

package com.tagrem.prizy.calculator;


import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;

public class AveragePriceCalculator implements PriceCalculator {
    @Override
    public void calculate(Product product, Survey latest) {
        int count = product.getSurveyCount();
        double total = product.getAveragePrice() * (count - 1);
        product.setAveragePrice((total + latest.getPrice())/count);
    }
}

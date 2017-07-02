package com.tagrem.prizy.calculator;


import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;

public class HighestPriceCalculator implements PriceCalculator{

    @Override
    public void calculate(Product product, Survey latest) {
        if(product.getHighestPrice() < latest.getPrice()){
            product.setHighestPrice(latest.getPrice());
        }
    }
}

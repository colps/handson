package com.tagrem.prizy.calculator;


import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;

public class LowestPriceCalculator implements PriceCalculator{

    @Override
    public void calculate(Product product, Survey latest) {
        if(product.getLowestPrice() > latest.getPrice()){
            product.setLowestPrice(latest.getPrice());
        }
    }
}

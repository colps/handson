package com.tagrem.prizy.calculator;

import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;

public interface PriceCalculator {

    public void calculate(Product product, Survey latest);

}

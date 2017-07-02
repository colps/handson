package com.tagrem.prizy.calculator;


import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;

import java.util.List;

public class IdealPriceCalculator implements PriceCalculator {
    @Override
    public void calculate(Product product, Survey latest) {
        List<Survey> surveys = product.getSurveys();
        if(surveys.size() <= 4){
            product.setIdealPrice(0d);
            return;
        }
        Double price = surveys
                .stream()
                .sorted(Survey.priceComparator())
                .skip(2)
                .limit(product.getSurveyCount() - 4)
                .mapToDouble(Survey::getPrice)
                .average()
                .orElse(0);

        product.setIdealPrice(price * 1.2);

    }
}

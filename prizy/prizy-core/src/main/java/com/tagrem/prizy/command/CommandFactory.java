package com.tagrem.prizy.command;

import com.tagrem.prizy.calculator.PriceCalculator;
import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.service.Service;

import java.util.List;

public class CommandFactory {


    public AddSurveyCommand newAddSurveyCommand(Service<Product> service, List<PriceCalculator> priceCalculators, AddSurveyRequest req) {
        return new AddSurveyCommand(service, priceCalculators, req);
    }

    public ViewProductsCommand newViewProductsCommand(Service<Product> service) {
        return new ViewProductsCommand(service);
    }

    public ViewProductDetailsCommand newViewProductDetailsCommand(Service<Product> service, String productId) {
        return new ViewProductDetailsCommand(productId, service);
    }

}

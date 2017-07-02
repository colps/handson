package com.tagrem.prizy.command;

import com.tagrem.prizy.calculator.PriceCalculator;
import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.service.Service;

import java.util.List;

public class AddSurveyCommand implements Command<Void> {


    private final List<PriceCalculator> calculators;
    private final Service<Product> productService;
    private final AddSurveyRequest request;

    public AddSurveyCommand(Service<Product> productService, List<PriceCalculator> calculators, AddSurveyRequest request) {
        this.productService = productService;
        this.request = request;
        this.calculators = calculators;
    }

    @Override
    public CommandResult<Void> execute() {
        Product product = productService.findOne(request.getProductId());
        calculators.forEach(priceCalculator -> priceCalculator.calculate(product, request.getCurrent()));
        product.addSurvey(request.getCurrent());
        productService.save(product);
        return new CommandResult<>(Void.INSTANCE, CommandResult.Status.OK, CommandResult.Code.SUCCESS, "Add Survey");
    }
}

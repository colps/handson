package com.tagrem.prizy.controller;

import com.tagrem.prizy.calculator.PriceCalculator;
import com.tagrem.prizy.command.*;
import com.tagrem.prizy.command.Void;
import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;
import com.tagrem.prizy.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/restapi/v1")
public class RestController {

    @Autowired
    private Service<Product> productService;

    @Autowired
    private List<PriceCalculator> priceCalculators;

    @Autowired
    private CommandFactory factory;

    @RequestMapping(method = RequestMethod.GET, value = "/product")
    public ResponseEntity<CommandResult<ProductList>> getProducts() {
        ViewProductsCommand command = factory.newViewProductsCommand(productService);
        CommandResult<ProductList> resp = command.execute();
        return new ResponseEntity<CommandResult<ProductList>>(resp, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/product/{productId}")
    public ResponseEntity<CommandResult<Product>> getProductDetails(@PathVariable String productId) {
        ViewProductDetailsCommand command = factory.newViewProductDetailsCommand(productService, productId);
        CommandResult<Product> resp = command.execute();
        return new ResponseEntity<CommandResult<Product>>(resp, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/product/{productId}/survey")
    public ResponseEntity<CommandResult<Void>> addSurvey(@PathVariable String productId, @RequestBody Survey current) {
        AddSurveyCommand command = factory.newAddSurveyCommand(productService, priceCalculators, new AddSurveyRequest(current, productId));
        CommandResult<Void> resp = command.execute();
        return new ResponseEntity<CommandResult<Void>>(resp, HttpStatus.OK);

    }


    public void setProductService(Service<Product> productService) {
        this.productService = productService;
    }

    public void setPriceCalculators(List<PriceCalculator> priceCalculators) {
        this.priceCalculators = priceCalculators;
    }

    public void setFactory(CommandFactory factory) {
        this.factory = factory;
    }
}

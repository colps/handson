package com.tagrem.prizy.command;


import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.service.Service;

public class ViewProductDetailsCommand implements Command<Product>{

    private final String productId;
    private final Service<Product> productService;

    public ViewProductDetailsCommand(String productId, Service<Product> productService){
        this.productId = productId;
        this.productService = productService;
    }

    @Override
    public CommandResult<Product> execute() {
        Product product = productService.findOne(productId);
        return new CommandResult<Product>(product, CommandResult.Status.OK, CommandResult.Code.SUCCESS, "View Product Details");
    }
}

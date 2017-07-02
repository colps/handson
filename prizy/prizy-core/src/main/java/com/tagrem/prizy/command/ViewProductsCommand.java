package com.tagrem.prizy.command;

import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.service.Service;

import java.util.List;

public class ViewProductsCommand implements Command<ProductList> {

    private final Service<Product> productService;

    public ViewProductsCommand(Service<Product> productService) {
        this.productService = productService;
    }
    
    @Override
    public CommandResult<ProductList> execute() {
        List<Product> products = productService.fetchAll();
        return new CommandResult<ProductList>(new ProductList(products),
                CommandResult.Status.OK, CommandResult.Code.SUCCESS, " View products");
    }
}

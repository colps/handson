package com.tagrem.prizy;

import com.tagrem.prizy.calculator.*;
import com.tagrem.prizy.command.CommandFactory;
import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.service.InMemoryServiceImpl;
import com.tagrem.prizy.service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfiguration {

    @Bean
    public Service<Product> inMemoryService() {
        return new InMemoryServiceImpl();
    }

    @Bean
    public List<PriceCalculator> priceCalculators(){
        return Arrays.asList( new HighestPriceCalculator(),
                new AveragePriceCalculator(),
                new LowestPriceCalculator(),
                new IdealPriceCalculator()
        );
    }

    @Bean
    public CommandFactory factory(){
        return new CommandFactory();
    }

}

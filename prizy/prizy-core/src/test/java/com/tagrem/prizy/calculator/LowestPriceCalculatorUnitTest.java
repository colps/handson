package com.tagrem.prizy.calculator;

import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class LowestPriceCalculatorUnitTest {

    private Product product;
    private Survey latest;
    private PriceCalculator lowest = new LowestPriceCalculator();

    @Before
    public void init () {
        product = mock(Product.class);
        latest = mock(Survey.class);
        when(product.getLowestPrice()).thenReturn(50d);
    }

    @Test
    public void itShouldUpdateLowestPrice () {
        when(latest.getPrice()).thenReturn(40d);
        lowest.calculate(product, latest);
        verify(product, times(1)).setLowestPrice(40d);
    }

    @Test
    public void itShouldNotUpdateLowestPrice () {
        when(latest.getPrice()).thenReturn(60d);
        lowest.calculate(product, latest);
        verify(product, never()).setLowestPrice(anyDouble());
    }

}

package com.tagrem.prizy.calculator;

import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class HighestPriceCalculatorUnitTest {

    private Product product;
    private Survey latest;
    private PriceCalculator highest = new HighestPriceCalculator();

    @Before
    public void init () {
        product = mock(Product.class);
        latest = mock(Survey.class);
        when(product.getHighestPrice()).thenReturn(25d);
    }

    @Test
    public void itShouldUpdateHighestPrice () {
        when(latest.getPrice()).thenReturn(40d);
        highest.calculate(product, latest);
        verify(product, times(1)).setHighestPrice(40d);
    }

    @Test
    public void itShouldNotUpdateHighestPrice () {
        when(latest.getPrice()).thenReturn(10d);
        highest.calculate(product, latest);
        verify(product, never()).setHighestPrice(anyDouble());
    }

}

package com.tagrem.prizy.calculator;

import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AveragePriceCalculatorUnitTest {

    private Product product;
    private Survey latest;
    private PriceCalculator average = new AveragePriceCalculator();

    @Before
    public void init () {
        product = mock(Product.class);
        latest = mock(Survey.class);
        when(product.getAveragePrice()).thenReturn(50d);
    }

    @Test
    public void itShouldUpdateAveragePrice () {
        when(latest.getPrice()).thenReturn(40d);
        when(product.getSurveyCount()).thenReturn(2);
        average.calculate(product, latest);
        verify(product, times(1)).setAveragePrice(45d);
    }


}

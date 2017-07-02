package com.tagrem.prizy.calculator;

import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Survey;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class IdealPriceCalculatorUnitTest {

    private Product product;
    private Survey latest;
    private PriceCalculator average = new IdealPriceCalculator();

    @Before
    public void init () {
        product = mock(Product.class);
        latest = mock(Survey.class);
    }

    @Test
    public void itShouldUpdateIdealPrice () {
        int surveyCount = 10;
        List<Survey> surveys = getMockSurveys(surveyCount, 20d);
        when(product.getSurveys()).thenReturn(surveys);
        when(product.getSurveyCount()).thenReturn(surveyCount);
        average.calculate(product, latest);
        verify(product, times(1)).setIdealPrice(24d);
    }

    @Test
    public void itShouldUpdateIdealPriceAsZero() {
        int surveyCount = 3;
        List<Survey> surveys = getMockSurveys(surveyCount, 20d);
        when(product.getSurveys()).thenReturn(surveys);
        when(product.getSurveyCount()).thenReturn(surveyCount);
        average.calculate(product, latest);
        verify(product, times(1)).setIdealPrice(0d);
    }

    private List<Survey> getMockSurveys(int size, double price){
        return Stream.generate(() -> mock(Survey.class))
                .limit(size)
                .map(survey -> {
                    when(survey.getPrice()).thenReturn(price);
                    return survey;
                })
                .collect(Collectors.toList());
    }

}

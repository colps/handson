package com.tagrem.prizy.command;

import com.tagrem.prizy.calculator.*;
import com.tagrem.prizy.model.Barcode;
import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Store;
import com.tagrem.prizy.model.Survey;
import com.tagrem.prizy.service.Service;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class AddSurveyCommandUnitTest {

    private CommandFactory factory = new CommandFactory();
    private Service<Product> productService;
    private List<PriceCalculator> calculators;
    private Product p;
    private Survey survey;

    @Before
    public void init () {
        productService = mock(Service.class);
        calculators = Arrays.asList( mock(HighestPriceCalculator.class),
                mock(AveragePriceCalculator.class),
                mock(LowestPriceCalculator.class),
                mock(IdealPriceCalculator.class)
        );

        p = new Product();
        p.setId("p1");
        Barcode b = new Barcode();
        b.setId(p.getId());
        b.setDescription("description");
        p.setCode(b);
        Survey s = new Survey();
        s.setId("sp11");
        s.setPrice(20d);
        Store store = new Store();
        store.setId("store1");
        store.setAddress("address1");
        s.setStore(store);
        p.addSurvey(s);
        p.setHighestPrice(s.getPrice());
        p.setLowestPrice(s.getPrice());
        p.setAveragePrice(s.getPrice());
        p.setIdealPrice(0d);
        s.setProduct(p);


        survey = new Survey();
        survey.setId("sp12");
        survey.setPrice(15d);
        store = new Store();
        store.setId("store2");
        store.setAddress("address2");
        s.setStore(store);
    }

    @Test
    public void itShouldUpdateProduct () {
        // given
        given(productService.findOne(anyString())).willReturn(p);
        // when
        AddSurveyRequest req = new AddSurveyRequest(survey, p.getId());
        AddSurveyCommand command = factory.newAddSurveyCommand(productService, calculators, req);
        command.execute();

        // then
        verify(productService, times(1)).save(p);
    }

    @Test
    public void itShouldAddSurveyToProduct() {
        // given
        given(productService.findOne(anyString())).willReturn(p);

        // when
        AddSurveyRequest req = new AddSurveyRequest(survey, p.getId());
        AddSurveyCommand command = factory.newAddSurveyCommand(productService, calculators, req);
        command.execute();

        // then
        assertThat(p.getSurveyCount(), is(2));
    }

    @Test
    public void itShouldCalculatePriceForProduct() {
        // given
        given(productService.findOne(anyString())).willReturn(p);
        // when
        AddSurveyRequest req = new AddSurveyRequest(survey, p.getId());
        AddSurveyCommand command = factory.newAddSurveyCommand(productService, calculators, req);
        command.execute();

        // then
        calculators.forEach(
                priceCalculator ->
                        verify(priceCalculator, times(1)).calculate(p, survey));
    }

}

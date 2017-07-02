package com.tagrem.prizy.command;

import com.tagrem.prizy.model.Barcode;
import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Store;
import com.tagrem.prizy.model.Survey;
import com.tagrem.prizy.service.Service;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ViewProductsCommandUnitTest {

    private CommandFactory factory = new CommandFactory();
    private Service<Product> productService;
    private Product p;
    private Survey survey;

    @Before
    public void init () {
        productService = mock(Service.class);

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
    public void itShouldFetchAllProducts() {
        // given
        given(productService.fetchAll()).willReturn(Arrays.asList(p));
        // when
        ViewProductsCommand command = factory.newViewProductsCommand(productService);
        command.execute();
        // then
        verify(productService, times(1)).fetchAll();
    }



}

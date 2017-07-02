package com.tagrem.prizy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tagrem.prizy.Application;
import com.tagrem.prizy.model.Barcode;
import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Store;
import com.tagrem.prizy.model.Survey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class RestControllerUnitTest {

    private MockMvc mockMvc;
    private ObjectMapper jsonMapper;
    private Product p;
    private Survey survey;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void init () {
        this.mockMvc = webAppContextSetup(context).build();
        this.jsonMapper = new ObjectMapper();

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

    private String toJson(Object o) throws Exception {
        String json = jsonMapper.writeValueAsString(o);
        return json;
    }

    @Test
    public void itShouldReturnAllProducts() throws Exception {
        mockMvc.perform(
                get("/restapi/v1/product").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(""))
                        .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturnProductDetails() throws Exception {
        mockMvc.perform(
                get("/restapi/v1/product/p1").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(""))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldAddSurveyToProduct() throws Exception {
        mockMvc.perform(
                post("/restapi/v1/product/p1/survey").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJson(survey)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

package com.tagrem.prizy.service;

import com.tagrem.prizy.model.Barcode;
import com.tagrem.prizy.model.Product;
import com.tagrem.prizy.model.Store;
import com.tagrem.prizy.model.Survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryServiceImpl implements Service<Product>{

    public final Map<String, Product> db = new HashMap<String, Product>();

    public InMemoryServiceImpl() {
        Product p = new Product();
        p.setId("p1");
        Barcode b = new Barcode();
        b.setId(p.getId());
        b.setDescription("description");
        p.setCode(b);
        Survey s = new Survey();
        s.setId("sp11");
        s.setPrice(10d);
        Store store = new Store();
        store.setId("store1");
        store.setAddress("address");
        s.setStore(store);
        p.addSurvey(s);
        p.setHighestPrice(s.getPrice());
        p.setLowestPrice(s.getPrice());
        p.setAveragePrice(s.getPrice());
        p.setIdealPrice(0d);
        s.setProduct(p);
        db.put(p.getId(), p);


    }

    public List<Product> fetchAll() {
        return new ArrayList<Product>(db.values());
    }

    public Product findOne(String id) {
        return db.get(id);
    }

    public void save(Product entity) {
        String id = Math.random()+ "";
        entity.setId(id);
        db.put(id, entity);
    }

    @Override
    public void update(Product entity) {
        db.put(entity.getId(), entity);
    }
}

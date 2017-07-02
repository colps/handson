package com.tagrem.prizy.service;


import java.util.List;

public interface Service<T> {

    public List<T> fetchAll();

    public T findOne(String id);

    public void save(T entity);

    public void update(T entity);

}

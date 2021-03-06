package com.jingpaidang.crm.dao.example;

import com.jingpaidang.crm.domain.example.Example;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JackChan
 * Date: 8/6/13
 * Time: 10:27 AM
 */
public interface ExampleMapper {

    public void insert(Example example);

    public List<Example> selectAll();

    public void update(Example example);

    public void delete(int id);
}

package com.example.springproject.service;


import com.example.springproject.pojo.Order;
import com.example.springproject.pojo.PageBean;
import com.example.springproject.pojo.Product;

import java.util.List;

public interface ProductService {


    PageBean<Product> allList(Integer pageNum, Integer pageSize, String category);

    List<Product> search(String category,String type);

    Product findById(Integer id);

    void order(Integer quantity, Product order);

    void delete(String name);

    void deleteCart(Integer total);

    List<Order> findListById(String id);

    List<Order> orderList();

    List<Order> findListByUsername(String username);
}

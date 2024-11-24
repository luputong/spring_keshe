package com.example.springproject.service.Impl;

import com.example.springproject.mapper.ProductMapper;
import com.example.springproject.mapper.UserMapper;
import com.example.springproject.pojo.*;
import com.example.springproject.service.ProductService;
import com.example.springproject.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean<Product> allList(Integer pageNum, Integer pageSize, String category) {
        PageBean<Product> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        log.info(category);
        List<Product> ps = productMapper.allList(pageNum, pageSize, category);
        log.info(ps.toString());
        Page<Product> p = (Page<Product>) ps;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public List<Product> search(String category, String name) {
        List<Product> lp = productMapper.search(category, name);
        return lp;
    }

    @Override
    public Product findById(Integer id) {
        Product lp = productMapper.findById(id);
        return lp;
    }

    @Override
    public void order(Integer quantity, Product product) {
        Order order = new Order();
        Double t = quantity * product.getPrice();
        order.setName(product.getName());
        order.setPrice(product.getPrice());
        order.setQuantity(quantity);
        order.setTotal(t);
        order.setUrl(product.getUrl());
        order.setCategory(product.getCategory());
        order.setType(product.getType());
        HashMap map = ThreadLocalUtil.get();
        order.setUserUsername(String.valueOf(map.get("username")));
        productMapper.order(order);
    }

    @Override
    public void delete(String fruitName) {
        HashMap map = ThreadLocalUtil.get();
        User username = userMapper.findByUserName(String.valueOf(map.get("username")));
        log.info(username.getUsername());
        productMapper.delete(fruitName,username.getUsername());
    }

    @Override
    public void deleteCart(Integer total) {
        HashMap map = ThreadLocalUtil.get();
        User username = userMapper.findByUserName(String.valueOf(map.get("username")));
        List<Order> orders = productMapper.orderList(username.getUsername());
        for (Order order :orders) {
        productMapper.addHistory(order);
        }
        productMapper.deleteAll(username.getUsername());
        Double d = username.getMoney() - total;
        userMapper.updateMoney(d, username.getId());
    }

    @Override
    public List<Order> findListById(String username) {
        List<Order> lp = productMapper.findListById(username);
        return lp;
    }

    @Override
    public List<Order> orderList() {
        HashMap map = ThreadLocalUtil.get();
        User username = userMapper.findByUserName(String.valueOf(map.get("username")));
        List<Order> lo = productMapper.orderList(username.getUsername());
        return lo;
    }

    @Override
    public List<Order> findListByUsername(String username) {
       return productMapper.findListByUsername(username);
    }


}

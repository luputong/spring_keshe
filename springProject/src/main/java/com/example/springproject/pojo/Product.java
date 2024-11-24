package com.example.springproject.pojo;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String name;
    private Double price;
    private Integer quantity;
    private String category;  //类别
    private String type;  //分类
    private String url;
}

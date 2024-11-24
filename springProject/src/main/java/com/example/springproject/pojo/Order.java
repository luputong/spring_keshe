package com.example.springproject.pojo;

import lombok.Data;

@Data
public class Order {
    private Integer id;
    private String name;
    private Integer quantity;
    private String url;
    private Double price;
    private Double total;
    private String category;  //类别
    private String type;  //种类
    private String userUsername;
}

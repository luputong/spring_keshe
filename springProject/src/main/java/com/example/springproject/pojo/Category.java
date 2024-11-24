package com.example.springproject.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    private Integer id;
    private String type;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

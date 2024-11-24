package com.example.springproject.service;

import com.example.springproject.pojo.Category;

public interface CategoryService {
    void add(Category category);

    void delete(String type);
}

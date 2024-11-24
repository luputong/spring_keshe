package com.example.springproject.service.Impl;

import com.example.springproject.mapper.CategoryMapper;
import com.example.springproject.pojo.Category;
import com.example.springproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }

    @Override
    public void delete(String type) {
        categoryMapper.delete(type);
    }

}

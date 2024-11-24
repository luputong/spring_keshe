package com.example.springproject.controller;

import com.example.springproject.pojo.Category;
import com.example.springproject.pojo.Result;
import com.example.springproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody Category category){
        categoryService.add(category);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteByName(@RequestBody String type){
        categoryService.delete(type);
        return Result.success();
    }

}

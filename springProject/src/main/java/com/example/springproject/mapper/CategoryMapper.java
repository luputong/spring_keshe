package com.example.springproject.mapper;

import com.example.springproject.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category(type, createtime, updatetime) values (#{type},#{createTime},#{updateTime})")
    void add(Category category);

    @Delete("delete from category where type=#{type}")
    void delete(String type);
}

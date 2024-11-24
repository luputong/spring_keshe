package com.example.springproject.mapper;

import com.example.springproject.pojo.Order;
import com.example.springproject.pojo.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> allList(Integer pageNum, Integer pageSize, String category);

    List<Product> search(String category, String type);

    @Select("select * from product where id=#{id}")
    Product findById(Integer id);

    @Insert("insert into `order`(name, quantity, price, category, type, total, url,user_username) " +
            "VALUES (#{name},#{quantity},#{price},#{category},#{type},#{total},#{url},#{userUsername})")
    void order(Order order);

    @Delete("delete from `order` where name=#{name} and user_username=#{username}")
    void delete(String name, String username);

    @Delete("delete from `order` where user_username=#{username}")
    void deleteAll(String username);

    @Select("select * from `history`where user_username=#{username}")
    List<Order> findListById(String username);

    @Select("select * from `order` where user_username=#{username}")
    List<Order> orderList(String username);

    @Insert("insert into `history`(name, quantity, price, category, type, total, url, user_username) " +
            "values (#{name},#{quantity},#{price},#{category},#{type}," +
            "#{total},#{url},#{userUsername})")
    void addHistory(Order order);
    @Select("select  * from `order` where user_username =#{username}" )
    List<Order> findListByUsername(String username);
}

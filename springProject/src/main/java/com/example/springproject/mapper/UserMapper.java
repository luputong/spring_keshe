package com.example.springproject.mapper;

import com.example.springproject.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User findByUserName(String username);

    @Insert("insert into user(username,password,createTime,updateTime)" + "values(#{username},#{password},now(),now())")
    void addUser(String username, String password);


    //userName email sex birthday
    @Update("update user set username=#{username}, email=#{email},sex=#{sex},birthday=#{birthday},updateTime=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update user set pic=#{picUrl},updateTime=now() where id=#{id}")
    void updatePic(String picUrl, Integer id);

    @Update("update user set money = #{d} where id=#{id}")
    void updateMoney(Double d, Integer id);

    @Update("update `order` set user_username=#{username} where user_username=#{oldUsername}")
    void updateUsername(String username,String oldUsername);
}

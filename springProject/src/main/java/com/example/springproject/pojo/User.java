package com.example.springproject.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class User {
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String sex;
    @Email
    private String email;
    private Double money;
    private String pic;
    private LocalDate birthday;
    private LocalDateTime createTime;  //创建时间
    private LocalDateTime updateTime;  //更新时间
}

package com.example.springproject.controller;

import com.example.springproject.pojo.Order;
import com.example.springproject.pojo.Result;
import com.example.springproject.pojo.User;
import com.example.springproject.service.ProductService;
import com.example.springproject.service.UserService;
import com.example.springproject.utils.JwtUtil;
import com.example.springproject.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,8}$") String username, @Pattern(regexp = "^\\S{5,8}$") String password) {
        log.info(String.format("用户%s注册", username));
        User user = userService.findByUserName(username);
        if (user == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,8}$") String username, @Pattern(regexp = "^\\S{5,8}$") String password) {
        User loginUser = userService.findByUserName(username);
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        if (password.equals(loginUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set("token", token,600, TimeUnit.SECONDS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userinfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        log.info(user.toString());
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        log.info(user.toString());
        return Result.success();
    }

    @PatchMapping("/updatePic")
    public Result updatePic(@RequestParam("picUrl") String picUrl) {
        userService.updatePic(picUrl);
        return Result.success();
    }

    @PostMapping("/getOrder")
    public Result gerOrder() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        List<Order> lp = productService.findListByUsername(username);
        log.info(lp.toString());
        return Result.success(lp);
    }

    @GetMapping("/addMoney")
    public Result addMoney(@RequestParam("money") Integer money) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        user.setMoney(user.getMoney() + money);
        userService.updateMoney(user.getId(), user.getMoney());
        return Result.success();
    }
}


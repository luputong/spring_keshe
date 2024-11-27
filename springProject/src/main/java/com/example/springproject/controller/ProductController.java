package com.example.springproject.controller;

import com.example.springproject.pojo.*;
import com.example.springproject.service.ProductService;
import com.example.springproject.service.UserService;
import com.example.springproject.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/fruits")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //获取所有产品列表
    @GetMapping
    public Result<PageBean<Product>> allList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String category
    ) {
        PageBean<Product> pb = productService.allList(pageNum, pageSize, category);
        return Result.success(pb);
    }
  @PutMapping
  public Result add(@RequestParam Integer id) {
      Product byId = productService.findById(id);
      return Result.success(byId);
    }
    //模糊搜索产品
    @GetMapping("/search")
    public Result<List<Product>> search(@RequestParam("category") String category, @RequestParam("name") String name) {
        List<Product> lp = productService.search(category, name);
        return Result.success(lp);
    }

    //通过用户名查询产品
    @GetMapping("/find")
    public Result<List<Order>> findByName() {
        HashMap o = ThreadLocalUtil.get();
        String username = (String) o.get("username");
        List<Order> lp = productService.findListById(username);
        log.info(lp.toString());
        return Result.success(lp);
    }

    //添加订单
    @PostMapping("/order")
    public Result order(@RequestBody addOrder addOrder) {
        Product byId = productService.findById(addOrder.getId());
        productService.order(addOrder.getNum(), byId);
        return Result.success();
    }

    //取消订单
    @DeleteMapping("/cancel")
    public Result delete(@RequestParam String name) {
        productService.delete(name);
        return Result.success();
    }

    //结算订单
    @GetMapping("/calculate")
    public Result calculate(@RequestParam("total") Integer total) {
        HashMap map = ThreadLocalUtil.get();
        String s = String.valueOf(map.get("username"));
        Double money = userService.findByUserName(s).getMoney();
        if (money <total) {
            return Result.error("余额不足");
        }
        productService.deleteCart(total);
        return Result.success();
    }
    @GetMapping("/orderList")
    public Result<List<Order>> orderList(){
        List<Order> lo = productService.orderList();
        return Result.success(lo);
    }
}

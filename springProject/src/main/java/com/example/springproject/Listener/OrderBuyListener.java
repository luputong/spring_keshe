package com.example.springproject.Listener;

import com.example.springproject.pojo.Result;
import com.example.springproject.pojo.addOrder;
import com.example.springproject.service.ProductService;
import com.example.springproject.service.UserService;
import com.example.springproject.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@RequiredArgsConstructor
//@Component
public class OrderBuyListener {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(OrderBuyListener.class);
    private final ProductService productService;
    @RabbitListener(queues = "order1")
    public Result calculate(@RequestParam("total") Integer total) {
        log.info("进来了");
        HashMap map = ThreadLocalUtil.get();
        log.info("进来了2");
        String s = String.valueOf(map.get("username"));
        Double money = userService.findByUserName(s).getMoney();
        if (money <total) {
            return Result.error("余额不足");
        }
        productService.deleteCart(total);
        return Result.success();
    }
}

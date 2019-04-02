package com.chenjing.weixinpay.controller;

import com.chenjing.weixinpay.domain.JsonData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description：OrderController
 *
 * @author:chenjing
 * @version:1.0
 * @time:15:37
 */

/*
* 订单接口
* */
@RestController
@RequestMapping("/user/api/v1/order")
public class OrderController {

    @GetMapping("add")
    public JsonData saveOrder(){
        return JsonData.buildSuccess("下单成功");
    }

}

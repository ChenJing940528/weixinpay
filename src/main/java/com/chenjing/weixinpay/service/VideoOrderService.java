package com.chenjing.weixinpay.service;

import com.chenjing.weixinpay.domain.VideoOrder;
import com.chenjing.weixinpay.dto.VideoOrderDto;
import org.apache.ibatis.annotations.Param;

/**
 * description：VideoOrderService
 *
 * @author:chenjing
 * @version:1.0
 * @time:16:25
 */
/*
* 订单接口
* */
public interface VideoOrderService {

    /*
    * 下单接口
    * */
    String save(VideoOrderDto videoOrderDto) throws Exception;

    /*
    *
    * 根据流水号查找订单
    * */
    VideoOrder findByOutTradeNo(String outTradeNo);

    /*
    * 根据流水号跟新订单
    * */
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);
}

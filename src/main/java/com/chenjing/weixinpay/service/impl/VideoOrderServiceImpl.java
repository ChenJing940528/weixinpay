package com.chenjing.weixinpay.service.impl;

import com.chenjing.weixinpay.config.WeChatConfig;
import com.chenjing.weixinpay.domain.User;
import com.chenjing.weixinpay.domain.Video;
import com.chenjing.weixinpay.domain.VideoOrder;
import com.chenjing.weixinpay.dto.VideoOrderDto;
import com.chenjing.weixinpay.mapper.UserMapper;
import com.chenjing.weixinpay.mapper.VideoMapper;
import com.chenjing.weixinpay.mapper.VideoOrderMapper;
import com.chenjing.weixinpay.service.VideoOrderService;
import com.chenjing.weixinpay.utils.CommonUtils;
import com.chenjing.weixinpay.utils.HttpUtils;
import com.chenjing.weixinpay.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * description：VideoOrderServiceImpl
 *
 * @author:chenjing
 * @version:1.0
 * @time:16:32
 */
@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoOrderMapper videoOrderMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String save(VideoOrderDto videoOrderDto) throws Exception {

        //查找视频信息
        Video video = videoMapper.findById(videoOrderDto.getVideoId());

        //查找用户信息
        User user = userMapper.findByid(videoOrderDto.getUserId());

        //生成订单
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setVideoId(video.getId());

        videoOrder.setState(0);
        videoOrder.setUserId(user.getId());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());

        videoOrder.setDel(0);
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setOutTradeNo(CommonUtils.genertateUUID());
        videoOrderMapper.insert(videoOrder);//将订单存入数据库
        //获取codeurl
        String codeUrl = unifiedOrder(videoOrder);//统一下单

        return codeUrl;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {

        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public int updateVideoOrderByOutTradeNo(VideoOrder videoOrder) {

        return videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
    }

    /*
    * 统一下单方法
    * */
    private String unifiedOrder(VideoOrder videoOrder) throws Exception {

        //生成签名
        SortedMap<String, String> params = new TreeMap<>();
        params.put("appid", weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str", CommonUtils.genertateUUID());
        params.put("body", videoOrder.getVideoTitle());
        params.put("out_trade_no", videoOrder.getOutTradeNo());
        params.put("total_fee", videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip", videoOrder.getIp());
        params.put("notify_url", weChatConfig.getPayCallbackUrl());
        params.put("trade_type", "NATIVE");

        //sign签名
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign", sign);

        //map转XML
        String payXml = WXPayUtil.mapToXml(params);

        System.out.println(payXml);

        //统一下单
        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrder(), payXml, 4000);
        if(null == orderStr){
            return null;
        }

        Map<String, String> unifiedOrderMap = WXPayUtil.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap.toString());
        if(unifiedOrderMap != null){
            return unifiedOrderMap.get("code_url");
        }

        return null;
    }
}

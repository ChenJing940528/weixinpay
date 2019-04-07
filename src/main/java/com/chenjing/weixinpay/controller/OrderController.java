package com.chenjing.weixinpay.controller;

import com.chenjing.weixinpay.dto.VideoOrderDto;
import com.chenjing.weixinpay.service.VideoOrderService;
import com.chenjing.weixinpay.utils.IpUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


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
//@RequestMapping("/user/api/v1/order")
@RequestMapping("/api/v1/order")//用来测试的地址，int userId = 1;userId是写死的
public class OrderController {

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    private Logger dataLogger = (Logger) LoggerFactory.getLogger("dataLogger");

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("add")
    public void saveOrder(@RequestParam(value = "video_id", required = true)int videoId,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        //异常测试
        //int c = 1/0;

        String ip = IpUtils.getIpAddr(request);
//        int userId = request.getAttribute("user_id");
        int userId = 1;
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setIp(ip);

        String codeUrl = videoOrderService.save(videoOrderDto);
        if(codeUrl == null){
            throw new NullPointerException();
        }
        try{

            //生成二维码配置
            Map<EncodeHintType, Object> hints = new HashMap<>();
            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            //设置编码类型
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            //生成二维码图片
            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE, 400, 400, hints);
            OutputStream out = response.getOutputStream();

            //将二维码图片写出去
            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

}

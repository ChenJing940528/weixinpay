package com.chenjing.weixinpay.controller;

import com.chenjing.weixinpay.config.WeChatConfig;
import com.chenjing.weixinpay.domain.JsonData;
import com.chenjing.weixinpay.domain.User;
import com.chenjing.weixinpay.domain.VideoOrder;
import com.chenjing.weixinpay.service.UserService;
import com.chenjing.weixinpay.service.VideoOrderService;
import com.chenjing.weixinpay.utils.JwtUtils;
import com.chenjing.weixinpay.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

/**
 * description：WechatController
 *
 * @author:chenjing
 * @version:1.0
 * @time:15:17
 *
 *  @RestController注解相当于@ResponseBody + @Controller合在一起的作用
 */
@Controller
//@RestController
@RequestMapping("/api/v1/wechat")
public class WechatController {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private VideoOrderService videoOrderService;

    /*
    * 拼装微信扫一扫登录url
    *
    * @RequestParam(value = "access_page", required = true//传这个参数可以在登录后返回到当前登录页面
    *
    * @ResponseBody 如果类上面使用的是@RestController则不用添加@ResponseBody，如果使用的是@Controller则需要使用@ResponseBody
    * */
    @GetMapping("login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page", required = true)String accessPage) throws UnsupportedEncodingException {

        String redirectUrl = weChatConfig.getOpenRedirectUrl();//获取开放平台重定向地址

        String callBackUrl = URLEncoder.encode(redirectUrl, "GBK");//进行编码

        String qrcodeUrl = String.format(weChatConfig.getOpenQrcodeUrl(), weChatConfig.getOpenAppid(), callBackUrl, accessPage);


        return JsonData.buildSuccess(qrcodeUrl);
    }



    /*
    * 微信扫码登录回调地址
    * */
    @GetMapping("/user/callback")
    public void wechatUserCallback(@RequestParam(value = "code", required = true) String code,
                                   String state, HttpServletResponse response) throws IOException {

        User user = userService.saveWeChatUser(code);
        if (user != null){
            //生成jwt
            String token = JwtUtils.genJsonWebToken(user);
            //state是当前用户的页面地址，需要拼接http:// 这样才不会站内跳转
            response.sendRedirect(state+"?token="+token+"&head_img="+user.getHeadImg()+"&name="+URLEncoder.encode(user.getName(),"UTF-8"));


        }

    }

    /*
    *
    * 微信支付回调
    * */
    @RequestMapping("/order/callback")
    public void orderCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inputStream = request.getInputStream();
        //BufferedReader是包装设计模式，性能更高
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null){
            sb.append(line);
        }
        in.close();
        inputStream.close();
        Map<String, String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
        System.out.println(callbackMap.toString());

        //将Map转换为SortedMap
        SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(callbackMap);

        //判断签名是否正确
        if(WXPayUtil.isCorrectSign(sortedMap, weChatConfig.getKey())){

            if("SUCCESS".equals(sortedMap.get("result_code"))){

                String outTradeNo = sortedMap.get("out_trade_no");

                VideoOrder dbVideoOrder = videoOrderService.findByOutTradeNo(outTradeNo);

                //更新订单状态
                if(dbVideoOrder.getState() == 0){ //判断逻辑看业务场景 state==0 为未支付， state=1 为已经支付
                    VideoOrder videoOrder = new VideoOrder();
                    videoOrder.setOpenid(sortedMap.get("openid"));
                    videoOrder.setOutTradeNo(outTradeNo);
                    videoOrder.setNotifyTime(new Date());
                    videoOrder.setState(1);

                    //影响行数row==1 或 row==0 响应微信成功或者失败
                    int rows = videoOrderService.updateVideoOrderByOutTradeNo(videoOrder);
                    if(rows == 1){//通知微信订单处理成功
                        response.setContentType("text/xml");
                        response.getWriter().print("success");
                    }
                }

            }
        }
        //都处理失败
        response.setContentType("text/xml");
        response.getWriter().print("fail");

    }
}

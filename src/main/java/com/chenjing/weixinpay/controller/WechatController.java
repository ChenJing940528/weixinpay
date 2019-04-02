package com.chenjing.weixinpay.controller;

import com.chenjing.weixinpay.config.WeChatConfig;
import com.chenjing.weixinpay.domain.JsonData;
import com.chenjing.weixinpay.domain.User;
import com.chenjing.weixinpay.service.UserService;
import com.chenjing.weixinpay.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
}

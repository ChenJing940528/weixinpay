package com.chenjing.weixinpay.service.impl;

import com.chenjing.weixinpay.config.WeChatConfig;
import com.chenjing.weixinpay.domain.User;
import com.chenjing.weixinpay.mapper.UserMapper;
import com.chenjing.weixinpay.service.UserService;
import com.chenjing.weixinpay.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * description：UserServiceImpl
 *
 * @author:chenjing
 * @version:1.0
 * @time:16:33
 */
@Service
public class UserServiceImpl implements UserService {

   @Autowired
   private WeChatConfig weChatConfig;
   @Autowired
   private UserMapper userMapper;

    @Override
    public User saveWeChatUser(String code) {

        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(), weChatConfig.getOpenAppid(), weChatConfig.getOpenAppsecret(), code);

       //获取access_toke
        Map<String, Object> baseMap = HttpUtils.doGet(accessTokenUrl);

        if(baseMap == null || baseMap.isEmpty()){ return null; }
        String accessToken = (String) baseMap.get("access_token");
        String openId = (String) baseMap.get("openid");

        User dbUser = userMapper.findByopenid(openId);
        if (dbUser != null){//不需要更新用户时，直接返回
            return dbUser;
        }

        //需要更新用户时，或者用户不存在时，进行下一步查询用户操作
        //获取用户的基本信息
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(), accessToken, openId);
        //获取access_toke
        Map<String, Object> baseUserMap = HttpUtils.doGet(userInfoUrl);

        if(baseUserMap == null || baseUserMap.isEmpty()){ return null; }
        String nickname = (String) baseUserMap.get("nickname");



        Double sexTemp = (Double) baseUserMap.get("sex");
        int sex = sexTemp.intValue();

        String province = (String) baseUserMap.get("province");
        String city = (String) baseUserMap.get("city");
        String country = (String) baseUserMap.get("country");
        String headimgurl = (String) baseUserMap.get("headimgurl");
        StringBuilder sb = new StringBuilder(country).append("||").append(province).append("||").append(city);
        String fianlAddress = sb.toString();

        try {
            //用户名解决乱码问题
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
            fianlAddress = new String(fianlAddress.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setName(nickname);
        user.setHeadImg(headimgurl);
        user.setCity(fianlAddress);
        user.setOpenid(openId);
        user.setSex(sex);
        user.setCreateTime(new Date());
        userMapper.save(user);
        return user;
    }
}

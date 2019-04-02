package com.chenjing.weixinpay.service;

import com.chenjing.weixinpay.domain.User;

/**
 * description：UserService
 *
 * @author:chenjing
 * @version:1.0
 * @time:16:31
 */
/*
* 用户业务接口类
* */
public interface UserService {

    User saveWeChatUser(String code);
}

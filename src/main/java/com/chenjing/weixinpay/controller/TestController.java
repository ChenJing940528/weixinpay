package com.chenjing.weixinpay.controller;

import com.chenjing.weixinpay.config.WeChatConfig;
import com.chenjing.weixinpay.domain.JsonData;
import com.chenjing.weixinpay.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	/*
	* 测试springboot是否搭建成功
	* */
	@RequestMapping("test")
	public String test(){
		return "hello weixinpay";
}

	/*
	* 测试访问配置文件是否成功
    * */
	@Autowired
	private WeChatConfig weChatConfig;

	@RequestMapping("test_config")
	public JsonData testConfig(){

		System.out.println(weChatConfig.getAppId());
		return JsonData.buildSuccess(weChatConfig.getAppId());
	}

	/*
    * 测试整合mybatis与mysql数据库，并测试mapper映射是否成功
    * */
	@Autowired
	private VideoMapper videoMapper;

	@RequestMapping("test_db")
	public Object testDB(){

		return videoMapper.findAll();
	}

}

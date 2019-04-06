package com.chenjing.weixinpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@MapperScan("com.chenjing.weixinpay.mapper")
//开启事物管理
@EnableTransactionManagement
public class WeixinpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinpayApplication.class, args);
	}

}

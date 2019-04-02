package com.chenjing.weixinpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
@MapperScan("com.chenjing.weixinpay.mapper")
public class WeixinpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinpayApplication.class, args);
	}

}

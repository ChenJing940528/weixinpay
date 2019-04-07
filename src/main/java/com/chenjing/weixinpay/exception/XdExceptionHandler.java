package com.chenjing.weixinpay.exception;

import com.chenjing.weixinpay.domain.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description：XdExceptionHandler
 *
 * @author:chenjing
 * @version:1.0
 * @time:20:22
 */

/*
* 异常处理控制器
* */
//SpringBoot扫描到这个注解后认为这个是处理异常的类
@ControllerAdvice
public class XdExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData Handler(Exception e){

        if(e instanceof  XdException){
            XdException xdException = (XdException) e;
            return JsonData.buildError(xdException.getMsg(), xdException.getCode());
        }else {
            return JsonData.buildError("全局异常，未知错误");
        }
    }
}

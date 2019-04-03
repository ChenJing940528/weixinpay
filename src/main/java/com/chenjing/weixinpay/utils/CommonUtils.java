package com.chenjing.weixinpay.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * description：CommonUtils
 *
 * @author:chenjing
 * @version:1.0
 * @time:9:02
 */
/*
* 常用工具类的封装，md5，uuid等
* */
public class CommonUtils {


    /*
    * 生成uuid，即用来表示唯一订单，也做 nonce_str
    * */
    public static String genertateUUID(){

        String uuid = UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 32);

        return uuid;
    }

    /*
    * md5常用工具类
    * */
    public static String MD5(String data){

        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item:
                 array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString().toUpperCase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.chenjing.weixinpay.utils;

import com.chenjing.weixinpay.domain.User;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * description：JwtUtils
 *
 * @author:chenjing
 * @version:1.0
 * @time:19:10
 */
/*
* jwt工具类，包括生成token和解析token，通过jwt的方式可以解决登录检验的问题，
* 用户访问浏览器成功后，服务端会将用户的id，姓名等重要信息，通过秘钥进行加密，
* 之后返回给用户，即在cookie中附着的token，当用户再次访问服务器时，会带着这个
* 附着token的cookie，此时服务端会用同样的秘钥对这个token进行解密，从而获得用户
* 的信息。
* */
public class JwtUtils {

    public static final String SUBJECT = "xdclass";

    public static final long EXPIRE = 1000*60*60*24*7; //过期时间，毫秒， 一周

    //秘钥
    public static final String APPSECRET = "xd666";

    /*
    * jwt加密过程
    * */
    public static String genJsonWebToken(User user){

        /*
        *生成jwt,生成token
        * */
        if(user == null || user.getId() == null || user.getName() == null
                || user.getHeadImg() == null){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                        .claim("id", user.getId())
                        .claim("name", user.getName())
                        .claim("img", user.getHeadImg())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                        .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();

        return token;
    }

    /*
    * jwt解密过程,校验token
    * */
    public static Claims checkJWT(String token){

        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET)
                                    .parseClaimsJws(token).getBody();

            return claims;
        } catch (Exception e) {
            return null;
        }
    }

}

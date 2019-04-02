package com.chenjing.weixinpay;

import com.chenjing.weixinpay.domain.User;
import com.chenjing.weixinpay.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.Test;

import java.util.Date;

/**
 * description：CommonTest
 *
 * @author:chenjing
 * @version:1.0
 * @time:19:31
 */
public class CommonTest {

    @Test
    public void testGeneJWT(){

        User user = new User();
        user.setId(999);
        user.setHeadImg("www.xdclass.net");
        user.setName("xd");

        String token = JwtUtils.genJsonWebToken(user);
        System.out.println(token);
    }

    @Test
    public void testCheck(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZGNsYXNzIiwiaWQiOjk5OSwibmFtZSI6InhkIiwiaW1nIjoid3d3LnhkY2xhc3MubmV0IiwiaWF0IjoxNTUzNjg2NTk4LCJleHAiOjE1NTQyOTEzOTh9.LA0dCmQSYcCWHepP-4fiT7ysmh2C9n5d-BN_LwP9-zQ";
        Claims claims = JwtUtils.checkJWT(token);
        if(claims != null){
            String name = (String) claims.get("name");
            String img = (String) claims.get("img");
            int id = (Integer) claims.get("id");
            System.out.println(name);
            System.out.println(img);
            System.out.println(id);
        }else {
            System.out.println("非法token");
        }

        System.out.println(new Date());
    }

}

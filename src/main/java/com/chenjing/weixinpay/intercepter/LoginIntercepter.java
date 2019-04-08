package com.chenjing.weixinpay.intercepter;

import com.chenjing.weixinpay.domain.JsonData;
import com.chenjing.weixinpay.utils.JwtUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * description：LoginIntercepter
 *
 * @author:chenjing
 * @version:1.0
 * @time:15:09
 */
public class LoginIntercepter implements HandlerInterceptor{

    private static final Gson gson = new Gson();

    /*
    * 进入controller之前进行拦截
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if(token == null){
            token = request.getParameter("token");
        }

        if(token != null){
            Claims claims = JwtUtils.checkJWT(token);
            if (claims != null) {
                Integer userId = (Integer) claims.get("id");
                String name = (String) claims.get("name");

                request.setAttribute("userId",userId);
                request.setAttribute("name",name);

                return true;
            }
        }

        sendJsonMessage(response, JsonData.buildError("未登陆，请前往登录页面"));
        return false;
    }

    /**
     * 响应数据给前端
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws IOException {

        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(gson.toJson(obj));
        writer.close();
        response.flushBuffer();
    }
}

package com.twojin.wooritheseday.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Slf4j
@Configuration
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 클라이언트로 전달할 응답 값 구성

        JSONObject jsonObject = new JSONObject();
        String failMsg = "";

        if (exception instanceof AuthenticationServiceException) {
            failMsg = "로그인 정보가 일치하지 않습니다";
        } else if (exception instanceof BadCredentialsException) {
            failMsg = "로그인 정보가 일치하지 않습니다";
        } else if (exception instanceof LockedException) {
            failMsg = "로그인 정보가 일치하지 않습니다";
        } else if (exception instanceof DisabledException) {
            failMsg = "로그인 정보가 일치하지 않습니다";
        } else if (exception instanceof AccountExpiredException) {
            failMsg = "로그인 정보가 일치하지 않습니다";
        } else if (exception instanceof CredentialsExpiredException) {
            failMsg = "로그인 정보가 일치하지 않습니다";
        }

        // 응답 값을 구성하고 전달
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        log.debug(failMsg);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfo", null);
        resultMap.put("resultCode", 9999);
        resultMap.put("failMsg", failMsg);
        jsonObject = new JSONObject(resultMap);

        writer.print(jsonObject);
        writer.flush();
        writer.close();
    }
}

package com.twojin.wooritheseday.config.filter;

import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.ConvertModules;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * https://adjh54.tistory.com/94
 */
@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtAuthorizationFilter :: doFilterInternal");

        List<String> list = Arrays.asList(
                "/user/login",
                "/user/join",
//                "/user/me",
                "/testLogin/login",
                "/testLogin/join",
                "/auth/validToken",
                "/auth/login"
        );

        String requestUri = request.getRequestURI();
        log.debug("JwtAuthorizationFilter :: " + requestUri);

        if (list.contains(requestUri)) {
            log.debug("JwtAuthorizationFilter :: contains ");
            filterChain.doFilter(request,response);
            return;
        }

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }

        String accessHeader = request.getHeader(AuthConstants.ACCESS_HEADER);
        String refreshHeader = request.getHeader(AuthConstants.REFRESH_HEADER);

        logger.debug("[+] accessHeader check: " + accessHeader);
        logger.debug("[+] refreshHeader check: " + refreshHeader);

        try {
            String accessUserId = null;
            String refreshUserId = null;

            // 토큰 빈값 확인
            if (accessHeader != null && !accessHeader.equalsIgnoreCase("")
//                    && refreshHeader != null && !refreshHeader.equalsIgnoreCase("")
            ) {
                // 토큰 유효 검증

                String accessToken = TokenUtil.getTokenFromHeader(accessHeader);
                String refreshToken = TokenUtil.getTokenFromHeader(refreshHeader);

                boolean isValid = false;

                isValid = TokenUtil.isValidToken(accessToken);

                if (isValid) { // 검증 통과라면
                    accessUserId = TokenUtil.getUserIdFromToken(accessToken);
                    log.debug("[+] accessUserId Check : " + accessUserId);

                    if (accessUserId == null && accessUserId.equalsIgnoreCase("")) {
                        // 토큰으로 id 검색 결과 있음

                        log.debug("JwtAuthorizationFilter :: access userId 검증 실패");
                        throw new BusinessExceptionHandler(ErrorCode.Expired_Token.getMessage(), ErrorCode.Expired_Token);
                    }
                }

            }

        } catch (Exception e) {
            log.debug("JwtAuthorizationFilter :: 검증 실패");
            log.debug("JwtAuthorizationFilter :: catchException");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
//            JSONObject jsonObject = jsonResponseWrapper(e);
            ApiResponse apiResponse = buildErrorResponse(e);

            JSONObject jsonObject = ConvertModules.dtoToJsonObj(apiResponse);
            jsonObject.put("status", apiResponse.getResultCode());
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();

            return;
        }


        log.debug("JwtAuthorizationFilter :: 검증성공");


        filterChain.doFilter(request, response);
        return;
    }

    /**
     * 토큰 관련 Exception 발생 시 예외 응답값 구성
     *
     * @param e Exception
     * @return JSONObject
     */
    private JSONObject jsonResponseWrapper(Exception e) {

        String resultMsg = "";
        // JWT 토큰 만료
        if (e instanceof ExpiredJwtException) {
            resultMsg = "TOKEN Expired";
        }
        // JWT 허용된 토큰이 아님
        else if (e instanceof SignatureException) {
            resultMsg = "TOKEN SignatureException Login";
        }
        // JWT 토큰내에서 오류 발생 시
        else if (e instanceof JwtException) {
            resultMsg = "TOKEN Parsing JwtException";
        }
        // 이외 JTW 토큰내에서 오류 발생
        else {
            resultMsg = "OTHER TOKEN ERROR";
        }

        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 401);
        jsonMap.put("code", "9999");
        jsonMap.put("message", resultMsg);
        jsonMap.put("reason", e.getMessage());

        JSONObject jsonObject = new JSONObject(jsonMap);

        logger.error(resultMsg, e);
        return jsonObject;
    }

    private ApiResponse buildErrorResponse(Exception e) {

        String resultMsg = "";
        String divisionCode = "";
        String errorCode = "401";

        // JWT 토큰 만료
        if (e instanceof ExpiredJwtException) {
            resultMsg = "TOKEN Expired";
        }
        // JWT 허용된 토큰이 아님
        else if (e instanceof SignatureException) {
            resultMsg = "TOKEN SignatureException Login";
        }
        // JWT 토큰내에서 오류 발생 시
        else if (e instanceof JwtException) {
            resultMsg = "TOKEN Parsing JwtException";
        } else if (e instanceof BusinessExceptionHandler) {
            resultMsg = "OTHER TOKEN ERROR";
            divisionCode = ((BusinessExceptionHandler) e).getErrorCode().getDivisionCode();

            errorCode = Integer.toString(
                    ((BusinessExceptionHandler) e).getErrorCode().getStatus()
            );
        }
        // 이외 JTW 토큰내에서 오류 발생
        else {
            resultMsg = "OTHER TOKEN ERROR";
        }

        ApiResponse apiResponse =  ApiResponse.builder()
                .result(e.getMessage())
                .resultCode(errorCode)
                .resultMsg(e.getMessage())
                .build();
        if (!divisionCode.equals("")) {
            apiResponse.setResult(apiResponse.getResult() + ":" + divisionCode);
        }

        return apiResponse;
    }

}

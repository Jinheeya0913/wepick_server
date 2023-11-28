package com.twojin.wooritheseday.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.auth.service.TokenService;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;

import static com.twojin.wooritheseday.auth.constant.AuthConstants.ACCESS_HEADER;
import static com.twojin.wooritheseday.auth.constant.AuthConstants.REFRESH_HEADER;


/**
 * AccessToken과 RefreshToken이 유효한지 확인하는 API
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;


    @RequestMapping("/validToken")
    public ResponseEntity<ApiResponse> validAuth(@RequestHeader(ACCESS_HEADER) String accessHeader,
                                            @RequestHeader(REFRESH_HEADER) String refreshHeader) throws URISyntaxException, JsonProcessingException {
        log.debug("validToken :: Start");
        String accessToken = "";
        String refreshToken = "";
        String userId = "";
        UserDTO user = null;
        String userJsonString = "";



        HttpHeaders httpHeaders = new HttpHeaders();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        ApiResponse apiResponse = null;


        try {

            if (!"".equals(accessHeader)) {
                accessToken = TokenUtil.getTokenFromHeader(accessHeader);
            } else {
                throw new BusinessExceptionHandler(ErrorCode.Expired_Token.getMessage(), ErrorCode.Expired_Token);
            }

            if (!"".equals(refreshHeader)) {
                refreshToken = TokenUtil.getTokenFromHeader(refreshHeader);
            } else {
                throw new BusinessExceptionHandler(ErrorCode.Expired_Token.getMessage(), ErrorCode.Expired_Token);
            }

            if (TokenUtil.isValidToken(accessToken)) {
                log.debug("validToken :: valid RefreshToken");
                if (!TokenUtil.isValidToken(refreshToken)) {
                    throw new BusinessExceptionHandler(ErrorCode.Expired_Token.getMessage(), ErrorCode.Expired_Token);
                    // Todo RefreshToken 만료 시에는 로그인 화면으로
                } else {
                    log.debug("validAccessToken :: Create New AccessToken");
                    // Todo RefreshToken 유효 시에는 accessToken 재발급
                    userId = TokenUtil.getUserIdFromToken(refreshToken);
                    user = userService.loadMyAccountByUserId(userId);
                    ObjectMapper objectMapper = new ObjectMapper();
                    userJsonString = objectMapper.writeValueAsString(
                            UserDTO.builder()
                                    .userNm(user.getUserNm())
                                    .userId(user.getUserId())
//                                .userAddress(user.getUserAddress())
                                    .userEmail(user.getUserEmail())
                                    .userPhoneNum(user.getUserPhoneNum())
                                    .build()
                    );

                    try {
                        jsonObject = (JSONObject) jsonParser.parse(userJsonString);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    accessToken = TokenUtil.generateAccessJwtToken(user);
                }
            } else {
                throw new BusinessExceptionHandler(ErrorCode.Expired_Token.getMessage(), ErrorCode.Expired_Token);
            }

            apiResponse = ApiResponse.createSuccessApiResponseWithObj(jsonObject);

        } catch (BusinessExceptionHandler e) {
            apiResponse = ApiResponse.builder()
                    .result("FAIL")
                    .resultCode(Integer.toString(e.getErrorCode().getStatus()))
                    .resultMsg(e.getMessage())
                    .build();
        }

        httpHeaders.set(ACCESS_HEADER, AuthConstants.TOKEN_TYPE +" "+accessToken);
        httpHeaders.set(REFRESH_HEADER, AuthConstants.TOKEN_TYPE + " "+ refreshToken);

        log.debug("validToken :: Period");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(apiResponse);
    }


    @RequestMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid UserDTO userDTO) {
        log.debug("로그인 합니다");
        HttpHeaders headers = new HttpHeaders();
        ApiResponse apiResponse;

        try {
            UserDTO user = userService.login(userDTO);
            if (user != null) {
                headers = tokenService.createTokenHeaderWithUser(user);
                apiResponse = ApiResponse.createSuccessApiResponseAuto();
            } else {
                throw new BusinessExceptionHandler(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND);
            }
        } catch (BusinessExceptionHandler e) {
            apiResponse = ApiResponse.builder()
                    .result(e.getMessage())
                    .resultCode(Integer.toString(e.getErrorCode().getStatus()))
                    .resultMsg(e.getMessage())
                    .build();
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAuto();
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(apiResponse);
    }

    @RequestMapping("/InfoAgree")
    public ResponseEntity<ApiResponse> infoUseAgree() {



        return ResponseEntity.ok().body(
                ApiResponse.createSuccessApiResponseAuto()
        );
    }


}

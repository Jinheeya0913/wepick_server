package com.twojin.wooritheseday.user.controller;

import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.file.service.FileUserService;
import com.twojin.wooritheseday.auth.service.TokenService;
import com.twojin.wooritheseday.common.utils.ConvertModules;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    FileUserService fileUserService;

    @RequestMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid  UserDTO userDTO) {
        log.debug("로그인 합니다");
        HttpHeaders headers = null;
        ApiResponse apiResponse;

        UserDTO user = userService.login(userDTO);

        try {
            if (user != null) {
                headers = tokenService.createTokenHeaderWithUser(user);
            } else {
                throw new BusinessExceptionHandler(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND);
            }
            JSONObject obj = ConvertModules.dtoToJsonObj(user);

            apiResponse = ApiResponse.createSuccessApiResponseWithObj(obj);

        } catch (BusinessExceptionHandler e) {
            apiResponse = ApiResponse.builder()
                    .result(e.getMessage())
                    .resultCode(e.getErrorCode().toString())
                    .resultMsg(e.getMessage())
                    .build();
        } catch (Exception e) {
            // Todo 임시처리
            throw new BusinessExceptionHandler(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND);
        }


        return ResponseEntity.ok().headers(headers).body(apiResponse);
    }


    @PostMapping("/me")
    public ResponseEntity<ApiResponse> getMe(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessTokenHeader,
                                             @RequestHeader(AuthConstants.REFRESH_HEADER) String refreshTokenHeader) {

        log.debug("[userController] >> getMe :: 내 정보 가져오기");

        ApiResponse apiResponse = null;
        UserDTO userDTO = null;

        // Todo 자동로그인 구현
        String accessToken = TokenUtil.getTokenFromHeader(accessTokenHeader);
        String refreshToken = TokenUtil.getTokenFromHeader(refreshTokenHeader);

        log.debug("autoLogin > accessToken : " + accessToken);
        log.debug("autoLogin > refreshToken : " + refreshToken);

        try {
            if (accessToken.equalsIgnoreCase("") || refreshToken.equalsIgnoreCase("")) {
                throw new BusinessExceptionHandler(ErrorCode.Expired_Token.getMessage(), ErrorCode.Expired_Token);
            }

            String accessUserId = TokenUtil.getUserIdFromToken(accessToken);
            String refreshUserId = TokenUtil.getUserIdFromToken(refreshToken);

            if (!accessUserId.equalsIgnoreCase(refreshUserId)) {
                throw new BusinessExceptionHandler(ErrorCode.Expired_Token.getMessage(), ErrorCode.Expired_Token);
            }

            userDTO = userService.loadMyAccountByUserId(accessUserId);

            JSONObject obj = ConvertModules.dtoToJsonObj(userDTO);

            apiResponse = ApiResponse.createSuccessApiResponseWithObj(obj);
        } catch (BusinessExceptionHandler e) {
            apiResponse = ApiResponse.builder()
                    .result("FAIL")
                    .resultMsg(e.getMessage())
                    .resultCode(Integer.toString(e.getErrorCode().getStatus()))
                    .build();
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAuto();
        }




        return ResponseEntity.ok().body(apiResponse);
    }

    @RequestMapping("/join")
    public ResponseEntity<ApiResponse> join(@RequestBody @Valid UserDTO userDTO) {
        log.debug("조인합니다");

        //Todo : 전화번호 이름이 중복일 경우의 Exception 처리

        UserDTO user = null ;
        ApiResponse apiResponse = null;
        try {
            user = userService.saveUser(userDTO);
            if (user != null) {
                apiResponse = ApiResponse.createSuccessApiResponseAuto();
            } else {
                apiResponse = ApiResponse.builder()
                        .result("FAILED")
                        .resultCode("401")
                        .resultMsg("회원가입 실패하였습니다.")
                        .build();
            }
        } catch (Exception e) {
            apiResponse = ApiResponse.builder()
                    .result("FAILED")
                    .resultCode("401")
                    .resultMsg("회원가입 실패하였습니다.")
                    .build();
        }

        return ResponseEntity.ok().body(apiResponse);
    }




}

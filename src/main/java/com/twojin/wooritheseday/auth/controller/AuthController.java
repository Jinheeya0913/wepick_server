package com.twojin.wooritheseday.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
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


    /**
     *
     * @param accessHeader
     * @param refreshHeader
     *
     *  @return Header  >> accessToken, refreshHeader
     *  @return Body    >> ApiResonse : 유저 정보를 담고 있는 ApiResponse 반환
     *
     * {@code @Description} :
     *
     *
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    @RequestMapping("/validToken")
    public ResponseEntity<ApiResponse> validAuth(@RequestHeader(ACCESS_HEADER) String accessHeader,
                                            @RequestHeader(REFRESH_HEADER) String refreshHeader) throws URISyntaxException, JsonProcessingException {
        log.debug("validToken :: Start");
        String accessToken = "";
        String refreshToken = "";
        String userId = "";
        UserDTO user = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        JSONObject jsonObject = new JSONObject();
        ApiResponse apiResponse = null;

        try {

            // 1. 빈값 체크

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

            // 2. 유효 토큰 검증


            log.debug("validToken :: validAccess Start ");

            boolean validAccess = TokenUtil.isValidTokenWithOutException(accessToken) ;
            boolean validRefresh = TokenUtil.isValidTokenWithOutException(refreshToken) ;

            if (validAccess) { // accessToken이 유효
                log.debug("validToken :: accessToken : true ");

                if (!validRefresh) { // refreshToken 유효하지 않음
                    // accessToken o & refreshToken x => 재로그인
                    log.debug("validToken >> validAccess >> ReLogin");
                    throw new BusinessExceptionHandler(ErrorCode.Expired_Token_Need_Login.getMessage(), ErrorCode.Expired_Token);
                } else {
                    log.debug("validAccessToken :: Valid Pass All");
                }

            } else {
                log.debug("validToken :: accessToken : false");

                if (!validRefresh) {
                    // accessToken x & refreshToken x => 재로그인
                    log.debug("validToken >> validRefresh >> ReLogin");
                    throw new BusinessExceptionHandler(ErrorCode.Expired_Token_Need_Login.getMessage(), ErrorCode.Expired_Token);
                } else {
                    log.debug("validToken >> validRefresh >> new AccessToken");
                    // accessToken x & refreshToken o => accessToken 재발급
                    userId = TokenUtil.getUserIdFromToken(refreshToken);
                    user = userService.loadMyAccountByUserId(userId);
                    accessToken = TokenUtil.generateAccessJwtToken(user);
                }
            }

            jsonObject = getUserDataFromToken(accessToken);
            apiResponse = ApiResponse.createSuccessApiResponseWithObj(jsonObject);

        } catch (BusinessExceptionHandler e) {
            log.error("[validToken] >> catch :: 에러 발생" );
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

    private JSONObject getUserDataFromToken(String token) {
        String userIdFromToken = TokenUtil.getUserIdFromToken(token);
        UserDTO userDTO = userService.loadMyAccountByUserId(userIdFromToken);
        JSONObject jsonObject = ConvertModules.dtoToJsonObj(userDTO);

        return jsonObject;
    }


}

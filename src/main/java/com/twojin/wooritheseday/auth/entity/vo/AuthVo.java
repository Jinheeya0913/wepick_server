package com.twojin.wooritheseday.auth.entity.vo;

import com.twojin.wooritheseday.auth.constant.AuthConstants;
import lombok.Builder;
import lombok.Data;

@Data
public class AuthVo {

    public String REFRESH_TOKEN;
    public String ACCESS_TOKEN;

    @Builder
    public AuthVo(String refreshToken, String accessToken) {
        this.REFRESH_TOKEN = AuthConstants.TOKEN_TYPE +" "+refreshToken;
        this.ACCESS_TOKEN =  AuthConstants.TOKEN_TYPE +" "+accessToken;
    }
}

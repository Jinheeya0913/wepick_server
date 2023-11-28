package com.twojin.wooritheseday.auth.service;

import com.twojin.wooritheseday.auth.entity.RefreshTokenInfoEntity;
import com.twojin.wooritheseday.user.entity.UserDTO;
import org.springframework.http.HttpHeaders;

public interface TokenService {

    HttpHeaders createTokenHeaderWithUser(UserDTO userDTO);

    RefreshTokenInfoEntity insertNewUserToken(RefreshTokenInfoEntity refreshEntity);
}

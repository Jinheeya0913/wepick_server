package com.twojin.wooritheseday.auth.service.impl;

import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.auth.entity.RefreshTokenInfoEntity;
import com.twojin.wooritheseday.auth.repository.AuthRepository;
import com.twojin.wooritheseday.auth.service.TokenService;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.user.entity.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("tokenService")
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Autowired
    AuthRepository authRepository;


    @Override
    @Transactional
    public HttpHeaders createTokenHeaderWithUser(UserDTO userDTO) {
        HttpHeaders headers = new HttpHeaders();

        String accessToken =TokenUtil.generateAccessJwtToken(userDTO);
        String refreshToken = TokenUtil.generateRefreshJwtToken(userDTO);

        log.debug("createTokenHeaderWithUser > accessToken : " + accessToken);
        log.debug("createTokenHeaderWithUser > refreshToken : " + refreshToken);

        RefreshTokenInfoEntity refreshTokenInfo = authRepository.findByUserId(userDTO.getUserId());

        if (refreshTokenInfo != null) {
            refreshToken = TokenUtil.generateRefreshJwtToken(userDTO);
            log.debug("createTokenHeaderWithUser > refreshToken 갱신!: " + refreshToken);
            refreshTokenInfo.setRefreshToken(refreshToken);
        } else {
            log.debug("createTokenHeaderWithUser > refreshToken 생성!: " + refreshToken);
            refreshTokenInfo = insertNewUserToken(
                    RefreshTokenInfoEntity.builder()
                            .refreshToken(refreshToken)
                            .userId(userDTO.getUserId())
                            .build()
            );
        }

        log.debug("createTokenHeaderWithUser > resultEntity : " + refreshTokenInfo.toString());

        headers.set(AuthConstants.ACCESS_HEADER, AuthConstants.TOKEN_TYPE+" "+accessToken);
        headers.set(AuthConstants.REFRESH_HEADER, AuthConstants.TOKEN_TYPE+" "+refreshToken);

        return headers;
    }

    @Override
    public RefreshTokenInfoEntity insertNewUserToken(RefreshTokenInfoEntity refreshEntity) {
        log.debug("insertNewUserToken > FIND RefreshToken START");
        RefreshTokenInfoEntity refreshTokenInfoEntity = authRepository.save(refreshEntity);
        log.debug("insertNewUserToken > FIND RefreshToken END");
        return refreshTokenInfoEntity;
    }

}

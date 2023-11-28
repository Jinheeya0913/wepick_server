package com.twojin.wooritheseday.config.provider;

import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.service.impl.UserServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @NonNull
    private BCryptPasswordEncoder passwordEncoder;

    // Todo : 해당 어토테이션이 뭔지 공부할 것
    @Resource
    private UserServiceImpl userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("2. CustomAuthenticationProvider");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // Filter에서 생성한 토큰으로부터 아이디와 비밀번호 조회

        String userId = token.getName();
        String userPw = (String) token.getCredentials();

        UserDTO user = (UserDTO) userService.loadMyAccountByUserId(userId);

        if (!(user.getUserPw().equalsIgnoreCase(userPw) && user.getUserPw().equalsIgnoreCase(userPw))) {
            throw new BadCredentialsException(user.getUserNm() + "Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(user, userPw, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

package com.twojin.wooritheseday.user.service.impl;

import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.auth.service.TokenService;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.repository.UserRepository;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.transaction.Transactional;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO login(UserDTO user) {

        //Todo : 로그인 할 때 비밀번호 빼고 보내기

        log.debug("userService :: login Start :: ");
        log.debug("userService :: userId :: " + user.getUserId());
        UserDTO result = null;

        try {
            result = userRepository.findByUserId(user.getUserId())
                    .orElseThrow(()
                            -> new BusinessExceptionHandler(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
            log.debug("result.toString " + result.toString());
            if (result != null) {
                log.error("비밀번호 확인 1 : "+ user.getUserPw());
                log.error("비밀번호 확인 2 : "+ result.getUserPw());
                if (!result.getUserPw().equals(user.getUserPw())) {
                    throw new BusinessExceptionHandler(ErrorCode.USER_PASSWORD_NOT_CORRECT.getMessage(), ErrorCode.USER_PASSWORD_NOT_CORRECT);
                }
            } else {
                throw new BusinessExceptionHandler(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        log.debug("userService :: login End :: ");
        return result;
    }

    @Override
    public UserDTO findMyAccount(UserDTO user) {
        UserDTO result = userRepository.findByUserNmAndUserPhoneNum(user.getUserNm(), user.getUserPhoneNum())
                .orElseThrow(IllegalArgumentException::new);

        return result;
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        log.debug("UserServiceImpl :: saveUser 수행");
        UserDTO result = userRepository.save(user);
        return result;
    }

    @Override
    public UserDTO loadMyAccountByUserId(String userId) {
        log.debug("[loadMyAccountByUserId] >> ID로 계정 조회");
        UserDTO result = userRepository.findByUserId(userId)
                .orElseThrow(()->new BusinessExceptionHandler(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));
        return result;
    }

    @Override
    public boolean agreeUserInfo(UserDTO userDTO) {
        return false;
    }

    @Override
    @Transactional
    public UserDTO updateUserImgUrl(String fileName , String userId) {
        log.debug("[userService] >> updateUserImgUrl ");

        UserDTO userDTO = userRepository.findByUserId(userId).orElse(null);

        if(userDTO!=null) {
            userDTO.setUserImgUrl(fileName);
            userDTO = userRepository.save(userDTO);
        }

        return userDTO;
    }

    @Override
    public UserDTO selectUserByUserId(String userId) {
        log.debug("[selectUserByUserId] >> START");
        UserDTO result = userRepository.findByUserId(userId)
                .orElseThrow(()->new BusinessExceptionHandler(ErrorCode.USER_NOT_SELECTED.getMessage(), ErrorCode.USER_NOT_SELECTED));


        log.debug("[selectUserByUserId] >> PERIOD");
        return UserDTO.builder()
                .userId(result.getUserId())
                .userEmail(result.getUserEmail())
                .userPhoneNum(result.getUserPhoneNum())
                .userNm(result.getUserNm())
                .userImgUrl(result.getUserImgUrl())
                .build();
    }

    public  String getUserNmByUserId(String userId) {
        log.debug("[UserServiceImpl] >> getUserNmByUserId :: START");
        String userNm= userRepository.findUserNmByUserId(userId).orElse(null);
        return userNm;
    }

    /**
     *
     * @param userId
     * @return userId, userEmail
     *
     * 간단한 정보만 전달
     */
    @Override
    public UserDTO selectSimpleUserInfoByUserId(String userId) {
        log.debug("[UserServiceImpl] >> selectSimpleUserInfoByUserId :: START");

        UserDTO userDTO=userRepository.findByUserId(userId)
                .orElseThrow(()->new BusinessExceptionHandler(ErrorCode.USER_NOT_SELECTED.getMessage(), ErrorCode.USER_NOT_SELECTED));



        return UserDTO.builder()
                .userId(userDTO.getUserId())
                .userImgUrl(userDTO.getUserImgUrl())
                .build();
    }

    public String getEncodedPassword(String userPw) {
        return new String(Base64Utils.encode(userPw.getBytes()));
    }

    public String getDecodedPassword(String userPw) {
        return new String(Base64Utils.decode(userPw.getBytes()));
    }





}

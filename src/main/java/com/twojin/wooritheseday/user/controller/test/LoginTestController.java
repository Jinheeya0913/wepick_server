package com.twojin.wooritheseday.user.controller.test;

import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/testLogin")
public class LoginTestController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/login")
    public ResponseEntity<Integer> test(@RequestBody @Valid UserDTO userDTO) {
        log.debug("로그인 합니다");


        UserDTO user = userService.login(userDTO);


        if (!"taran0913".equals(user.getUserId())) {
            log.debug("에러 발생 합니다");
            throw new BusinessExceptionHandler(ErrorCode.BUSINESS_EXCEPTION_ERROR.getMessage(), ErrorCode.BUSINESS_EXCEPTION_ERROR);
        }
        return ResponseEntity.ok().body(1);
    }

    @RequestMapping("/join")
    public ResponseEntity<UserDTO> join(@RequestBody @Valid UserDTO userDTO) {
        log.debug("조인합니다");
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        userDTO.setUserPw(encodedPassword);

        UserDTO user = userService.saveUser(userDTO);
        return ResponseEntity.ok().body(user);
    }




}

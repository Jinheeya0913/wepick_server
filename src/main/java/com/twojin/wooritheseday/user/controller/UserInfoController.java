package com.twojin.wooritheseday.user.controller;

import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.JsonConvertModules;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userInfo")
@Slf4j
public class UserInfoController {

    @Autowired
    UserService userService;


    @RequestMapping("getUserInfo")
    public ResponseEntity<ApiResponse> getUserInfo(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader, @RequestBody UserDTO userMap) {
        log.debug("[UserInfoController] >> getUserInfo :: START");

        log.debug("[UserInfoController] >> getUserInfo :: userMap : " + userMap.toString());

        ApiResponse apiResponse = null;
        try {
            UserDTO userDTO = userService.selectSimpleUserInfoByUserId(userMap.getUserId());

            log.debug("[UserInfoController] >> getUserInfo :: userDto : " + userDTO.toString());
            apiResponse = ApiResponse.createSuccessApiResponseWithObj(JsonConvertModules.dtoToJsonObj(userDTO));
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }
        return ResponseEntity.ok(apiResponse);
    }


}

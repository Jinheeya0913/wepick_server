package com.twojin.wooritheseday.file.controller;


import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.ConvertModules;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.file.dto.vo.FileVo;
import com.twojin.wooritheseday.file.service.FileUserService;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;

@Controller
@RequestMapping("/file/me")
@Slf4j
public class FileUserController {

    @Autowired
    FileUserService fileUserService;

    @Autowired
    UserService userService;

    // setProfileImg
    // 프로필 사진 설정 만들기
    @RequestMapping("/setProfileImg")
    public ResponseEntity<ApiResponse> setProfileImage(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessTokenHeader,
                                                       @RequestParam(value = "image",required = false) MultipartFile image) {

        log.debug("[setProfileImage] >> 시작합니다 ");
        ApiResponse apiResponse;

        String tokenFromHeader = TokenUtil.getTokenFromHeader(accessTokenHeader);
        String userId = TokenUtil.getUserIdFromToken(tokenFromHeader);
        UserDTO user = null;
        JSONObject jsonObject = new JSONObject();

        try {
            String fileName = fileUserService.uploadProfileImage(image, userId); // uuid 반환
            user = userService.updateUserImgUrl(fileName, userId);

            if (user != null) {
                jsonObject = ConvertModules.dtoToJsonObj(user);
            } else {
                throw new BusinessExceptionHandler(ErrorCode.USER_PROFILE_IMG_UPLOAD.getMessage(), ErrorCode.USER_PROFILE_IMG_UPLOAD);
            }
            apiResponse = ApiResponse.createSuccessApiResponseWithObj(jsonObject);

        } catch (BusinessExceptionHandler e) {
            apiResponse = ApiResponse.
                    builder()
                    .resultMsg(e.getMessage())
                    .resultCode(e.getErrorCode().toString())
                    .result(e.getMessage())
                    .build();
        } catch (Exception e) {
            log.debug("[userController] >> setProfileImage :: Exception 실패");
            log.error(e.getClass().getName());
            log.error(e.getMessage());
            e.printStackTrace();


            apiResponse = ApiResponse.createFailApiResponseAuto();
        }

        return ResponseEntity.ok().body(apiResponse);
    }

    //getProfileImg
    // 프로필 사진 얻기
    @RequestMapping("/getProfileImg")
    public ResponseEntity<Resource> getProfileImage(@RequestParam String pid) {

        log.debug("[getProfileImage] >> 이미지 가져오기 시작");
        log.debug("[getProfileImage] >> pid : " + pid);

        HttpHeaders headers = new HttpHeaders();
        Resource resource = null;


        String userId = "taran0913";
        try {
            FileVo vo = fileUserService.downloadProfileImage(pid);
            resource = vo.getResource();

            if (resource != null) {
                log.debug("[getProfileImage] >> 성공 1");
                log.debug("[getProfileImage] >> 성공 1 : Files.probeContentType(vo.getPath()) : " + Files.probeContentType(vo.getPath()) );
                headers.add("Content-Type", Files.probeContentType(vo.getPath()));
            } else {
                log.error("[getProfileImage] >> 실패 1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("[getProfileImage] >> 이미지 가져오기 실패 1");
        }

        log.info("[getProfileImage] >> 완료");

        return ResponseEntity.ok().headers(headers).body(resource);
    }

}

package com.twojin.wooritheseday.file.controller;


import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.ConvertModules;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.file.service.FileUserService;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    @RequestMapping("/me/getProfileImg")
    public ResponseEntity<ApiResponse> getProfileImage(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessTokenHeader) {
        ApiResponse apiResponse = null;
        UserDTO userDTO = null;
        String fileName = "";

        String userId = TokenUtil.getUserIdFromHeader(accessTokenHeader);

        try {
            userDTO = userService.loadMyAccountByUserId(userId);
            apiResponse = ApiResponse.createSuccessApiResponseWithObj(ConvertModules.dtoToJsonObj(userDTO));
            fileName = userDTO.getUserImgUrl();
            fileUserService.downloadProfileImage(fileName);


        } catch (BusinessExceptionHandler e) {
            apiResponse = ApiResponse.builder()
                    .resultMsg(e.getMessage())
                    .resultCode(Integer.toString(e.getErrorCode().getStatus()))
                    .build();
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAuto();

        }


        return ResponseEntity.ok().body(apiResponse);
    }

}

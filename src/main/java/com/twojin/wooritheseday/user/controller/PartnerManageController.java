package com.twojin.wooritheseday.user.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.ConvertModules;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.user.entity.PartnerMaterDTO;
import com.twojin.wooritheseday.user.entity.PartnerTempQueDTO;
import com.twojin.wooritheseday.user.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.service.PartnerService;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/partner")
@Controller
@Slf4j
public class PartnerManageController {

    @Autowired
    PartnerService partnerService;

    @Autowired
    UserService userService;

    // Todo : 내 파트너 유무 조회
    // partner_m 테이블을 확인했을 때 조회된 파트너의 정보가 없으면 null
    @RequestMapping("/getMyPartner")
    public ResponseEntity<ApiResponse> getMyPartnerInfo(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader) {
        log.debug("[getMyPartnerInfo] >> START");

        ApiResponse apiResponse = null;
        UserDTO user = null;
        PartnerMaterDTO partner = null;

        // 1. 우선 내 정보 조회
        String userId = TokenUtil.getUserIdFromHeader(accessHeader);

        try {
            partner= partnerService.getPartnerInfoByUserId(userId);

            if (partner != null) { // 조회 결과 있음
                apiResponse = ApiResponse.createSuccessApiResponseWithObj(ConvertModules.dtoToJsonObj(partner));
            } else { // 조회 결과 없음
                log.error("[getMyPartnerInfo] >> 조회 결과 없음");
                throw new BusinessExceptionHandler(ErrorCode.PARTNER_NOT_EXIST.getMessage(), ErrorCode.PARTNER_NOT_EXIST);
            }
        } catch (Exception e) {
            log.error("[getMyPartnerInfo] >> Error ");
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }
        log.debug("[getMyPartnerInfo] >> 조회 성공");
        return ResponseEntity.ok().body(apiResponse);
    }
    // Todo : Partner 1. 내 파트너 코드 갖기
    @RequestMapping("/getMyPartnerRegCode")
    public ResponseEntity<ApiResponse> getMySearchCode(@RequestHeader(value = AuthConstants.ACCESS_HEADER) String accessHeader) {
        JSONObject resultObj = new JSONObject();
        ApiResponse apiResponse = null;

        String userIdFromHeader = TokenUtil.getUserIdFromHeader(accessHeader);

        try {
            PartnerTempQueDTO partnerTempQueDTO = partnerService.registPartnerQueue(userIdFromHeader);
            if (partnerTempQueDTO == null) {
                throw new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_QUEUE_FAIL.getMessage(), ErrorCode.PARTNER_REGIST_QUEUE_FAIL);
            }
            resultObj = ConvertModules.dtoToJsonObj(partnerTempQueDTO);
            apiResponse = ApiResponse.createSuccessApiResponseWithObj(resultObj);
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }
        return ResponseEntity.ok().body(apiResponse);
    }

    // Todo : 파트너 찾기
    @RequestMapping("/searchPartnerWithCode")
    public ResponseEntity<ApiResponse> searchPartnerWithCode(@RequestBody PartnerTempQueDTO partner) throws ParseException, JsonProcessingException {
        String ptRegCd= partner.getPtTempRegCd();
        JSONObject result = new JSONObject();

        if (ptRegCd != null) {
            log.debug("searchPartnerWithCode >> ptRegCd >> " + ptRegCd);
            UserDTO userDTO = partnerService.selectPartnerQueueWithPtRegCd(ptRegCd);
            log.debug("searchPartnerWithCode >> userDTO >> " + userDTO.toString());
            result = ConvertModules.dtoToJsonObj(userDTO);
        } else {
            throw new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_NOT_FOUND.getMessage(), ErrorCode.PARTNER_REGIST_NOT_FOUND);
        }


        return ResponseEntity.ok().body(new ApiResponse("SUCCESS" , "101", "파트너 조회 완료", result));
    }

    // Todo : 파트너 요청 보내기
    @RequestMapping("/requestPartner")
    public ResponseEntity<ApiResponse> requestPartner(@RequestBody PartnerRequestQueueDTO dto, @RequestParam String ptRegCd) {

        JSONObject result = new JSONObject();

        JSONObject result1;


        UserDTO partner =partnerService.selectPartnerQueueWithPtRegCd(ptRegCd);

        dto.setPtRegUserId(partner.getUserId());
        PartnerRequestQueueDTO res = partnerService.requestPartner(dto);

        if (res != null) {
            result = ConvertModules.dtoToJsonObj(res);
        }
        return ResponseEntity.ok().body(ApiResponse.createSuccessApiResponseWithObj(result));
    }



    // Todo : 내 파트너 요청 조회하기

    // Todo : 파트너 요청 수락하기

    // Todo : 파트너 요청 거절하기

    // Todo : 파트너 삭제하기


}

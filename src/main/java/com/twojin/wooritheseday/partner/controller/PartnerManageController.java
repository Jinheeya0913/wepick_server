package com.twojin.wooritheseday.partner.controller;


import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.ConvertModules;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.partner.entity.PartnerMaterDTO;
import com.twojin.wooritheseday.partner.entity.PartnerTempQueDTO;
import com.twojin.wooritheseday.partner.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.partner.util.PartnerUtils;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.partner.service.PartnerService;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    // Todo : Partner 1. 파트너
    @RequestMapping("/getMyPartnerRegCode")
    public ResponseEntity<ApiResponse> getMySearchCode(@RequestHeader(value = AuthConstants.ACCESS_HEADER) String accessHeader) {
        JSONObject resultObj = new JSONObject();
        ApiResponse apiResponse = null;

        String userIdFromHeader = TokenUtil.getUserIdFromHeader(accessHeader);

        try {
            PartnerTempQueDTO partnerTempQueDTO = partnerService.createPartnerRegCd(userIdFromHeader);
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

    /**
     *
     * @param accessHeader
     * @param partner
     * @return          resultMap.put("partnerInfo", partnerInfo); // 파트너 정보
     *                  resultMap.put("reqQueInfo", reqQue); // 요청 상황
     */
    // Todo : 파트너 찾기
    @RequestMapping("/searchPartnerWithCode")
    public ResponseEntity<ApiResponse> searchPartnerWithCode(@RequestHeader(value = AuthConstants.ACCESS_HEADER) String accessHeader,
                                                             @RequestBody PartnerTempQueDTO partner) {
        log.debug("[searchPartnerWithCode] >> START");

        String ptRegCd= partner.getPtTempRegCd();
        String userId = TokenUtil.getUserIdFromHeader(accessHeader);

        log.debug("[searchPartnerWithCode] >> ptRegCd :: " + ptRegCd);
        log.debug("[searchPartnerWithCode] >> userId  :: " + userId);

        ApiResponse apiResponse = null;
        JSONObject result = new JSONObject();
        try {
            if (ptRegCd != null) {

                // 1. 생성 번호로 유저 정보 찾기
                log.debug("searchPartnerWithCode >> ptRegCd >> " + ptRegCd);
                PartnerTempQueDTO tempQue = partnerService.selectPartnerQueueWithPtRegCd(ptRegCd);
                log.debug("searchPartnerWithCode >> PartnerTempQueDTO >> " + tempQue.toString());

                String selectedUserId = tempQue.getPtTempUserId();


                // 2, 자기 자신의 파트너 요청 코드를 입력할 경우
                if (selectedUserId.equals(userId)) {
                    throw new BusinessExceptionHandler(ErrorCode.PARTNER_SELF.getMessage(), ErrorCode.PARTNER_SELF);
                }

                UserDTO partnerInfo = userService.selectUserByUserId(selectedUserId);

                // 3. 검색한 파트너에 대한 요청 상황 검색
                PartnerRequestQueueDTO reqQue=partnerService.selectRequestStatusWithRequesterId(tempQue, userId);

                Map<String, Object> resultMap = new HashMap<>();

                resultMap.put("partnerInfo", partnerInfo); // 파트너 정보
                resultMap.put("reqQueInfo", reqQue); // 요청 상황

                result = ConvertModules.dtoToJsonObj(resultMap);


            } else {
                throw new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_NOT_FOUND.getMessage(), ErrorCode.PARTNER_REGIST_NOT_FOUND);
            }
            apiResponse = ApiResponse.createSuccessApiResponseWithObj(result);

        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }

        return ResponseEntity.ok().body(apiResponse);
    }

    // Todo : 파트너 요청 보내기
    @RequestMapping("/requestPartner")
    public ResponseEntity<ApiResponse> requestPartner(@RequestBody PartnerRequestQueueDTO dto, @RequestParam String ptRegCd) {

        JSONObject result = new JSONObject();

        JSONObject result1;


//        UserDTO partner =partnerService.selectPartnerQueueWithPtRegCd(ptRegCd);

//        dto.setPtRegUserId(partner.getUserId());
//        PartnerRequestQueueDTO res = partnerService.requestPartner(dto);
//
//        if (res != null) {
//            result = ConvertModules.dtoToJsonObj(res);
//        }
        return ResponseEntity.ok().body(ApiResponse.createSuccessApiResponseWithObj(null));
    }



    // Todo : 내 파트너 요청 조회하기

    // Todo : 파트너 요청 수락하기

    // Todo : 파트너 요청 거절하기

    // Todo : 파트너 삭제하기


}

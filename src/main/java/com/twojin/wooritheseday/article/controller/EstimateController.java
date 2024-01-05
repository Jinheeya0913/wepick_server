package com.twojin.wooritheseday.article.controller;

//import com.twojin.wooritheseday.article.service.EstimateService;
import com.twojin.wooritheseday.article.entity.EstimateHallDTO;
import com.twojin.wooritheseday.article.service.EstimateService;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
//import com.twojin.wooritheseday.article.entity.EstimateHallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/estimate")
@RestController
public class EstimateController {

    @Autowired
    EstimateService estimateService;

    @RequestMapping("writeHall")
    public ResponseEntity<ApiResponse> writeHallEstimate(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader, @RequestBody EstimateHallDTO hallEstimateInfo ) {


        ApiResponse apiResponse = null;

        String userId=TokenUtil.getUserIdFromHeader(accessHeader);

        try {
            if (hallEstimateInfo != null) {
                hallEstimateInfo.setWriterId(userId);
                boolean result = estimateService.writeHallEstimate(hallEstimateInfo);
                if (result) {
                    apiResponse = ApiResponse.createSuccessApiResponseAuto();
                }
            } else {
                throw new BusinessExceptionHandler(ErrorCode.ESTIMATE_WRONG_FORM.getMessage(), ErrorCode.ESTIMATE_WRONG_FORM);
            }
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }

        return ResponseEntity.ok(apiResponse);
    }


}

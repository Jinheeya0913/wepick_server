package com.twojin.wooritheseday.article.controller;

//import com.twojin.wooritheseday.article.service.EstimateService;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
//import com.twojin.wooritheseday.article.entity.EstimateHallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/review")
@RestController
public class EstimateController {

//    @Autowired
//    EstimateService estimateService;
//
//    @RequestMapping("writeReview")
//    public ResponseEntity<ApiResponse> writeHallEstimate(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader, @RequestBody EstimateHallDTO hallEstimateVO) {
//
//        ApiResponse apiResponse = null;
//
//        try {
//            if (hallEstimateVO != null) {
//                estimateService.writeHallEstimate(hallEstimateVO);
//
//            } else {
//                throw new BusinessExceptionHandler(ErrorCode.ESTIMATE_WRONG_FORM.getMessage(), ErrorCode.ESTIMATE_WRONG_FORM);
//            }
//        } catch (Exception e) {
//            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
//        }
//
//        return ResponseEntity.ok(apiResponse);
//    }
//

}

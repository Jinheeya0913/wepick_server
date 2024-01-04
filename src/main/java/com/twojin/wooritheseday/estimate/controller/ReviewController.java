package com.twojin.wooritheseday.estimate.controller;

import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.estimate.vo.HallEstimateVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/review")
@RestController
public class ReviewController {

    @RequestMapping("writeReview")
    public ResponseEntity<ApiResponse> writeReview(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader, @RequestBody HallEstimateVO hallEstimateVO) {




        return ResponseEntity.ok(ApiResponse.createFailApiResponseAuto());
    }

}

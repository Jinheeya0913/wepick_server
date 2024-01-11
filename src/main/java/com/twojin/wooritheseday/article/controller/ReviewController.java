package com.twojin.wooritheseday.article.controller;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.service.ReviewService;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping("writeHallReview")
    public ResponseEntity<ApiResponse> writeReview(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader,
//                                                   @RequestPart("data") Map<String, Object>  data,
                                                   @RequestPart("images")  List<MultipartFile> images
                                                    ) {
        log.debug("[ReviewController] >> writeReview :: START");
        log.debug("[ReviewController] >> writeReview :: images.length : " + images.size());


//        log.debug("[ReviewController] >> writeReview :: data: " + data.toString());
        ApiResponse apiResponse = null;

        String userId=TokenUtil.getUserIdFromHeader(accessHeader);
//        data.setUserId(userId);
//
//        try {
//            boolean result = reviewService.writeReviewHall(data);
//            if(result) {
//                apiResponse = ApiResponse.createSuccessApiResponseAuto();
//            } else {
//                throw new BusinessExceptionHandler(ErrorCode.REVIEW_REGIST_FAIL.getMessage(), ErrorCode.REVIEW_REGIST_FAIL);
//            }
//        } catch (Exception e) {
//            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
//        }
        return ResponseEntity.ok(ApiResponse.createSuccessApiResponseAuto());
    }

}

package com.twojin.wooritheseday.article.controller;

import com.twojin.wooritheseday.article.entity.EstimateHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.service.ReviewService;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.ConvertModules;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping("writeHallReview")
    public ResponseEntity<ApiResponse> writeReview(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader, @RequestBody ReviewHallDTO reviewHallDTO) {
        log.debug("[ReviewController] >> writeReview :: START");
        ApiResponse apiResponse = null;

        String userId=TokenUtil.getUserIdFromHeader(accessHeader);
        reviewHallDTO.setUserId(userId);

        try {
            boolean result = reviewService.writeReviewArticle(reviewHallDTO);
            apiResponse = ApiResponse.createSuccessApiResponseAuto();
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }
        return ResponseEntity.ok(apiResponse);
    }

}

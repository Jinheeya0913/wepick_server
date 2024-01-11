package com.twojin.wooritheseday.article.controller;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import com.twojin.wooritheseday.article.service.ReviewService;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.JsonConvertModules;
import com.twojin.wooritheseday.common.utils.TokenUtil;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import com.twojin.wooritheseday.product.vo.ReviewCommonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = "writeReviewHall", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> writeReview(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader
            , @RequestParam("data") String  data, @RequestParam("images") List<MultipartFile> images) {

        log.debug("[ReviewController] >> writeReview :: START");
        log.debug("[ReviewController] >> writeReview :: data" + data.toString());
        ApiResponse apiResponse = null;
        String userId = TokenUtil.getUserIdFromHeader(accessHeader);


        try {
            ReviewMasterDTO reviewMasterDTO = reviewService.writeReviewHall(data, images, userId);
            apiResponse = ApiResponse.createSuccessApiResponseWithObj(reviewMasterDTO);
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }

        return ResponseEntity.ok(apiResponse);
    }

}

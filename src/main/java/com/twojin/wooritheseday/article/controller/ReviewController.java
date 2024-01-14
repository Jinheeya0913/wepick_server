package com.twojin.wooritheseday.article.controller;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import com.twojin.wooritheseday.article.service.ReviewService;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.common.enums.ProductClass;
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
import java.util.Map;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    /**
     *
     * @param accessHeader
     * @return
     */
    @RequestMapping("selectReviewList")
    public ResponseEntity<ApiResponse> selectReviewList(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader , @RequestBody Map<String,String>  productMap) {
        log.debug("[ReviewController] >> selectReviewList :: START");
        log.debug("[ReviewController] >> selectReviewList :: productClass :  " + productMap.get("productClass"));

        ApiResponse apiResponse = null;
        ProductClass product =ProductClass.valueOf(productMap.get("productClass").toUpperCase());

        try {
            List<ReviewMasterDTO> reviewMasterList = reviewService.selectReviewMasterList(product);
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }


        try {

            reviewService.selectReviewMasterList(product);
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }

        return ResponseEntity.ok(ApiResponse.createSuccessApiResponseAuto());
    }

    @RequestMapping(value = "writeReviewHall", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> writeReview(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader
            , @RequestParam("data") String data, @RequestParam("images") List<MultipartFile> images) {

        log.debug("[ReviewController] >> writeReview :: START");
        log.debug("[ReviewController] >> writeReview :: data" + data.toString());
        ApiResponse apiResponse = null;
        String userId = TokenUtil.getUserIdFromHeader(accessHeader);

        try {

            ReviewMasterDTO reviewMasterDTO = reviewService.writeReviewHall(data, images, userId);
            if (reviewMasterDTO != null) {
                log.debug("[ReviewController] >> writeReview :: reviewMasterDTO" + reviewMasterDTO.toString());
                apiResponse = ApiResponse.createSuccessApiResponseWithObj(reviewMasterDTO);
            } else {
                apiResponse = ApiResponse.createFailApiResponseAuto();
            }
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }
        return ResponseEntity.ok(apiResponse);
    }

}

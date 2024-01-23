package com.twojin.wooritheseday.article.controller;

import com.twojin.wooritheseday.article.vo.ReviewListRespVO;
import org.apache.commons.lang3.StringUtils;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
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

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    /**
     * @param
     * @return
     */
    @RequestMapping("selectReviewList")
    public ResponseEntity<ApiResponse> selectReviewList(@RequestBody Map<String, String> requestMap) {


        log.debug("[ReviewController] >> selectReviewList :: START");
        log.debug("[ReviewController] >> selectReviewList :: productClass :  " + requestMap.get("productClass"));


        ApiResponse apiResponse = null;
        ReviewListRespVO reviewListRespVO = new ReviewListRespVO();

        String productClass = requestMap.get("productClass");


        try {
            // 비어있으면 오류
            if (StringUtils.isEmpty(productClass)) {
                throw new BusinessExceptionHandler(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage(), ErrorCode.MISSING_REQUEST_PARAMETER_ERROR);
            }

            ReviewListRespVO respVO = reviewService.selectReviewListByProductClass(productClass);

            apiResponse = ApiResponse.createSuccessApiResponseWithObj(respVO);
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }

        return ResponseEntity.ok(apiResponse);
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


    /**
     *
     * @param accessHeader
     * @param reviewInfo
     * @return resultData : True 증가 / False 감소
     */
    @RequestMapping("upDownReviewLike")
    public ResponseEntity<ApiResponse> upDownReviewLike(@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader
            , @RequestBody ReviewMasterDTO reviewInfo) {
        log.debug("[ReviewController] >> upDownReviewLike :: START");
        
        String userId = TokenUtil.getUserIdFromHeader(accessHeader);
        ApiResponse apiResponse = null;


        try {
            boolean result = reviewService.thumbUpDownReviewLike(reviewInfo, userId);
            if (result) {
                apiResponse = ApiResponse.createSuccessApiResponseWithObj(true);
            } else {
                apiResponse = ApiResponse.createSuccessApiResponseWithObj(false);
            }

        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }

        return ResponseEntity.ok(apiResponse);
    }

}

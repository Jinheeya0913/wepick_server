package com.twojin.wooritheseday.article.controller;

import com.twojin.wooritheseday.article.entity.EstimateHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.auth.constant.AuthConstants;
import com.twojin.wooritheseday.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

       @RequestMapping("writeHallReview")
        public ResponseEntity<ApiResponse> writeReview (@RequestHeader(AuthConstants.ACCESS_HEADER) String accessHeader, @RequestBody ReviewHallDTO reviewHallDTO){
               ApiResponse apiResponse = null;

               try {

               } catch (Exception e) {
                   apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
               }
               return ResponseEntity.ok(ApiResponse.createFailApiResponseAuto());
       }

}

package com.twojin.wooritheseday.file.controller;

import com.twojin.wooritheseday.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/file/review")
@Slf4j
public class FileReviewController {


    public ResponseEntity<ApiResponse> getReviewThumbnail(@RequestBody Map<String,String> requestMap) {
        ApiResponse apiResponse = null;

        String reviewClass = requestMap.get("reviewClass");
        String fileName = requestMap.get("fileName");


        try {

            apiResponse = ApiResponse.createSuccessApiResponseAuto();
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }


        return ResponseEntity.ok(apiResponse);


    }



}

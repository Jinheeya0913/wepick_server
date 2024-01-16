package com.twojin.wooritheseday.product.controller;

import com.twojin.wooritheseday.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/product/hall")
public class HallController {


    @RequestMapping("getHallList")
    public ResponseEntity<ApiResponse> getHallList() {

        ApiResponse apiResponse = null;

        try {

        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }

        return ResponseEntity.ok(ApiResponse.createFailApiResponseAuto());
    }
}

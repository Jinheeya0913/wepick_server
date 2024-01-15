package com.twojin.wooritheseday.product.controller;


import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.product.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/place")
@Slf4j
public class PlaceController {

    @Autowired
    PlaceService placeService;


        @RequestMapping("getPlaceList")
            public ResponseEntity<ApiResponse> getPlaceList() {

                ApiResponse apiResponse = null;

                try {

                } catch (Exception e) {
                    apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
                }

                return ResponseEntity.ok(ApiResponse.createFailApiResponseAuto());
            }


}

package com.twojin.wooritheseday.product.controller;


import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.JsonConvertModules;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import com.twojin.wooritheseday.product.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product/place")
@Slf4j
public class PlaceController {

    @Autowired
    PlaceService placeService;

    @RequestMapping("getPlaceList")
    public ResponseEntity<ApiResponse> getPlaceList() {
        log.debug("[PlaceController] >> getPlaceList :: START");

        ApiResponse apiResponse = null;

        try {

            List<PlaceDTO> placeDTOList = placeService.selectPlaceDTOList();
            if (placeDTOList.isEmpty()) {
                log.debug("[PlaceController] >> getPlaceList :: ERROR");
                throw new BusinessExceptionHandler(ErrorCode.PRODUCT_SELECT_FAIL.getMessage(), ErrorCode.PRODUCT_SELECT_FAIL);
            }
            apiResponse = ApiResponse.createSuccessApiResponseWithObj(JsonConvertModules.listToJsonArray(placeDTOList));

        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }
        return ResponseEntity.ok(apiResponse);
    }


}

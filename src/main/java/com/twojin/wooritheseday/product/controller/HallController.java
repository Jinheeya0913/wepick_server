package com.twojin.wooritheseday.product.controller;

import com.twojin.wooritheseday.common.response.ApiResponse;
import com.twojin.wooritheseday.common.utils.JsonConvertModules;
import com.twojin.wooritheseday.product.entity.HallDTO;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import com.twojin.wooritheseday.product.service.HallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/product/hall")
public class HallController {

    @Autowired
    HallService hallService;


    @RequestMapping("getHallListByProductCd")
    public ResponseEntity<ApiResponse> getHallListByProductCd(@RequestBody String placeCd) {
        log.debug("[HallController] >> getHallListByProductCd :: START");
        log.debug("[HallController] >> getHallListByProductCd :: placeDTO : " + placeCd);

        ApiResponse apiResponse = null;

        try {
            List<HallDTO> hallDTOList = hallService.selectPlaceDTOListByPlaceCd((long) Integer.parseInt(placeCd));
            apiResponse = ApiResponse.createSuccessApiResponseWithObj(JsonConvertModules.listToJsonArray(hallDTOList));
        } catch (Exception e) {
            apiResponse = ApiResponse.createFailApiResponseAutoWithException(e);
        }

        log.debug("[HallController] >> getHallListByProductCd :: END");
        return ResponseEntity.ok(apiResponse);
    }
}

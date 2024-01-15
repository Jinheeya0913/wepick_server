package com.twojin.wooritheseday.product.service.impl;

import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import com.twojin.wooritheseday.product.repository.PlaceRepository;
import com.twojin.wooritheseday.product.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("placeService")
@Slf4j
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    PlaceRepository placeRepository;


    @Override
    public List<PlaceDTO> selectPlaceDTOList() {
        log.debug("[PlaceServiceImpl] >> selectPlaceDTOList :: START");

        List<PlaceDTO> placeDTOList = placeRepository.findAllByUseAt(true)
                .orElseThrow(()->new BusinessExceptionHandler(ErrorCode.PRODUCT_SELECT_FAIL.getMessage(), ErrorCode.PRODUCT_SELECT_FAIL));

        return placeDTOList;
    }
}

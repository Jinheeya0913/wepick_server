package com.twojin.wooritheseday.product.service.impl;

import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.product.entity.HallDTO;
import com.twojin.wooritheseday.product.repository.HallRepository;
import com.twojin.wooritheseday.product.service.HallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("hallService")
@Slf4j
public class HallServiceImpl implements HallService {

    @Autowired
    HallRepository hallRepository;

    @Override
    public List<HallDTO> selectPlaceDTOListByPlaceCd(Long placeCd) {
        log.debug("[HallServiceImpl] >> selectPlaceDTOList :: START");

        List<HallDTO> hallDTOList = hallRepository.findAllByPlaceDTO_PlaceCdAndUseAt(placeCd,true)
                .orElseThrow(()->new BusinessExceptionHandler(ErrorCode.PRODUCT_SELECT_FAIL.getMessage(), ErrorCode.PRODUCT_SELECT_FAIL));

        if (hallDTOList.isEmpty()) {
            log.debug("[HallServiceImpl] >> selectPlaceDTOList :: size 0");
            throw new BusinessExceptionHandler(ErrorCode.PRODUCT_SELECT_NO_RESULT.getMessage(), ErrorCode.PRODUCT_SELECT_NO_RESULT);
        }

        return hallDTOList;
    }
}

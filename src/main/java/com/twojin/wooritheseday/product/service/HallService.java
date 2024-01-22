package com.twojin.wooritheseday.product.service;

import com.twojin.wooritheseday.product.entity.HallDTO;

import java.util.List;

public interface HallService {

    List<HallDTO> selectPlaceDTOListByPlaceCd(Long productCd);
}

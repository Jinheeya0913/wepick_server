package com.twojin.wooritheseday.product.service.impl;

import com.twojin.wooritheseday.product.entity.HallDTO;
import com.twojin.wooritheseday.product.service.HallService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("hallService")
public class HallServiceImpl implements HallService {

    @Override
    public List<HallDTO> selectPlaceDTOList() {
        return null;
    }
}

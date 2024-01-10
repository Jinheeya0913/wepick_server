package com.twojin.wooritheseday.article.service.impl;

import com.twojin.wooritheseday.article.entity.EstimateHallDTO;
import com.twojin.wooritheseday.article.repository.EstimateHallRepository;
import com.twojin.wooritheseday.article.service.EstimateService;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("estimateService")
public class EstimateServiceImpl implements EstimateService {

    @Autowired
    EstimateHallRepository estimateHallRepository;

    // 웨딩홀 견적서 작성
    @Override
    public boolean writeHallEstimate(EstimateHallDTO hallEstimateVO) {


        EstimateHallDTO alreadyExist = estimateHallRepository.findByWriterIdAndPlaceInfo_placeCd(hallEstimateVO.getWriterId(),hallEstimateVO.getPlaceInfo().getPlaceCd()).orElse(null);

        if (alreadyExist != null) {
            throw new BusinessExceptionHandler(ErrorCode.ESTIMATE_EXIST_ALREADY.getMessage(), ErrorCode.ESTIMATE_EXIST_ALREADY);
        } else {
            estimateHallRepository.save(hallEstimateVO);
        }

        return true;
    }
}

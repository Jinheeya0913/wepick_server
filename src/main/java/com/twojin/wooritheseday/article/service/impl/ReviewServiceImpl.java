package com.twojin.wooritheseday.article.service.impl;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import com.twojin.wooritheseday.article.repository.ReviewHallRepository;
import com.twojin.wooritheseday.article.repository.ReviewMasterRepository;
import com.twojin.wooritheseday.article.service.ReviewService;
import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.common.enums.ProductClass;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("reviewService")
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewHallRepository hallRepository;

    @Autowired
    ReviewMasterRepository masterRepository;

    @Override
    @Transactional
    public boolean writeReviewHall(ReviewHallDTO reviewHallDTO) {
        log.debug("[ReviewServiceImpl] >> writeReviewArticle :: START");
        log.debug("[ReviewServiceImpl] >> writeReviewArticle :: reviewHallDTO.toString() >> " + reviewHallDTO.toString());

        // 1. 이미 리뷰한 대상인지 확인

//        ReviewHallDTO alreadyReview = hallRepository.findByUserIdAndAndPlaceInfo_placeCd(reviewHallDTO.userId, reviewHallDTO.getPlaceInfo().getPlaceCd())
//                .orElse(null);
//        if (alreadyReview != null) {
//            throw new BusinessExceptionHandler(ErrorCode.REVIEW_REGIST_ALREADY.getMessage(), ErrorCode.REVIEW_REGIST_ALREADY);
//        }

        // 2. 최초 리뷰면 저장

//        try {
//            ReviewHallDTO hallResult = hallRepository.save(reviewHallDTO);
//            ReviewMasterDTO reviewMasterDTO = new ReviewMasterDTO();
//            reviewMasterDTO = reviewMasterDTO.createNewDto(hallResult.getReviewCD(), hallResult.userId, ProductClass.HALL_CLASS);
//            ReviewMasterDTO resultMaster = masterRepository.save(reviewMasterDTO);
//
//        } catch (Exception e) {
//            log.debug("[ReviewServiceImpl] >> writeReviewHall :: Error 발생");
//            e.printStackTrace();
//            throw new BusinessExceptionHandler(ErrorCode.REVIEW_REGIST_FAIL.getMessage(), ErrorCode.REVIEW_REGIST_FAIL);
//        }

        // 3. DB에 성공적으로 저장했으면 리뷰 이미지를 서버에 저장

        return true;
    }
}

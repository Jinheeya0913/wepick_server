package com.twojin.wooritheseday.article.service.impl;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import com.twojin.wooritheseday.article.repository.ReviewHallRepository;
import com.twojin.wooritheseday.article.repository.ReviewMasterRepository;
import com.twojin.wooritheseday.article.service.ReviewService;
import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.common.enums.ProductClass;
import com.twojin.wooritheseday.common.utils.JsonConvertModules;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import com.twojin.wooritheseday.product.vo.ReviewCommonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service("reviewService")
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewHallRepository hallRepository;

    @Autowired
    ReviewMasterRepository masterRepository;

    @Override
    @Transactional
    public boolean writeReviewHall(String data, List<MultipartFile> images, String userId) {
        log.debug("[ReviewServiceImpl] >> writeReviewArticle :: START");
        log.debug("[ReviewServiceImpl] >> writeReviewArticle :: data.toString() >> " + data.toString());

        ReviewHallDTO reviewHallDTO = JsonConvertModules.jsonStrToDto(data, ReviewHallDTO.class);
        ReviewCommonVO reviewCommonVO = JsonConvertModules.jsonStrToDto(data, ReviewCommonVO.class);
        PlaceDTO placeDTO = JsonConvertModules.jsonStrToDto(JsonConvertModules.toJsonString(reviewHallDTO.getPlaceInfo()), PlaceDTO.class);

        // 1. 이미 리뷰한 대상인지 확인

        ReviewHallDTO alreadyReview = hallRepository.findByUserIdAndAndPlaceInfo_placeCd(userId, reviewHallDTO.getPlaceInfo().getPlaceCd())
                .orElse(null);
        if (alreadyReview != null) {
            throw new BusinessExceptionHandler(ErrorCode.REVIEW_REGIST_ALREADY.getMessage(), ErrorCode.REVIEW_REGIST_ALREADY);
        }

        // 2. 최초 리뷰면 저장

        ReviewMasterDTO resultMaster = null;

        try {
            ReviewHallDTO hallResult = hallRepository.save(reviewHallDTO);
            ReviewMasterDTO reviewMasterDTO = new ReviewMasterDTO();
            reviewMasterDTO = reviewMasterDTO.createNewDto(hallResult.getReviewCD(), hallResult.userId, ProductClass.HALL_CLASS);

            resultMaster = masterRepository.save(reviewMasterDTO);
        } catch (Exception e) {
            log.debug("[ReviewServiceImpl] >> writeReviewHall :: Error 발생");
            e.printStackTrace();
            throw new BusinessExceptionHandler(ErrorCode.REVIEW_REGIST_FAIL.getMessage(), ErrorCode.REVIEW_REGIST_FAIL);
        }

        // 3. DB에 성공적으로 저장했으면 리뷰 이미지를 서버에 저장


        return true;
    }

    @Override
    public ReviewHallDTO convertJsontToReveiwHall(String jsonData) {
        ReviewHallDTO reviewHallDTO = new ReviewHallDTO();
        try {
            reviewHallDTO = JsonConvertModules.jsonStrToDto(jsonData, ReviewHallDTO.class);
            PlaceDTO placeDTO = JsonConvertModules.jsonStrToDto(jsonData, PlaceDTO.class);
            reviewHallDTO.setPlaceInfo(placeDTO);
        } catch(Exception e) {
            log.error("[ReviewServiceImpl] >> convertJsontToReveiwHall :: 에러발생");
            return null;
        }
        return reviewHallDTO;
    }
}

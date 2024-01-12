package com.twojin.wooritheseday.article.service.impl;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import com.twojin.wooritheseday.article.repository.ReviewHallRepository;
import com.twojin.wooritheseday.article.repository.ReviewMasterRepository;
import com.twojin.wooritheseday.article.service.ReviewService;
import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.common.enums.ProductClass;
import com.twojin.wooritheseday.common.utils.FileUtil;
import com.twojin.wooritheseday.common.utils.JsonConvertModules;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import com.twojin.wooritheseday.product.vo.ReviewCommonVO;
import com.twojin.wooritheseday.user.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("reviewService")
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewHallRepository hallRepository;

    @Autowired
    ReviewMasterRepository masterRepository;

    @Autowired
    FileUtil fileUtil;

    @Override
    @Transactional
    public ReviewMasterDTO writeReviewHall(String data, List<MultipartFile> images, String userId) {
        log.debug("[ReviewServiceImpl] >> writeReviewHall :: START");

        ReviewHallDTO reviewHallDTO = JsonConvertModules.jsonStrToDto(data, ReviewHallDTO.class);
        ReviewCommonVO reviewCommonVO = JsonConvertModules.jsonStrToDto(data, ReviewCommonVO.class);
        PlaceDTO placeDTO = JsonConvertModules.jsonStrToDto(JsonConvertModules.toJsonString(reviewHallDTO.getPlaceInfo()), PlaceDTO.class);


        // 1. 이미 리뷰한 대상인지 확인

        ReviewHallDTO alreadyReview = hallRepository.findByUserIdAndAndPlaceInfo_placeCd(userId, reviewHallDTO.getPlaceInfo().getPlaceCd())
                .orElse(null);
        if (alreadyReview != null) {
            throw new BusinessExceptionHandler(ErrorCode.REVIEW_REGIST_ALREADY.getMessage(), ErrorCode.REVIEW_REGIST_ALREADY);
        }

        // 2. 리뷰 이미지를 서버에 저장
        // 리뷰 이미지가 있을 경우에만

        String folderPath = "reviewHallImgs";
        List<String> fileNameList = new ArrayList<String>();
        List<String> saveNameList = new ArrayList<String >();

        try {
            if (images.size() > 0) {
                for (MultipartFile file : images) {
                    Map<String, String> resultMap = fileUtil.saveMultiFileImg(file, folderPath);
                    String fileName = resultMap.get("fileName");
                    String saveName = resultMap.get("saveName");
                    if (fileName != null && saveName != null) {
                        log.debug("[ReviewServiceImpl] >> writeReviewHall :: 이미저 저장 성공 >>" + fileName);
                        fileNameList.add(fileName);
                        saveNameList.add(saveName);
                    } else {
                        throw new BusinessExceptionHandler(ErrorCode.FILE_IMG_UPLOAD_FAIL.getMessage(), ErrorCode.FILE_IMG_UPLOAD_FAIL);
                    }
                }
            }
        } catch (BusinessExceptionHandler e) { // 실패할 경우 업로드한 사진들 모두 삭제
            FileUtil.deleteMultiFileImgs(folderPath, saveNameList);
        } catch (Exception e) {
            FileUtil.deleteMultiFileImgs(folderPath, saveNameList);
            throw new RuntimeException(e);
        }

        // 이미지 저장이 끝났으면 나머지 값 세팅

        reviewHallDTO.setReviewImages(fileNameList);
        reviewHallDTO.setUserId(userId);
        reviewHallDTO.setReviewTitle(reviewCommonVO.getReviewTitle());
        reviewHallDTO.setReviewContents(reviewCommonVO.getReviewContents());


        // 3. db에 저장
        ReviewMasterDTO resultMaster = null;

        try {
            log.debug("[ReviewServiceImpl] >> writeReviewHall :: reviewHall 저장 수행");

            ReviewHallDTO hallResult = hallRepository.save(reviewHallDTO);

            ReviewMasterDTO reviewMasterDTO = new ReviewMasterDTO();
            reviewMasterDTO = reviewMasterDTO.createNewDto(hallResult.getReviewCD(), hallResult.userId, ProductClass.HALL_CLASS);


            log.debug("[ReviewServiceImpl] >> writeReviewHall :: reviewMasterDTO.toString 저장 수행" + reviewMasterDTO.toString());
            ReviewMasterDTO masterDTO = masterRepository.save(reviewMasterDTO);
            return masterDTO;
        } catch (Exception e) {
            log.debug("[ReviewServiceImpl] >> writeReviewHall :: Error 발생");
            FileUtil.deleteMultiFileImgs(folderPath, saveNameList);
            e.printStackTrace();
            throw new BusinessExceptionHandler(ErrorCode.REVIEW_REGIST_FAIL.getMessage(), ErrorCode.REVIEW_REGIST_FAIL);
        }
    }

    @Override
    public ReviewHallDTO convertJsontToReveiwHall(String jsonData) {
        ReviewHallDTO reviewHallDTO = new ReviewHallDTO();
        try {
            reviewHallDTO = JsonConvertModules.jsonStrToDto(jsonData, ReviewHallDTO.class);
            PlaceDTO placeDTO = JsonConvertModules.jsonStrToDto(jsonData, PlaceDTO.class);
            reviewHallDTO.setPlaceInfo(placeDTO);
        } catch (Exception e) {
            log.error("[ReviewServiceImpl] >> convertJsontToReveiwHall :: 에러발생");
            return null;
        }
        return reviewHallDTO;
    }
}

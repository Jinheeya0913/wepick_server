package com.twojin.wooritheseday.article.service.impl;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import com.twojin.wooritheseday.article.repository.ReviewHallRepository;
import com.twojin.wooritheseday.article.repository.ReviewMasterRepository;
import com.twojin.wooritheseday.article.service.ReviewService;
import com.twojin.wooritheseday.article.vo.ReviewListRespVO;
import com.twojin.wooritheseday.common.enums.ErrorCode;
import com.twojin.wooritheseday.common.enums.ProductClass;
import com.twojin.wooritheseday.common.utils.FileUtil;
import com.twojin.wooritheseday.common.utils.JsonConvertModules;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.file.dto.ReviewImgEntity;
import com.twojin.wooritheseday.file.repository.ReviewImgInfoRepository;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import com.twojin.wooritheseday.product.vo.ReviewCommonVO;
import com.twojin.wooritheseday.user.entity.MyReviewLike;
import com.twojin.wooritheseday.user.repository.UserReviewLikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
    ReviewImgInfoRepository reviewImgInfoRepository;

    @Autowired
    UserReviewLikeRepository userReviewLikeRepository;
    @Autowired
    FileUtil fileUtil;

    @Override
    public ReviewListRespVO selectReviewListByProductClass(String productClass) {
        log.debug("[ReviewServiceImpl] >> selectReviewListByProductClass :: START");

        List<ReviewCommonVO> resultList = null;

        ReviewListRespVO respVO = new ReviewListRespVO();

        if (productClass.equals(ProductClass.HALL.getClassName())) {
            log.debug("[ReviewServiceImpl] >> selectReviewListByProductClass :: "+ProductClass.HALL);
             List<ReviewHallDTO> reviewHallDTOList = hallRepository.findByUseAtOrderByRegistDtDesc(true)
                    .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.REVIEW_SELECT_LIST_FAILE.getMessage(), ErrorCode.REVIEW_SELECT_LIST_FAILE));
            resultList = new ArrayList<>(reviewHallDTOList);
        } else if (productClass.equals(ProductClass.GIFT.getClassName())) {
            log.debug("[ReviewServiceImpl] >> selectReviewListByProductClass :: "+ProductClass.GIFT);
            // 작성 예정
        } else if (productClass.equals(ProductClass.PACKAGE.getClassName())) {
            log.debug("[ReviewServiceImpl] >> selectReviewListByProductClass :: "+ProductClass.PACKAGE);
            // 작성 예정
        } else if (productClass.equals(ProductClass.ALL.getClassName())) {
            log.debug("[ReviewServiceImpl] >> selectReviewListByProductClass :: "+ProductClass.PACKAGE);
        } else  {
            throw new BusinessExceptionHandler(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getMessage(), ErrorCode.MISSING_REQUEST_PARAMETER_ERROR);
        }

        respVO.setReviewClass(productClass);
        respVO.setReviewList(resultList);

        return respVO;
    }

    @Override
    @Transactional
    public ReviewMasterDTO writeReviewHall(String data, List<MultipartFile> images, String userId) {
        log.debug("[ReviewServiceImpl] >> writeReviewHall :: START");

        ReviewHallDTO reviewHallDTO = JsonConvertModules.jsonStrToDto(data, ReviewHallDTO.class);
        ReviewCommonVO reviewCommonVO = JsonConvertModules.jsonStrToDto(data, ReviewCommonVO.class);
        PlaceDTO placeDTO = JsonConvertModules.jsonStrToDto(JsonConvertModules.toJsonString(reviewHallDTO.getPlaceInfo()), PlaceDTO.class);

        ProductClass productClass = ProductClass.HALL;


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
        ReviewImgEntity fileVo;

        try {
            fileVo = null;
            if (images.size() > 0) {
                for (MultipartFile file : images) {
                    Map<String, String> resultMap = fileUtil.saveMultiFileImg(file, folderPath);
                    String fileName = resultMap.get("fileName");
                    String saveName = resultMap.get("saveName");
                    String fileExtension = resultMap.get("fileExtension");
                    fileVo = ReviewImgEntity.builder()
                            .userId(userId)
                            .fileExtension(fileExtension)
                            .useAt(true)
                            .fileName(fileName)
                            .filePath(folderPath)
                            .productClass(productClass)
                            .build();

                    reviewImgInfoRepository.save(fileVo);

                    if (fileName != null && saveName != null && fileVo != null) {
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
        reviewHallDTO.setUseAt(true);
        reviewHallDTO.setProductClass(ProductClass.HALL);


        // 3. db에 저장
        ReviewMasterDTO resultMaster = null;

        try {
            log.debug("[ReviewServiceImpl] >> writeReviewHall :: reviewHall 저장 수행");

            ReviewHallDTO hallResult = hallRepository.save(reviewHallDTO);

            ReviewMasterDTO reviewMasterDTO = new ReviewMasterDTO();
            reviewMasterDTO = reviewMasterDTO.createNewDto(hallResult.getReviewCD(), hallResult.getUserId(), productClass);


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


    /**
     *
     * @param reviewInfo
     * @param userId
     * @return true : thumb up / false : thumb down
     */
    @Override
    @Transactional
    public boolean thumbUpDownReviewLike(ReviewMasterDTO reviewInfo, String userId) {
        log.debug("[ReviewServiceImpl] >> thumbUpReviewLike :: START");

        String  productClass = reviewInfo.getProductClass().getClassName();

        MyReviewLike myReviewLike = userReviewLikeRepository.findByUserIdAndReviewArticleCd(userId, reviewInfo.getReviewArticleCd())
                .orElse(null);

        ReviewHallDTO reviewHallInfo = null;


        if (myReviewLike != null) {
            userReviewLikeRepository.delete(myReviewLike);
            if (productClass.equals(ProductClass.HALL.getClassName())) {
                log.debug("[ReviewServiceImpl] >> thumbUpDownReviewLike :: Hall Review Count -1");
                reviewHallInfo = hallRepository.findByReviewCD(myReviewLike.getReviewArticleCd())
                        .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.REVIEW_SELECT_LIST_FAILE.getMessage(), ErrorCode.REVIEW_SELECT_LIST_FAILE));
                reviewHallInfo.setFavoriteCount(reviewHallInfo.getFavoriteCount()-1);
                hallRepository.save(reviewHallInfo);
            } else if (productClass.equals(ProductClass.PACKAGE.getClassName())) {
                log.debug("[ReviewServiceImpl] >> thumbUpDownReviewLike :: PACKAGE Review Count -1");
            } else if (productClass.equals(ProductClass.GIFT.getClassName())){
                log.debug("[ReviewServiceImpl] >> thumbUpDownReviewLike :: PACKAGIFTGE Review Count -1");
            }

            return true;
        } else {
            myReviewLike = MyReviewLike.builder()
                    .reviewWriter(reviewInfo.getUserId())
                    .productClass(reviewInfo.getProductClass())
                    .reviewArticleCd(reviewInfo.getReviewArticleCd())
                    .userId(userId)
                    .build();

            userReviewLikeRepository.save(myReviewLike);

            if (productClass.equals(ProductClass.HALL.getClassName())) {
                log.debug("[ReviewServiceImpl] >> thumbUpDownReviewLike :: Hall Review Count +1");
                reviewHallInfo = hallRepository.findByReviewCD(myReviewLike.getReviewArticleCd())
                        .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.REVIEW_SELECT_LIST_FAILE.getMessage(), ErrorCode.REVIEW_SELECT_LIST_FAILE));
                reviewHallInfo.setFavoriteCount(reviewHallInfo.getFavoriteCount() + 1);
            } else if (productClass.equals(ProductClass.PACKAGE.getClassName())) {
                log.debug("[ReviewServiceImpl] >> thumbUpDownReviewLike :: PACKAGE Review Count +1");
            } else if (productClass.equals(ProductClass.GIFT.getClassName())){
                log.debug("[ReviewServiceImpl] >> thumbUpDownReviewLike :: PACKAGIFTGE Review Count +1");
            }

            return false;
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

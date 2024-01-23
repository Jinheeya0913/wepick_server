package com.twojin.wooritheseday.article.service;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import com.twojin.wooritheseday.article.vo.ReviewListRespVO;
import com.twojin.wooritheseday.product.vo.ReviewCommonVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {


    ReviewListRespVO selectReviewListByProductClass(String productClass);

    ReviewCommonVO getReviewInfoByReviewCd(Long reviewCd);

    ReviewMasterDTO writeReviewHall(String data, List<MultipartFile> images, String userId);

    boolean thumbUpDownReviewLike(ReviewMasterDTO reviewInfo, String userId);

    ReviewHallDTO convertJsontToReveiwHall(String jsonData);
}

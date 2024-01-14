package com.twojin.wooritheseday.article.service;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import com.twojin.wooritheseday.common.enums.ProductClass;
import com.twojin.wooritheseday.product.vo.ReviewCommonVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {


    List<ReviewCommonVO> selectReviewListByProductClass(String productClass);

    ReviewMasterDTO writeReviewHall(String data, List<MultipartFile> images, String userId);

    ReviewHallDTO convertJsontToReveiwHall(String jsonData);
}

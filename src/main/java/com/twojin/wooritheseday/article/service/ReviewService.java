package com.twojin.wooritheseday.article.service;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import com.twojin.wooritheseday.common.enums.ProductClass;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {


    List<ReviewMasterDTO> selectReviewMasterList(ProductClass productClass);

    ReviewMasterDTO writeReviewHall(String data, List<MultipartFile> images, String userId);

    ReviewHallDTO convertJsontToReveiwHall(String jsonData);
}

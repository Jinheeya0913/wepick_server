package com.twojin.wooritheseday.article.service;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    ReviewMasterDTO writeReviewHall(String data, List<MultipartFile> images, String userId);

    ReviewHallDTO convertJsontToReveiwHall(String jsonData);
}

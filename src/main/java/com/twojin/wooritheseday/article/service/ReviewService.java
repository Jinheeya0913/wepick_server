package com.twojin.wooritheseday.article.service;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import org.springframework.stereotype.Service;

public interface ReviewService {

    boolean  writeReviewArticle(ReviewHallDTO reviewHallDTO);
}

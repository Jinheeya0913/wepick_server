package com.twojin.wooritheseday.article.service.impl;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.article.repository.ReviewHallRepository;
import com.twojin.wooritheseday.article.repository.ReviewMasterRepository;
import com.twojin.wooritheseday.article.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reviewService")
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewHallRepository hallRepository;

    @Autowired
    ReviewMasterRepository masterRepository;

    @Override
    public boolean writeReviewArticle(ReviewHallDTO reviewHallDTO) {



        return false;
    }
}

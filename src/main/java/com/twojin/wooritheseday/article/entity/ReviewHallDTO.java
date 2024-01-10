package com.twojin.wooritheseday.article.entity;

import com.twojin.wooritheseday.product.vo.ReviewCommonVO;

import javax.persistence.Entity;

@Entity
public class ReviewHallDTO extends ReviewCommonVO {

    protected double transRating;

    protected double banquetRating;

    protected double serviceRating;

    protected double visitorRating;
}

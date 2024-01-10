package com.twojin.wooritheseday.article.entity;

import com.twojin.wooritheseday.product.vo.ReviewCommonVO;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "woori_review_hall")
@NoArgsConstructor
public class ReviewHallDTO extends ReviewCommonVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARTICLE")
    @SequenceGenerator(name = "SEQ_ARTICLE", sequenceName = "SEQ_ARTICLE", allocationSize = 1)
    private Long reviewCD;

    protected double transRating;

    protected double banquetRating;

    protected double serviceRating;

    protected double visitorRating;

    public ReviewHallDTO(Long reviewCD, double transRating, double banquetRating, double serviceRating, double visitorRating) {
        this.reviewCD = reviewCD;
        this.transRating = transRating;
        this.banquetRating = banquetRating;
        this.serviceRating = serviceRating;
        this.visitorRating = visitorRating;
    }

    @Builder
    public ReviewHallDTO(String userId, String reviewTitle, String reviewContents, String reviewImages, Date registDt, Date updateDt, Long reviewCD, double transRating, double banquetRating, double serviceRating, double visitorRating) {
        super(userId, reviewTitle, reviewContents, reviewImages, registDt, updateDt);
        this.reviewCD = reviewCD;
        this.transRating = transRating;
        this.banquetRating = banquetRating;
        this.serviceRating = serviceRating;
        this.visitorRating = visitorRating;
    }
}

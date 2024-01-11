package com.twojin.wooritheseday.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import com.twojin.wooritheseday.product.vo.ReviewCommonVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "woori_review_hall")
@Data
@NoArgsConstructor
// JsonIgnoreProperties
// 주어진 JSON 데이터에서 DTO로 변환하는 과정에서 일부 필드가 DTO에 포함되지 않는 경우,
// Jackson 라이브러리의 기능을 사용하여 특정 필드를 무시하거나, 기본값으로 설정하
public class ReviewHallDTO extends ReviewCommonVO {

    @Id
    @Column(name = "review_cd")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARTICLE")
    @SequenceGenerator(name = "SEQ_ARTICLE", sequenceName = "SEQ_ARTICLE", allocationSize = 1)
    private Long reviewCD;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="place_cd",referencedColumnName = "place_cd")
    private PlaceDTO placeInfo;

    protected double transRating;

    protected double banquetRating;

    protected double serviceRating;

    protected double visitorRating;
    @Builder
    public ReviewHallDTO(String userId, String reviewTitle, String reviewContents, List<String> reviewImages, Date registDt, Date updateDt, Long reviewCD, double transRating, double banquetRating, double serviceRating, double visitorRating) {
        super(userId, reviewTitle, reviewContents, reviewImages, registDt, updateDt);
        this.reviewCD = reviewCD;
        this.transRating = transRating;
        this.banquetRating = banquetRating;
        this.serviceRating = serviceRating;
        this.visitorRating = visitorRating;
    }

    @Override
    public String toString() {
        return "ReviewHallDTO{" +
                "reviewCD=" + reviewCD +
                ", transRating=" + transRating +
                ", banquetRating=" + banquetRating +
                ", serviceRating=" + serviceRating +
                ", visitorRating=" + visitorRating +
                ", userId='" + userId + '\'' +
                ", reviewTitle='" + reviewTitle + '\'' +
                ", reviewContents='" + reviewContents + '\'' +
                ", reviewImages=" + reviewImages +
                ", registDt=" + registDt +
                ", updateDt=" + updateDt +
                '}';
    }

    public void setCommonInfos(ReviewCommonVO commonInfos) {
        this.setReviewTitle(commonInfos.getUserId());
        this.setReviewContents(commonInfos.getReviewContents());
        this.setUserId(commonInfos.getUserId());
        this.setReviewImages(commonInfos.getReviewImages());
    }
}

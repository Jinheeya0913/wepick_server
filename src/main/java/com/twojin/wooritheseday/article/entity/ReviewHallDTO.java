package com.twojin.wooritheseday.article.entity;

import com.twojin.wooritheseday.product.entity.HallDTO;
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

    public ReviewHallDTO(Long reviewCD, double transRating, double banquetRating, double serviceRating, double visitorRating) {
        this.reviewCD = reviewCD;
        this.transRating = transRating;
        this.banquetRating = banquetRating;
        this.serviceRating = serviceRating;
        this.visitorRating = visitorRating;
    }

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
}

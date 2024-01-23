package com.twojin.wooritheseday.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twojin.wooritheseday.common.enums.ProductClass;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "woori_user_review_like")
@Data
@NoArgsConstructor
public class MyReviewLikeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rLikeCd;

    private Long reviewArticleCd;

    private String reviewWriter;

    private String userId;

    @Enumerated(EnumType.STRING)
    private ProductClass productClass;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    private Date regDate;

    @Builder
    public MyReviewLikeDTO(Long rLikeCd, Long reviewArticleCd, String reviewWriter, String userId, ProductClass productClass, Date regDate) {
        this.rLikeCd = rLikeCd;
        this.reviewArticleCd = reviewArticleCd;
        this.reviewWriter = reviewWriter;
        this.userId = userId;
        this.productClass = productClass;
        this.regDate = regDate;
    }
}

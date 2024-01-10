package com.twojin.wooritheseday.article.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twojin.wooritheseday.common.enums.ProductClass;
import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "woori_review_master")
@NoArgsConstructor
public class ReviewMasterDTO  {

    @Id
    protected Long reviewArticleCd;

    protected String userId;

    protected ProductClass productClass;

    @Convert(converter = BooleanToYNConverterUtil.class)
    @ColumnDefault("Y")
    protected boolean useAt;

    @ColumnDefault("0")
    protected int openCount;

    @ColumnDefault("0")
    protected int favoriteCount;

    @ColumnDefault("0")
    protected int thumbCount;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date registDt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date updateDt;

    @Builder
    public ReviewMasterDTO(Long reviewArticleCd, String userId, ProductClass productClass, boolean useAt,
                           int openCount, int favoriteCount, int thumbCount,
                           Date registDt, Date updateDt) {
        this.reviewArticleCd = reviewArticleCd;
        this.userId = userId;
        this.productClass = productClass;
        this.useAt = useAt;
        this.openCount = openCount;
        this.favoriteCount = favoriteCount;
        this.thumbCount = thumbCount;
        this.registDt = registDt;
        this.updateDt = updateDt;
    }


    public ReviewMasterDTO createNewDto(Long reviewCd, String userId, ProductClass productClass ) {
        return ReviewMasterDTO.builder()
                .reviewArticleCd(reviewCd)
                .userId(userId)
                .productClass(productClass)
                .build();
    }
}

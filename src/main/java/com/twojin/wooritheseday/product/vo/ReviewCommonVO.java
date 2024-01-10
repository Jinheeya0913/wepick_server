package com.twojin.wooritheseday.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@NoArgsConstructor
@MappedSuperclass
public class ReviewCommonVO {


    public String userId;

    public String reviewTitle;

    public String reviewContents;

    public String reviewImages;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    public Date registDt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    public Date updateDt;

    public ReviewCommonVO(String userId, String reviewTitle, String reviewContents, String reviewImages, Date registDt, Date updateDt) {
        this.userId = userId;
        this.reviewTitle = reviewTitle;
        this.reviewContents = reviewContents;
        this.reviewImages = reviewImages;
        this.registDt = registDt;
        this.updateDt = updateDt;
    }
}

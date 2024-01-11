package com.twojin.wooritheseday.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.twojin.wooritheseday.common.utils.StringListConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@MappedSuperclass
public class ReviewCommonVO {


    public String userId;

    public String reviewTitle;

    public String reviewContents;

    /*
    Request에서 Json으로 받은 List를 Column에 저장할 때 []으로 감싼 String으로 컬럼에 저장
     */
    @Convert(converter = StringListConverter.class)
    public List<String> reviewImages;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    public Date registDt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    public Date updateDt;

    public ReviewCommonVO(String userId, String reviewTitle, String reviewContents, List<String> reviewImages, Date registDt, Date updateDt) {
        this.userId = userId;
        this.reviewTitle = reviewTitle;
        this.reviewContents = reviewContents;
        this.reviewImages = reviewImages;
        this.registDt = registDt;
        this.updateDt = updateDt;
    }
}

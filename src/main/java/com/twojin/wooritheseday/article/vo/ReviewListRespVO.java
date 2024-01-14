package com.twojin.wooritheseday.article.vo;

import com.twojin.wooritheseday.product.vo.ReviewCommonVO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewListRespVO {

    List<ReviewCommonVO> reviewList;

    String reviewClass;

    @Builder
    public ReviewListRespVO(List<ReviewCommonVO> reviewList, String reviewClass) {
        this.reviewList = reviewList;
        this.reviewClass = reviewClass;
    }
}

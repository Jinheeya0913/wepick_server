package com.twojin.wooritheseday.article.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twojin.wooritheseday.common.enums.ProductClass;
import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ReviewMasterDTO  {

    @Id
    protected Long reviewArticleCd;

    protected String reveiwerId;

    protected ProductClass productClass;

    @Convert(converter = BooleanToYNConverterUtil.class)
    protected boolean useAt;

    protected int openCount;

    protected int favoriteCount;

    protected int thumbCount;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date registDt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date updateDt;

}

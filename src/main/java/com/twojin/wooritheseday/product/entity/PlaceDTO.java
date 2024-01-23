package com.twojin.wooritheseday.product.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * 예식장 Entity
 */
@Entity
@Data
@Table(name = "woori_place_master")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT")
    @SequenceGenerator(name = "SEQ_PRODUCT", sequenceName = "SEQ_PRODUCT", allocationSize = 1)
    @Column(name = "place_cd")
    protected Long placeCd;

    protected String placeNm;

    protected String placeAddress;

    protected String placePhone;

    @Convert(converter = BooleanToYNConverterUtil.class)
    protected boolean useAt;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date registDt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date updateDt;






}

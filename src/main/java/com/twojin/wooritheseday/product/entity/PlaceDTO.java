package com.twojin.wooritheseday.product.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "place_cd")
    protected Long placeCd;

    protected String placeNm;

    protected String placeAddress;

    protected String placePhone;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date registDt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date updateDt;






}

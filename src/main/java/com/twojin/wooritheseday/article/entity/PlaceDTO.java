package com.twojin.wooritheseday.article.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PlaceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "place_cd")
    protected Long placeCd;

    protected String placeName;

    protected String placeAddress;

    protected String placePhone;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date registDt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    protected Date updateDt;






}

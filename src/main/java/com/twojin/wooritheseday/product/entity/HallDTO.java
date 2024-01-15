package com.twojin.wooritheseday.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "woori_hall_master")
public class HallDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hall_cd")
    private Long hallCd;

    // 외래키 설정
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "place_cd" , referencedColumnName = "place_cd")
    private PlaceDTO placeCd;

    private String hallNm;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    private Date registDt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    private Date updateDt;
}

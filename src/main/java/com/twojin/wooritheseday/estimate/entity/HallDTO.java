package com.twojin.wooritheseday.estimate.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class HallDTO {

    @Id
    private Long hallCd;

    // 외래키 설정
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "place_id" , referencedColumnName = "place_Id")
    private PlaceDTO placeCd;

    private String hallName;

    @CreationTimestamp
    private Date registDt;

    @UpdateTimestamp
    private Date updateDt;
}

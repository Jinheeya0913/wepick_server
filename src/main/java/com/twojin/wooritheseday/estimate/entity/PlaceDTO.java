package com.twojin.wooritheseday.estimate.entity;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

/**
 * 예식장 Entity
 */
@Entity
public class PlaceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "place_id")
    protected Long placeId;

    protected String placeName;

    protected String placeAddress;

    protected String placePhone;

    @CreationTimestamp
    protected String registDt;

    @UpdateTimestamp
    protected String updateDt;






}

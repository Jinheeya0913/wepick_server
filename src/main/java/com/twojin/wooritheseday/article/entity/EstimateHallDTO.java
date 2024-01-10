package com.twojin.wooritheseday.article.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "woori_hall_estimate")
public class EstimateHallDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long estimateHallCd;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="place_cd",referencedColumnName = "place_cd")
    private PlaceDTO placeInfo;
    private String hallName;
    private String placeName;
    private String writerId;
    private int placeFee;
    private int foodFee;
    private int guaranteedPrsnl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "Asia/Seoul")
    private Date bookingDate;
    private String bookingTime;
    private String memo;
    private boolean includeBeverage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    private Date registDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    private Date updateDt;

    @Builder
    public EstimateHallDTO(Long estimateHallCd, PlaceDTO placeInfo, String hallName, String placeName, String writerId, int placeFee, int foodFee, int guaranteedPrsnl, Date bookingDate, String bookingTime, String memo, boolean includeBeverage, Date registDt, Date updateDt) {
        this.estimateHallCd = estimateHallCd;
        this.placeInfo = placeInfo;
        this.hallName = hallName;
        this.placeName = placeName;
        this.writerId = writerId;
        this.placeFee = placeFee;
        this.foodFee = foodFee;
        this.guaranteedPrsnl = guaranteedPrsnl;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.memo = memo;
        this.includeBeverage = includeBeverage;
        this.registDt = registDt;
        this.updateDt = updateDt;
    }
}

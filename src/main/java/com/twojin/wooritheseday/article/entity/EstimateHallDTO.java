package com.twojin.wooritheseday.article.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long estimateHallCd;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="hall_cd",referencedColumnName = "hall_cd")
    private HallDTO hallDTO;
    private String hallName;
    private String placeName;
    private int placeFee;
    private int foodFee;
    private int guaranteedPrsnl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    private Date bookingDate;
    private String bookingTime;
    private String memo;
    private boolean includeBeverage;

    @Builder
    public EstimateHallDTO(Long estimateHallCd, HallDTO hallDTO, String hallName, String placeName, int placeFee, int foodFee, int guaranteedPrsnl, Date bookingDate, String bookingTime, String memo, boolean includeBeverage) {
        this.estimateHallCd = estimateHallCd;
        this.hallDTO = hallDTO;
        this.hallName = hallName;
        this.placeName = placeName;
        this.placeFee = placeFee;
        this.foodFee = foodFee;
        this.guaranteedPrsnl = guaranteedPrsnl;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.memo = memo;
        this.includeBeverage = includeBeverage;
    }
}

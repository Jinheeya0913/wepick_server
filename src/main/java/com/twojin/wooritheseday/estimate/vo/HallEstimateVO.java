package com.twojin.wooritheseday.estimate.vo;


import lombok.Builder;

import java.util.Date;

public class HallEstimateVO {


    Long placeId;
    Long hallId;
    String hallName;
    String placeName;
    int placeFee;
    int foodFee;
    int guaranteedPrsnl;
    Date bookingDate;
    String bookingTime;
    String memo;
    boolean includeBeverage;

    @Builder
    public HallEstimateVO(Long placeId, Long hallId, String hallName, String placeName, int placeFee, int foodFee, int guaranteedPrsnl, Date bookingDate, String bookingTime, String memo, boolean includeBeverage) {
        this.placeId = placeId;
        this.hallId = hallId;
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

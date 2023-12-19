package com.twojin.wooritheseday.partner.entity.vo;

import com.twojin.wooritheseday.partner.entity.PartnerMaterDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PartnerInfoVo {

    boolean partnerConnYn; // 연결 여부

    String partnerConnCd; // 바트너 연결 코드

    String partnerId;

    String partnerNm;

    Date regDt; // 등록 날짜

    Date meetDt; // 만난 날짜

    public PartnerInfoVo (PartnerMaterDTO masterDTO, String partnerId) {
        this.partnerConnCd = masterDTO.getPartnerConnCd();
        this.partnerId = partnerId;
        this.regDt = masterDTO.getRegDt();
        this.meetDt = masterDTO.getMeetDt();
    }

    public PartnerInfoVo (PartnerMaterDTO masterDTO, String partnerId, String partnerNm) {
        this.partnerConnCd = masterDTO.getPartnerConnCd();
        this.partnerConnYn = masterDTO.isPartnerConnYn();
        this.partnerNm = partnerNm;
        this.partnerId = partnerId;
        this.regDt = masterDTO.getRegDt();
    }

    @Builder
    public PartnerInfoVo(boolean partnerConnYn, String partnerConnCd, String partnerId, Date regDt, Date meetDt, String partnerNm) {
        this.partnerConnYn = partnerConnYn;
        this.partnerConnCd = partnerConnCd;
        this.partnerId = partnerId;
        this.regDt = regDt;
        this.meetDt = meetDt;
    }
}

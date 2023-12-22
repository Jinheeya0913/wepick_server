package com.twojin.wooritheseday.partner.entity.vo;

import com.twojin.wooritheseday.partner.entity.PartnerMaterDTO;
import com.twojin.wooritheseday.user.entity.UserDTO;
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

    String partnerImgUrl;

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
    public PartnerInfoVo(boolean partnerConnYn, String partnerConnCd, String partnerId, String partnerNm, String partnerImgUrl, Date regDt, Date meetDt) {
        this.partnerConnYn = partnerConnYn;
        this.partnerConnCd = partnerConnCd;
        this.partnerId = partnerId;
        this.partnerNm = partnerNm;
        this.partnerImgUrl = partnerImgUrl;
        this.regDt = regDt;
        this.meetDt = meetDt;
    }

    public static PartnerInfoVo initPartnerInfoVo(UserDTO partnerInfo, PartnerMaterDTO partnerMaterDTO) {
        return PartnerInfoVo.builder()
                .partnerNm(partnerInfo.getUserNm())
                .partnerImgUrl(partnerInfo.getUserImgUrl())
                .partnerId(partnerInfo.getUserId())
                .partnerConnYn(partnerMaterDTO.isPartnerConnYn())
                .partnerConnCd(partnerMaterDTO.getPartnerConnCd())
                .meetDt(partnerMaterDTO.getMeetDt())
                .regDt(partnerMaterDTO.getRegDt())
                .build();
    }

}

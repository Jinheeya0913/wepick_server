package com.twojin.wooritheseday.partner.entity.vo;

import com.twojin.wooritheseday.partner.entity.PartnerMasterDTO;
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

    String partnerAlias;

    String partnerImgUrl;

    Date regDt; // 등록 날짜

    Date meetDt; // 만난 날짜


    public PartnerInfoVo (PartnerMasterDTO masterDTO, String partnerId) {
        this.partnerConnCd = masterDTO.getPartnerConnCd();
        this.partnerId = partnerId;
        this.regDt = masterDTO.getRegDt();
        this.meetDt = masterDTO.getMeetDt();
    }

    public PartnerInfoVo (PartnerMasterDTO masterDTO, String partnerId, String partnerNm) {
        this.partnerConnCd = masterDTO.getPartnerConnCd();
        this.partnerConnYn = masterDTO.isPartnerConnYn();
        this.partnerNm = partnerNm;
        this.partnerId = partnerId;
        this.regDt = masterDTO.getRegDt();
    }

    @Builder
    public PartnerInfoVo(boolean partnerConnYn, String partnerConnCd, String partnerId, String partnerNm, String partnerAlias, String partnerImgUrl, Date regDt, Date meetDt) {
        this.partnerConnYn = partnerConnYn;
        this.partnerConnCd = partnerConnCd;
        this.partnerId = partnerId;
        this.partnerNm = partnerNm;
        this.partnerAlias = partnerAlias;
        this.partnerImgUrl = partnerImgUrl;
        this.regDt = regDt;
        this.meetDt = meetDt;
    }

    public static PartnerInfoVo initPartnerInfoVo(UserDTO partnerInfo, PartnerMasterDTO partnerMasterDTO) {

        String partnerId = partnerInfo.getUserId();
        String partnerAlias = null;

        if (partnerId.equals(partnerMasterDTO.getPartnerUser1())) {
            partnerAlias = partnerMasterDTO.getPartnerUser1_alias();
        } else if (partnerId.equals(partnerMasterDTO.getPartnerUser2())) {
            partnerAlias = partnerMasterDTO.getPartnerUser2_alias();
        }

        return PartnerInfoVo.builder()
                .partnerNm(partnerInfo.getUserNm())
                .partnerImgUrl(partnerInfo.getUserImgUrl())
                .partnerId(partnerInfo.getUserId())
                .partnerAlias(partnerAlias)
                .partnerConnYn(partnerMasterDTO.isPartnerConnYn())
                .partnerConnCd(partnerMasterDTO.getPartnerConnCd())
                .meetDt(partnerMasterDTO.getMeetDt())
                .regDt(partnerMasterDTO.getRegDt())
                .build();
    }

}

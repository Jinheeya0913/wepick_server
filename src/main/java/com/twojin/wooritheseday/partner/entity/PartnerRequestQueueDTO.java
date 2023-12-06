package com.twojin.wooritheseday.partner.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "woori_partner_regist_queue")
@NoArgsConstructor
public class PartnerRequestQueueDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ptRegQueCd;

    private String ptAcceptorId; // 요청 수신자

    private String ptRequesterId; // 요청 발신자

    private String ptTempCd; // 요청 코드

    private String ptReqStatus; // 처리 상태

    @CreationTimestamp
    private Date regDt;

    @UpdateTimestamp
    private Date updateDt;

    @Builder
    public PartnerRequestQueueDTO(Long ptRegQueCd, String ptAcceptorId, String ptRequesterId, String ptTempCd, String ptReqStatus, Date regDt, Date updateDt) {
        this.ptRegQueCd = ptRegQueCd;
        this.ptAcceptorId = ptAcceptorId;
        this.ptRequesterId = ptRequesterId;
        this.ptTempCd = ptTempCd;
        this.ptReqStatus = ptReqStatus;
        this.regDt = regDt;
        this.updateDt = updateDt;
    }
}

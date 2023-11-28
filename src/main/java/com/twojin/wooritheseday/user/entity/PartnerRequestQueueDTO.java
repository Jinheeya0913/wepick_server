package com.twojin.wooritheseday.user.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    private String ptRegUserId;

    private String ptReqUserId;

    private String ptRegCd;

    @CreationTimestamp
    private Date ptReqDt;

    private Date ptReqUpdateDt;

    private String ptReqStatus;

    public PartnerRequestQueueDTO(String ptRegUserId, String ptReqUserId, String ptRegCd, String ptReqStatus) {
        this.ptRegUserId = ptRegUserId;
        this.ptReqUserId = ptReqUserId;
        this.ptRegCd = ptRegCd;
        this.ptReqStatus = ptReqStatus;
    }
}

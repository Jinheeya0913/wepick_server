package com.twojin.wooritheseday.partner.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "woori_partner_m",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "partnerUserUniq",
                columnNames = {"partner_user_1", "partner_user_2"}

        )
    }
)
@Data
@NoArgsConstructor
public class PartnerMasterDTO {

    // 파트너 Cd
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long partnerCd;

    // 파트너 우효 상태
    @Convert(converter = BooleanToYNConverterUtil.class)
    boolean partnerConnYn;

    @Column
    String partnerConnCd; // 바트너 연결 코드

    @Column(name = "partner_user_1",nullable = false)
    String partnerUser1; // 연결된 사용자 1

    @Column(name = "partner_user_1_alias",nullable = true)
    String partnerUser1_alias;

    @Column(name = "partner_user_2_alias", nullable = true)
    String partnerUser2_alias;

    @Column(name = "partner_user_2", nullable = false)
    String partnerUser2; // 연결된 사용자 2

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    Date regDt; // 등록 날짜

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    Date upateDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
    Date meetDt; // 만난 날짜

    @Builder
    public PartnerMasterDTO(Long partnerCd, boolean partnerConnYn, String partnerConnCd, String partnerUser1, String partnerUser2,
            String partnerUser1_alias, String partnerUser2_alias, Date regDt, Date upateDt, Date meetDt) {
        this.partnerCd = partnerCd;
        this.partnerConnYn = partnerConnYn;
        this.partnerConnCd = partnerConnCd;
        this.partnerUser1 = partnerUser1;
        this.partnerUser2 = partnerUser2;
        this.partnerUser1_alias = partnerUser1_alias;
        this.partnerUser2_alias = partnerUser2_alias;
        this.regDt = regDt;
        this.upateDt = upateDt;
        this.meetDt = meetDt;
    }
}

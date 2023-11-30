package com.twojin.wooritheseday.user.entity;

import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import lombok.Data;
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
public class PartnerDTO {

    // 파트너 Cd
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long partnerCd;

    // 파트너 우효 상태
    @Convert(converter = BooleanToYNConverterUtil.class)
    boolean partnerConnYn;

    @Column
    String partnerConnCd; // 바트너 연결 코드

    @Column(name = "partner_user_1")
    String partnerUser1; // 연결된 사용자 1

    @Column(name = "partner_user_2")
    String partnerUser2; // 연결된 사용자 2

    @CreationTimestamp
    Date regDt; // 등록 날짜

    @UpdateTimestamp
    Date upateDt;

    Date meetDt; // 만난 날짜











}

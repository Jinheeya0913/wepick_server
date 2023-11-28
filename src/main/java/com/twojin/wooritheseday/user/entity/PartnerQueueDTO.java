package com.twojin.wooritheseday.user.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "woori_partner_queue")
@NoArgsConstructor
public class PartnerQueueDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ptQueCd;

    // 등록된 유저
    @Column(unique = true)
    private String ptRegUserId;

    // 등록 코드
    private String ptRegCd;

    // 대기 상태
    private String ptStatus;

    @Builder
    public PartnerQueueDTO(String pt_register_userId, String pt_register_registCd) {
        this.ptRegUserId = pt_register_userId;
        this.ptRegCd = pt_register_registCd;
        this.ptStatus = "Q";
    }


}

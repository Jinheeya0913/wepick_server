package com.twojin.wooritheseday.user.entity;

import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "woori_partner_regCd")
@NoArgsConstructor
public class PartnerTempQueDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ptTempCd;

    // 등록된 유저
    @Column(unique = true)
    private String ptTempUserId;

    // 등록 코드
    private String ptTempRegCd;

    // 대기 상태
    @Convert(converter = BooleanToYNConverterUtil.class)
    private boolean ptTempStatus;

    @UpdateTimestamp
    private Date updateDt;

    @CreationTimestamp
    private Date createDt;

    @Builder
    public PartnerTempQueDTO(String pt_register_userId, String pt_register_registCd) {
            this.ptTempUserId = pt_register_userId;
            this.ptTempRegCd = pt_register_registCd;
            this.ptTempStatus = true;
    }

}

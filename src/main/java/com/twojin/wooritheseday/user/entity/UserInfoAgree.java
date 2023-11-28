package com.twojin.wooritheseday.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "woori_user_info_agree")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoAgree {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long agreeId;

    @Column(nullable = false, unique = true)
    private Long userCd;

    @Column(nullable = false)
    private String agreeAt;
    @CreationTimestamp
    private Date agreeDate;

    @UpdateTimestamp
    private Date updateDate;

    @Builder
    public UserInfoAgree(Long agreeId, Long userCd, Date agreeDate, Date updateDate) {
        this.agreeId = agreeId;
        this.userCd = userCd;
        this.agreeDate = agreeDate;
        this.updateDate = updateDate;
    }
}

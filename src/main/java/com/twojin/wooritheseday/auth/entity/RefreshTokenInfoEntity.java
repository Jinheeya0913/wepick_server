package com.twojin.wooritheseday.auth.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name ="woori_Refresh_Token")
@Data
@NoArgsConstructor
public class RefreshTokenInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long refreshId;

    @Column(nullable = false, unique = true)
    @NotBlank
    String userId;

    @Column(nullable = false)
    @NotBlank
    String refreshToken;

    @UpdateTimestamp
    Date regDt;

    @Builder
    public RefreshTokenInfoEntity(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

}

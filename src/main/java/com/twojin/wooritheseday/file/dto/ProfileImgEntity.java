package com.twojin.wooritheseday.file.dto;


import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name ="woori_profile_img")
@Data
@NoArgsConstructor
public class ProfileImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long profileImgId;

    @Column(nullable = false, unique = true)
    @NotBlank
    String userId;

    @Column(nullable = false)
    @NotBlank
    String filePath;

    @Convert(converter = BooleanToYNConverterUtil.class)
    boolean useAt;

    @CreationTimestamp
    Date createDt;

    @UpdateTimestamp
    Date updateDt;

    @Builder
    public ProfileImgEntity(String userId, String filePath, boolean useAt) {
        this.userId = userId;
        this.filePath = filePath;
        this.useAt = useAt;
    }
}

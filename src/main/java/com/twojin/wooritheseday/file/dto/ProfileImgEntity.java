package com.twojin.wooritheseday.file.dto;


import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import com.twojin.wooritheseday.common.vo.FileEntity;
import lombok.AccessLevel;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImgEntity extends FileEntity {

    @Column(nullable = false)
    @NotBlank
    protected  String userId;

    @Convert(converter = BooleanToYNConverterUtil.class)
    protected boolean useAt;


    @Builder
    public ProfileImgEntity(String userId, String filePath, String fileName , String fileExtension, boolean useAt) {
        this.userId = userId;
        super.filePath = filePath;
        super.fileName = fileName;
        this.fileExtension = fileExtension;
        this.useAt = useAt;

    }
}

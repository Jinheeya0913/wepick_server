package com.twojin.wooritheseday.file.dto;

import com.twojin.wooritheseday.common.enums.ProductClass;
import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import com.twojin.wooritheseday.common.vo.FileEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "woori_review_img")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImgEntity extends FileEntity {

    @Column(nullable = false)
    @NotBlank
    protected String userId;

    @Enumerated(EnumType.STRING)
    protected ProductClass productClass;

    @Convert(converter = BooleanToYNConverterUtil.class)
    protected boolean useAt;


    @Builder
    public ReviewImgEntity(String userId, String filePath, String fileName, String fileExtension, boolean useAt, ProductClass productClass) {
        this.userId = userId;
        super.filePath = filePath;
        super.fileName = fileName;
        this.fileExtension = fileExtension;
        this.useAt = useAt;
        this.productClass = productClass;

    }


}

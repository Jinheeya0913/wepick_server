package com.twojin.wooritheseday.article.entity;

import com.twojin.wooritheseday.common.utils.BooleanToYNConverterUtil;
import com.twojin.wooritheseday.common.vo.FileEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ReviewImgsEntity extends FileEntity {

    private  String userId;

    private boolean useAt;


    public ReviewImgsEntity(String userId, boolean useAt) {
        this.userId = userId;
        this.useAt = useAt;
    }
}

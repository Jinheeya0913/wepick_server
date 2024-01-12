package com.twojin.wooritheseday.common.enums;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@ToString
public enum ProductClass {

    HALL_CLASS("HALL"),

    PLACE_CLASS("PLACE"),

    PACKAGE_CLASS("PACKAGE"),

    ;
    // END
    private final String className;

    // 생성자 구성
    ProductClass(final String className) {
        this.className = className;
    }
}

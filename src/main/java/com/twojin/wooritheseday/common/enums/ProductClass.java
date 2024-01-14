package com.twojin.wooritheseday.common.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ProductClass {

    HALL("HALL"),

    PLACE("PLACE"),

    PACKAGE("PACKAGE"),

    ;
    // END
    private final String className;

    // 생성자 구성
    ProductClass(final String className) {
        this.className = className;
    }
}

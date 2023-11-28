package com.twojin.wooritheseday.common.utils;

import javax.persistence.AttributeConverter;

public class BooleanToYNConverterUtil implements AttributeConverter<Boolean,String> {

    @Override
    public String convertToDatabaseColumn(Boolean aBoolean) {
        return (aBoolean !=null && aBoolean) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String s) {
        return "Y".equalsIgnoreCase(s);
    }
}

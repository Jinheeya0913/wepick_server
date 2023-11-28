package com.twojin.wooritheseday.user.util;

import com.twojin.wooritheseday.user.entity.PartnerQueueDTO;

import java.util.Random;

public class PartnerUtils {

    public static String  createPartnerQueueCode() {

        String randomCd = CustomStringUtils.createRandomString();
        return randomCd;
    }

}

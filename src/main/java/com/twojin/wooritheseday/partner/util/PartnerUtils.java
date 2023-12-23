package com.twojin.wooritheseday.partner.util;
import com.twojin.wooritheseday.partner.entity.PartnerMasterDTO;
import com.twojin.wooritheseday.user.util.StringUtils;

public class PartnerUtils {

    public static String  createPartnerQueueCode() {

        String randomCd = StringUtils.createRandomString();
        return randomCd;
    }

    public static void validExistProgressRequest() {

    }

    public static String getPartnerIdFromMasterDTO(PartnerMasterDTO masterDTO, String userId) {
        String partnerUser1 = masterDTO.getPartnerUser1();
        String partnerUser2 = masterDTO.getPartnerUser2();

        if (!partnerUser1.equals(userId) && partnerUser2.equals(userId)) {
            return masterDTO.getPartnerUser1();
        } else if (!partnerUser2.equals(userId) && partnerUser1.equals(userId)) {
            return masterDTO.getPartnerUser2();
        } else {
            return null;
        }
    }

    public static int getGubunNumberByUserId(PartnerMasterDTO masterDTO , String userId) {
        String partnerUser1 = masterDTO.getPartnerUser1();
        String partnerUser2 = masterDTO.getPartnerUser2();

        if (!partnerUser1.equals(userId)&& partnerUser2.equals(userId)) {
            return 1;
        } else if (partnerUser1.equals(userId) && !partnerUser2.equals(userId) ) {
            return 2;
        } else {
            return 0;
        }
    }

}

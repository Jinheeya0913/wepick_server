package com.twojin.wooritheseday.user.service;

import com.twojin.wooritheseday.user.entity.PartnerMaterDTO;
import com.twojin.wooritheseday.user.entity.PartnerTempQueDTO;
import com.twojin.wooritheseday.user.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.user.entity.UserDTO;

public interface PartnerService {

    PartnerMaterDTO getPartnerInfoByUserId(String userId);

    PartnerTempQueDTO registPartnerQueue(String userId);

    UserDTO selectPartnerQueueWithPtRegCd(String ptRegCd);

    public PartnerRequestQueueDTO requestPartner(PartnerRequestQueueDTO dto);

}

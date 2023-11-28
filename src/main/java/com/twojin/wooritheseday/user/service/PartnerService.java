package com.twojin.wooritheseday.user.service;

import com.twojin.wooritheseday.user.entity.PartnerQueueDTO;
import com.twojin.wooritheseday.user.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.user.entity.UserDTO;

public interface PartnerService {

    PartnerQueueDTO registPartnerQueue(String userId);

    UserDTO selectPartnerQueueWithPtRegCd(String ptRegCd);

    public PartnerRequestQueueDTO requestPartner(PartnerRequestQueueDTO dto);

}

package com.twojin.wooritheseday.partner.service;

import com.twojin.wooritheseday.partner.entity.PartnerMaterDTO;
import com.twojin.wooritheseday.partner.entity.PartnerTempQueDTO;
import com.twojin.wooritheseday.partner.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.user.entity.UserDTO;

public interface PartnerService {


    // 1. 내 파트너 조회
    PartnerMaterDTO getPartnerInfoByUserId(String userId);

    // 2. 파트너 코드 생성
    PartnerTempQueDTO createPartnerRegCd(String userId);


    // 3. 파트너 코드로 파트너 검색
    PartnerTempQueDTO selectPartnerQueueWithPtRegCd(String ptRegCd);

    // 4. 신청 전 : 검색된 파트너에 대한 신청 상태 검색
    PartnerRequestQueueDTO selectRequestStatusWithRequesterId(PartnerTempQueDTO tempQue, String requesterId);

    public PartnerRequestQueueDTO requestPartner(PartnerRequestQueueDTO dto);

}

package com.twojin.wooritheseday.partner.service;

import com.twojin.wooritheseday.partner.entity.PartnerTempQueDTO;
import com.twojin.wooritheseday.partner.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.partner.entity.vo.PartnerInfoVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PartnerService {


    // 1. 내 파트너 조회
    PartnerInfoVo getPartnerInfoByUserId(String userId);

    // 2. 파트너 코드 생성
    PartnerTempQueDTO createPartnerRegCd(String userId);


    // 3. 파트너 코드로 파트너 검색
    PartnerTempQueDTO selectPartnerQueueWithPtTempRegCd(String ptTempRegCd);

    /**
     * 4. 신청 전 : 검색된 파트너에 대한 신청 상태 검색
     * @param partnerTempQueDTO
     * @param requesterId
     * @return PartnerRequestQueueDTO
     */
    PartnerRequestQueueDTO selectRequestStatusWithRequesterId(PartnerTempQueDTO partnerTempQueDTO, String requesterId);

    /**
     *  5. 파트너 신청
     * @param dto
     * @param requesterId
     * @return true
     */
    public boolean registRequestPartner(PartnerTempQueDTO dto, String requesterId);


    /**
     * 6. 내 파트너 신청 목록 조회
     * @param acceptorId
     * @return List<PartnerRequestInfoVo>
     */
    public List<Map<String,Object>> selectAllMyRequestQueWithAcceptorId(String acceptorId);

    /**
     * 7. 파트너 요청 거절
     */

    public PartnerInfoVo acceptPartnerRequest(PartnerRequestQueueDTO queueDTO);

    /**
     * 8. 파트너 요청 수락
     */

    public boolean refusePartnerRequest( PartnerRequestQueueDTO queueDTO);

    /**
     * 9. 만난 날짜 변경
     */

    public PartnerInfoVo updatePartnerMeetDate(String userId, Date meetDt);

}

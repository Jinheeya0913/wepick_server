package com.twojin.wooritheseday.partner.service.impl;

import com.twojin.wooritheseday.auth.constant.ProgressConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.partner.entity.PartnerMaterDTO;
import com.twojin.wooritheseday.partner.entity.PartnerTempQueDTO;
import com.twojin.wooritheseday.partner.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.partner.repository.PartnerDtoRepository;
import com.twojin.wooritheseday.partner.repository.PartnerQueRepository;
import com.twojin.wooritheseday.partner.repository.PartnerReqQueueRepository;
import com.twojin.wooritheseday.user.repository.UserRepository;
import com.twojin.wooritheseday.partner.service.PartnerService;
import com.twojin.wooritheseday.partner.util.PartnerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("partnerService")
@Slf4j
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    PartnerQueRepository partnerQueRepository;

    @Autowired
    PartnerReqQueueRepository partnerReqQueueRepository;

    @Autowired
    PartnerDtoRepository partnerDtoRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public PartnerMaterDTO getPartnerInfoByUserId(String userId) {
        PartnerMaterDTO partnerMaterDTO = partnerDtoRepository.findByPartnerUser1OrPartnerUser2(userId, userId).orElse(null);
        return partnerMaterDTO;
    }

    // 2. 생성코드로 파트너 찾기
    @Override
    public PartnerTempQueDTO selectPartnerQueueWithPtRegCd(String ptRegCd) {
        log.debug("[partnerService] >> selectPartnerQueueWithPtRegCd :: 시작");
        log.debug("[partnerService] >> selectPartnerQueueWithPtRegCd :: ptRegCd : " + ptRegCd);


        PartnerTempQueDTO partnerTempQueDTO = partnerQueRepository.findByPtTempRegCd(ptRegCd)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_NON_EXIST.getMessage(), ErrorCode.PARTNER_REGIST_NON_EXIST));

        log.debug("[partnerService] >> selectPartnerQueueWithPtRegCd :: partnerTempQueDTO  " + partnerTempQueDTO.toString());


        return partnerTempQueDTO;
    }

    @Override
    public PartnerRequestQueueDTO selectRequestStatusWithRequesterId(PartnerTempQueDTO tempQue, String requesterId) {
        String tempUserId = tempQue.getPtTempUserId();
        PartnerRequestQueueDTO reqQue = partnerReqQueueRepository.findByPtRequesterIdAndPtAcceptorId(requesterId, tempUserId).orElse(null);

        if (reqQue != null) { // 필요한 정보만 리턴
            return PartnerRequestQueueDTO.builder()
                    .ptRequesterId(reqQue.getPtRequesterId())
                    .ptAcceptorId(reqQue.getPtAcceptorId())
                    .regDt(reqQue.getRegDt())
                    .updateDt(reqQue.getUpdateDt())
                    .ptReqStatus(reqQue.getPtReqStatus())
                    .build();
        }

        return reqQue;
    }


    @Override
    @Transactional
    public PartnerTempQueDTO createPartnerRegCd(String userId) {
        // Todo : Partner 2. 파트너 코드 생성하기
        String randomStr = PartnerUtils.createPartnerQueueCode();

        PartnerTempQueDTO partnerTempQueDTO = partnerQueRepository
                .findByPtTempUserId(userId)
                .orElse(
                        // 없을 경우 새로 만들어주고 랜덤키를 새로 부여
                        PartnerTempQueDTO.builder()
                                .pt_register_userId(userId)
                                .build()
                );
        PartnerTempQueDTO resultPartnerQueue = null;

        partnerTempQueDTO.setPtTempRegCd(randomStr);
        try {
            resultPartnerQueue = partnerQueRepository.save(partnerTempQueDTO);
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_QUEUE_FAIL.getMessage(), ErrorCode.PARTNER_REGIST_QUEUE_FAIL);
        }

        return resultPartnerQueue;
    }

    @Override
    public boolean registRequestPartner(PartnerTempQueDTO dto, String requesterId) {
        PartnerRequestQueueDTO requestQueueDTO = null;
        List<PartnerRequestQueueDTO> queList = null;
        String acceptorId = dto.getPtTempUserId();


        // 1. 요청을 보내기 전 요청이 진행 중인 건이 있는지 확인
        // - db에서 요청자와 대상자 데이터 조회

        queList = partnerReqQueueRepository.findAllByPtRequesterIdAndPtAcceptorId(requesterId, acceptorId);

        // 만약 누적 건이 있다면, 누적 처리 건 중에서 검증 진행
        if (queList.size() > 0) {
            for (PartnerRequestQueueDTO que : queList) {
                String requestStatus = que.getPtReqStatus();
                String requestTempCd = que.getPtTempCd();

                // 요청 처리 중인 건이 있으면
                if (requestStatus.equals(ProgressConstants.PROGRESSED)) {
                    throw new BusinessExceptionHandler(ErrorCode.PARTNER_REQUEST_PROGRESSED.getMessage(), ErrorCode.PARTNER_REQUEST_PROGRESSED);
                }

                // 같은 신청 코드로 접수된 건이 있으면
                if (requestTempCd.equals(dto.getPtTempRegCd())) {
                    throw new BusinessExceptionHandler(ErrorCode.PARTNER_REQUEST_USED_TEMPCD.getMessage(), ErrorCode.PARTNER_REQUEST_USED_TEMPCD);
                }

            }
        }

        partnerReqQueueRepository.save(PartnerRequestQueueDTO.builder()
                .ptRequesterId(requesterId)
                .ptAcceptorId(dto.getPtTempUserId())
                .ptReqStatus(ProgressConstants.PROGRESSED)
                .ptTempCd(dto.getPtTempRegCd())
                .build());

        return true;
    }


}

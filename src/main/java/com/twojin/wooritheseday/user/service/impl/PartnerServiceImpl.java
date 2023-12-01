package com.twojin.wooritheseday.user.service.impl;

import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.user.entity.PartnerMaterDTO;
import com.twojin.wooritheseday.user.entity.PartnerTempQueDTO;
import com.twojin.wooritheseday.user.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.repository.PartnerDtoRepository;
import com.twojin.wooritheseday.user.repository.PartnerQueRepository;
import com.twojin.wooritheseday.user.repository.PartnerReqQueueRepository;
import com.twojin.wooritheseday.user.repository.UserRepository;
import com.twojin.wooritheseday.user.service.PartnerService;
import com.twojin.wooritheseday.user.util.PartnerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    // Todo : Partner 4. ptcd로 파트너 조회하기
    @Override
    public UserDTO selectPartnerQueueWithPtRegCd(String ptRegCd) {
        PartnerTempQueDTO partnerTempQueDTO = partnerQueRepository.findByPtTempRegCd(ptRegCd)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_NON_EXIST.getMessage(), ErrorCode.PARTNER_REGIST_NON_EXIST));
        UserDTO userDTO = null;

        log.debug("selectPartnerQueueWithRegCd :: partnerQueueDTO :: " + partnerTempQueDTO.toString());

        if (
//                !"Q".equals(partnerTempQueDTO.getPtTempStatus()) 임시 처리
                true
        ) {
            throw new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_CANT_USE.getMessage(), ErrorCode.PARTNER_REGIST_CANT_USE);
        } else {
            userDTO = userRepository.findByUserId(partnerTempQueDTO.getPtTempUserId())
                    .orElseThrow(
                            () -> new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_NOT_FOUND.getMessage(), ErrorCode.PARTNER_REGIST_NOT_FOUND)
                    );
            log.debug("selectPartnerQueueWithRegCd :: userDTO :: " + userDTO.toString());
        }

        return userDTO;
    }

    @Override
    @Transactional
    public PartnerTempQueDTO registPartnerQueue(String userId) {
        // Todo : Partner 2. 생성하기
        String randomStr = PartnerUtils.createPartnerQueueCode();

        PartnerTempQueDTO partnerTempQueDTO = partnerQueRepository
                .findByPtTempUserId(userId)
                .orElse(
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
    public PartnerRequestQueueDTO requestPartner(PartnerRequestQueueDTO dto ) {
        // Todo : First(빌더 활용하여 save)
        dto.setPtReqStatus("Q");
        PartnerRequestQueueDTO req = partnerReqQueueRepository.save(dto);
        return req;
    }

    // Todo : Partner 3. 갱신하기
}

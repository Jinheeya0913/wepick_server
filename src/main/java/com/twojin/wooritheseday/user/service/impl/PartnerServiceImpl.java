package com.twojin.wooritheseday.user.service.impl;

import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.user.entity.PartnerQueueDTO;
import com.twojin.wooritheseday.user.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.user.entity.UserDTO;
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
    UserRepository userRepository;

    // Todo : Partner 4. ptcd로 파트너 조회하기
    @Override
    public UserDTO selectPartnerQueueWithPtRegCd(String ptRegCd) {
        PartnerQueueDTO partnerQueueDTO = partnerQueRepository.findByPtRegCd(ptRegCd)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_NON_EXIST.getMessage(), ErrorCode.PARTNER_REGIST_NON_EXIST));
        UserDTO userDTO = null;

        log.debug("selectPartnerQueueWithRegCd :: partnerQueueDTO :: " + partnerQueueDTO.toString());

        if (!"Q".equals(partnerQueueDTO.getPtStatus())) {
            throw new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_CANT_USE.getMessage(), ErrorCode.PARTNER_REGIST_CANT_USE);
        } else {
            userDTO = userRepository.findByUserId(partnerQueueDTO.getPtRegUserId())
                    .orElseThrow(
                            () -> new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_NOT_FOUND.getMessage(), ErrorCode.PARTNER_REGIST_NOT_FOUND)
                    );
            log.debug("selectPartnerQueueWithRegCd :: userDTO :: " + userDTO.toString());
        }

        return userDTO;
    }

    @Override
    @Transactional
    public PartnerQueueDTO registPartnerQueue(String userId) {
        // Todo : Partner 2. 생성하기
        String randomStr = PartnerUtils.createPartnerQueueCode();
        PartnerQueueDTO partnerQueueDTO = partnerQueRepository
                .findByPtRegUserId(userId)
                .orElse(
                        PartnerQueueDTO.builder()
                                .pt_register_userId(userId)
                                .build()
                );

        partnerQueueDTO.setPtRegCd(randomStr);

        PartnerQueueDTO resultPartnerQueue = partnerQueRepository.save(partnerQueueDTO);
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

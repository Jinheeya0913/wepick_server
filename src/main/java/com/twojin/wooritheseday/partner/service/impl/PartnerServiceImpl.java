package com.twojin.wooritheseday.partner.service.impl;

import com.twojin.wooritheseday.common.constant.ProgressConstants;
import com.twojin.wooritheseday.common.codes.ErrorCode;
import com.twojin.wooritheseday.common.utils.ConvertModules;
import com.twojin.wooritheseday.config.handler.BusinessExceptionHandler;
import com.twojin.wooritheseday.partner.entity.PartnerMasterDTO;
import com.twojin.wooritheseday.partner.entity.PartnerTempQueDTO;
import com.twojin.wooritheseday.partner.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.partner.entity.vo.PartnerInfoVo;
import com.twojin.wooritheseday.partner.entity.vo.PartnerRequestInfoVo;
import com.twojin.wooritheseday.partner.repository.PartnerMasterRepository;
import com.twojin.wooritheseday.partner.repository.PartnerQueRepository;
import com.twojin.wooritheseday.partner.repository.PartnerReqQueueRepository;
import com.twojin.wooritheseday.user.entity.UserDTO;
import com.twojin.wooritheseday.user.repository.UserRepository;
import com.twojin.wooritheseday.partner.service.PartnerService;
import com.twojin.wooritheseday.partner.util.PartnerUtils;
import com.twojin.wooritheseday.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("partnerService")
@Slf4j
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    PartnerQueRepository partnerQueRepository;

    @Autowired
    PartnerReqQueueRepository partnerReqQueueRepository;

    @Autowired
    PartnerMasterRepository partnerMasterRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public PartnerMasterDTO selectPartnerMasterInfo(String userId) {
        PartnerMasterDTO partnerMasterDTO = partnerMasterRepository.findMyPartnerInfoByUserId(userId)
                .orElseThrow(()->new BusinessExceptionHandler(ErrorCode.PARTNER_NOT_EXIST.getMessage(), ErrorCode.PARTNER_NOT_EXIST));

        return partnerMasterDTO;
    }

    @Override
    public PartnerInfoVo getPartnerInfoByUserId(String userId) {
        PartnerMasterDTO partnerMasterDTO = partnerMasterRepository.findMyPartnerInfoByUserId(userId).orElse(null);

        String partnerId = null;

        if(partnerMasterDTO == null) return null;

        if (!partnerMasterDTO.getPartnerUser1().equals(userId)) {
            partnerId = partnerMasterDTO.getPartnerUser1();
        } else {
            partnerId = partnerMasterDTO.getPartnerUser2();
        }

        UserDTO partnerInfo = userService.selectUserByUserId(partnerId);
        String partnerNm = userService.getUserNmByUserId(partnerId);

        if (partnerNm.isEmpty()) {
            throw new BusinessExceptionHandler(ErrorCode.PARTNER_FAILED.getMessage(), ErrorCode.PARTNER_FAILED);
        }
        log.debug("[getPartnerInfoByUserId]>> partnerInfo.toString " + partnerInfo.toString());


        PartnerInfoVo partnerInfoVo = PartnerInfoVo.builder()
                .partnerId(partnerInfo.getUserId())
                .partnerNm(partnerInfo.getUsername())
                .partnerImgUrl(partnerInfo.getUserImgUrl())
                .partnerConnCd(partnerMasterDTO.getPartnerConnCd())
                .partnerConnYn(partnerMasterDTO.isPartnerConnYn())
                .regDt(partnerMasterDTO.getRegDt())
                .build();

        if (partnerMasterDTO.getMeetDt() != null) {
            partnerInfoVo.setMeetDt(partnerMasterDTO.getMeetDt());
        }

        log.debug("[getPartnerInfoByUserId]>> partnerInfoVo.toString " + partnerInfoVo.toString());

        return partnerInfoVo;
    }

    // 2. 생성코드로 파트너 찾기
    @Override
    public PartnerTempQueDTO selectPartnerQueueWithPtTempRegCd(String ptRegCd) {
        log.debug("[partnerService] >> selectPartnerQueueWithPtRegCd :: 시작");
        log.debug("[partnerService] >> selectPartnerQueueWithPtRegCd :: ptRegCd : " + ptRegCd);


        PartnerTempQueDTO partnerTempQueDTO = partnerQueRepository.findByPtTempRegCd(ptRegCd)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_NON_EXIST.getMessage(), ErrorCode.PARTNER_REGIST_NON_EXIST));

        log.debug("[partnerService] >> selectPartnerQueueWithPtRegCd :: partnerTempQueDTO  " + partnerTempQueDTO.toString());


        return partnerTempQueDTO;
    }

    @Override
    public PartnerRequestQueueDTO selectRequestStatusWithRequesterId(PartnerTempQueDTO tempQue, String requesterId) {
        log.debug("[selectRequestStatusWithRequesterId] >> START");
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
        List<PartnerRequestQueueDTO> queList = null;
        String acceptorId = dto.getPtTempUserId();


        // 1. 요청을 보내기 전 요청이 진행 중인 건이 있는지 확인
        // - db에서 요청자와 대상자 데이터 조회

        queList = partnerReqQueueRepository.findAllByPtRequesterIdAndPtAcceptorId(requesterId, acceptorId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PARTNER_REQUEST_SELECT_FAIL.getMessage(), ErrorCode.PARTNER_REQUEST_SELECT_FAIL));

        // 만약 누적 건이 있다면, 누적 처리 건 중에서 검증 진행
        if (queList.size() > 0) {
            for (PartnerRequestQueueDTO que : queList) {

                log.error("[registRequestPartner] >> que :: " + que.toString());
                String requestStatus = que.getPtReqStatus();
                String requestTempCd = que.getPtTempCd();

                // 요청 처리 중인 건이 있으면
                if (requestStatus.equals(ProgressConstants.PROGRESSED)) {
                    log.error("[registRequestPartner] >> 요청 처리 중인 건으로 ERROR");
                    throw new BusinessExceptionHandler(ErrorCode.PARTNER_REQUEST_PROGRESSED.getMessage(), ErrorCode.PARTNER_REQUEST_PROGRESSED);
                }

                // 같은 신청 코드로 접수된 건이 있으면
                if (requestTempCd.equals(dto.getPtTempRegCd())) {
                    log.error("[registRequestPartner] >> 같은 신청 코드 접수");
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

    @Override
    public List<Map<String, Object>> selectAllMyRequestQueWithAcceptorId(String acceptorId) {
        log.debug("[selectAllMyRequestQueWithAcceptorId] >> START");
        List<PartnerRequestQueueDTO> queueDTOList = null;
        List<Map<String, Object>> mapList = new ArrayList<>();

        try {
             queueDTOList = partnerReqQueueRepository.findAllByPtAcceptorId(acceptorId);
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.PARTNER_REQUEST_SELECT_FAIL.getMessage(), ErrorCode.PARTNER_REQUEST_SELECT_FAIL);
        }
        List<PartnerRequestInfoVo> requestInfoList = new ArrayList<>();

        for (PartnerRequestQueueDTO requestInfo : queueDTOList) {
            Map<String, Object> objectMap = new HashMap<>();
            // 1. 거절 됐거나 만료됐으면 패스
            if (requestInfo.getPtReqStatus().equals(ProgressConstants.REFUESED)) {
                log.error("[selectAllMyRequestQueWithAcceptorId] >> continue1");
                continue;
            }

            log.debug("[selectAllMyRequestQueWithAcceptorId] >> 요청자 정보 조회");

            String userId = requestInfo.getPtRequesterId();
            UserDTO requesterInfo = userRepository.findByUserId(userId).orElse(null);

            log.debug("[selectAllMyRequestQueWithAcceptorId] >> 요청자 정보 : " + requesterInfo.toString());

            if(requesterInfo!= null)  requesterInfo = UserDTO.builder()
                        .userId(requesterInfo.getUserId())
                        .userNm(requesterInfo.getUserNm())
                        .userImgUrl(requesterInfo.getUserImgUrl())
                        .userEmail(requesterInfo.getUserEmail())
                        .userPhoneNum(requesterInfo.getUserPhoneNum())
                        .build();

            // 2. 비활성화된 사용자면 패스
            if (!requesterInfo.getUserUseAt().equals("Y")) {
                log.error("[selectAllMyRequestQueWithAcceptorId] >> continue2");
                continue;
            }
            JSONObject requestInfoObj = ConvertModules.dtoToJsonObj(requestInfo);
            JSONObject requesterInfoObj = ConvertModules.dtoToJsonObj(requesterInfo);

            objectMap.put("reqQueInfo", requestInfoObj);
            objectMap.put("partnerInfo", requesterInfoObj);


            mapList.add(objectMap);

        }


        return mapList;
    }

    @Override
    @Transactional
    public PartnerInfoVo acceptPartnerRequest(PartnerRequestQueueDTO queueDTO) {
        PartnerRequestQueueDTO requestQueueDTO = null;
        PartnerMasterDTO partnerMasterDTO = null;

        requestQueueDTO = partnerReqQueueRepository.findByPtTempCdAndPtRequesterId(queueDTO.getPtTempCd(), queueDTO.getPtRequesterId())
                .orElseThrow(()->new BusinessExceptionHandler(ErrorCode.PARTNER_REQUEST_SELECT_FAIL.getMessage(), ErrorCode.PARTNER_REQUEST_SELECT_FAIL));

        String ptReqStatus = requestQueueDTO.getPtReqStatus();

        if (!ptReqStatus.equals(ProgressConstants.PROGRESSED)) {
            throw new BusinessExceptionHandler(ErrorCode.BUSINESS_ALREADY_PROGRESSED.getMessage(), ErrorCode.BUSINESS_ALREADY_PROGRESSED);
        }

        String requesterId = queueDTO.getPtRequesterId();
        String acceptorId = queueDTO.getPtAcceptorId();

        // db에 등록돼 있는지 확인
        PartnerMasterDTO requesterInfo = partnerMasterRepository.findMyPartnerInfoByUserId(requesterId).orElse(null);
        PartnerMasterDTO acceptorInfo = partnerMasterRepository.findMyPartnerInfoByUserId(acceptorId).orElse(null);

        if (requesterInfo != null || acceptorInfo != null) {
            throw new BusinessExceptionHandler(ErrorCode.PARTNER_REGIST_IMPOSIBLE.getMessage(), ErrorCode.PARTNER_REGIST_IMPOSIBLE);
        }

        // 파트너 정보 가져오기
        UserDTO partnerInfo = userService.selectUserByUserId(requesterId);

        partnerMasterDTO = PartnerMasterDTO.builder()
                .partnerUser1(queueDTO.getPtAcceptorId())
                .partnerUser2(partnerInfo.getUserId())
                .partnerConnYn(true)
                .partnerConnCd(queueDTO.getPtTempCd())
                .build();

        partnerMasterDTO = partnerMasterRepository.save(partnerMasterDTO);
        requestQueueDTO.setPtReqStatus("ACCEPTED");


        return PartnerInfoVo.builder()
                .partnerNm(partnerInfo.getUserNm())
                .partnerImgUrl(partnerInfo.getUserImgUrl())
                .partnerId(partnerInfo.getUserId())
                .partnerConnYn(partnerMasterDTO.isPartnerConnYn())
                .partnerConnCd(partnerMasterDTO.getPartnerConnCd())
                .meetDt(partnerMasterDTO.getMeetDt())
                .regDt(partnerMasterDTO.getRegDt())
                .build();
    }

    @Override
    @Transactional
    public boolean refusePartnerRequest(PartnerRequestQueueDTO queueDTO) {

        PartnerRequestQueueDTO requestQueueDTO = null;
        requestQueueDTO = partnerReqQueueRepository.findByPtTempCdAndPtRequesterId(queueDTO.getPtTempCd(), queueDTO.getPtRequesterId())
                    .orElseThrow(()->new BusinessExceptionHandler(ErrorCode.PARTNER_REQUEST_SELECT_FAIL.getMessage(), ErrorCode.PARTNER_REQUEST_SELECT_FAIL));

        String ptReqStatus = requestQueueDTO.getPtReqStatus();

        if (!ptReqStatus.equals(ProgressConstants.PROGRESSED)) {
            throw new BusinessExceptionHandler(ErrorCode.BUSINESS_ALREADY_PROGRESSED.getMessage(), ErrorCode.BUSINESS_ALREADY_PROGRESSED);
        }

        requestQueueDTO.setPtReqStatus(ProgressConstants.REFUESED);
        return true;
    }

    @Transactional
    @Override
    public PartnerInfoVo updatePartnerMeetDate(String userId, Date meetDt) {
        UserDTO partnerDto = null;

        PartnerMasterDTO masterDto = partnerMasterRepository.findMyPartnerInfoByUserId(userId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PARTNER_FAILED.getMessage(), ErrorCode.PARTNER_FAILED));

        masterDto.setMeetDt(meetDt);

        String partnerId = PartnerUtils.getPartnerIdFromMasterDTO(masterDto, userId);

        if (partnerId != null) {
            partnerDto = userService.selectUserByUserId(partnerId);
            return PartnerInfoVo.initPartnerInfoVo(partnerDto, masterDto);
        } else {
            throw new BusinessExceptionHandler(ErrorCode.PARTNER_UPDATE_MEETDT.getMessage(), ErrorCode.PARTNER_UPDATE_MEETDT);
        }
    }
    @Override
    @Transactional
    public PartnerInfoVo updatePartnerAlias(String userId, String newAlias) {

        PartnerMasterDTO masterDTO= partnerMasterRepository.findMyPartnerInfoByUserId(userId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.PARTNER_NOT_EXIST.getMessage(), ErrorCode.PARTNER_NOT_EXIST));

        // 구분에 따른 파트너 별명 설정

        int partnerGubun = PartnerUtils.getGubunNumberByUserId(masterDTO, userId);
        log.debug("[PartnerServiceImpl] >> updatePartnerAlias :: partnerGubun =" + partnerGubun);
        masterDTO.setAliasByGubun(newAlias, partnerGubun);

        log.debug("[PartnerServiceImpl] >> updatePartnerAlias :: masterDto.toString" + masterDTO.toString());

        String partnerId = PartnerUtils.getPartnerIdFromMasterDTO(masterDTO, userId);
        UserDTO partnerInfo = null;

        try {
             partnerInfo = userService.selectUserByUserId(partnerId);
        } catch (BusinessExceptionHandler e) {
            throw new BusinessExceptionHandler(ErrorCode.PARTNER_UPDATE_ALIAS.getMessage(), ErrorCode.PARTNER_UPDATE_ALIAS);
        }

        return PartnerInfoVo.initPartnerInfoVo(partnerInfo, masterDTO);
    }


}

package com.twojin.wooritheseday.partner.repository;

import com.twojin.wooritheseday.partner.entity.PartnerRequestQueueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerReqQueueRepository extends JpaRepository<PartnerRequestQueueDTO,Long> {

    public Optional<PartnerRequestQueueDTO> findByPtRequesterIdAndPtAcceptorId(String requesterId, String acceptorIds);

//    public List <PartnerRequestQueueDTO> findAllByPtRequesterIdAndPtAcceptorId(String requesterId, String acceptorIds);
    public Optional<List <PartnerRequestQueueDTO>> findAllByPtRequesterIdAndPtAcceptorId(String requesterId, String acceptorIds);

    public List<PartnerRequestQueueDTO> findAllByPtAcceptorId(String acceptorId);

    public Optional<PartnerRequestQueueDTO> findByPtTempCdAndPtRequesterId(String ptTempCd,String requesterId);



}

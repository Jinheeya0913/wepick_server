package com.twojin.wooritheseday.partner.repository;

import com.twojin.wooritheseday.partner.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.partner.entity.PartnerTempQueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerReqQueueRepository extends JpaRepository<PartnerRequestQueueDTO,Long> {

    public Optional<PartnerRequestQueueDTO> findByPtRequesterIdAndPtAcceptorId(String requesterId, String acceptorIds);


}

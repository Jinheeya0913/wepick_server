package com.twojin.wooritheseday.partner.repository;

import com.twojin.wooritheseday.partner.entity.PartnerMasterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerMasterRepository extends JpaRepository<PartnerMasterDTO, Long> {
    @Query(value = "select dto from PartnerMasterDTO dto where dto.partnerUser1 =?1 or dto.partnerUser2=?1")
    public Optional<PartnerMasterDTO> findMyPartnerInfoByUserId(String partnepartner_user_1r_user1);

}

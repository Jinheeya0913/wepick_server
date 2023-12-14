package com.twojin.wooritheseday.partner.repository;

import com.twojin.wooritheseday.partner.entity.PartnerMaterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerDtoRepository extends JpaRepository<PartnerMaterDTO, Long> {
    @Query(value = "select dto from PartnerMaterDTO dto where dto.partnerUser1 =?1 or dto.partnerUser2=?1")
    public Optional<PartnerMaterDTO> findMyPartnerInfoByUserId(String partnepartner_user_1r_user1);

}

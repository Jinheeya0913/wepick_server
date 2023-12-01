package com.twojin.wooritheseday.user.repository;

import com.twojin.wooritheseday.user.entity.PartnerTempQueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerQueRepository extends JpaRepository<PartnerTempQueDTO,Long> {

    public Optional<PartnerTempQueDTO> findByPtTempUserId(String userId);
    public Optional<PartnerTempQueDTO> findByPtTempRegCd(String regCd);

}

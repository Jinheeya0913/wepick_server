package com.twojin.wooritheseday.user.repository;

import com.twojin.wooritheseday.user.entity.PartnerQueueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerQueRepository extends JpaRepository<PartnerQueueDTO,Long> {

    public Optional<PartnerQueueDTO> findByPtRegUserId(String userId);
    public Optional<PartnerQueueDTO> findByPtRegCd(String regCd);

}

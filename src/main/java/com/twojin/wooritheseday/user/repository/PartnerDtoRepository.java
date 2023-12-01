package com.twojin.wooritheseday.user.repository;

import com.twojin.wooritheseday.user.entity.PartnerMaterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerDtoRepository extends JpaRepository<PartnerMaterDTO, Long> {
    public Optional<PartnerMaterDTO> findByPartnerUser1OrPartnerUser2(String user1, String user2);

}

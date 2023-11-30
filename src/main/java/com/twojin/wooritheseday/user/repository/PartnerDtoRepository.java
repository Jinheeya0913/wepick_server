package com.twojin.wooritheseday.user.repository;

import com.twojin.wooritheseday.user.entity.PartnerDTO;
import com.twojin.wooritheseday.user.entity.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerDtoRepository extends JpaRepository<PartnerDTO, Long> {
    public Optional<PartnerDTO> findByPartnerUser1OrPartnerUser2(String user1, String user2);

}

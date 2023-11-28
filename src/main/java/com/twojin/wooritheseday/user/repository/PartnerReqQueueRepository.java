package com.twojin.wooritheseday.user.repository;

import com.twojin.wooritheseday.user.entity.PartnerRequestQueueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerReqQueueRepository extends JpaRepository<PartnerRequestQueueDTO,Long> {


}

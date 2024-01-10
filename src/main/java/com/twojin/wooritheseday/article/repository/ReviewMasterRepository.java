package com.twojin.wooritheseday.article.repository;

import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewMasterRepository extends JpaRepository<ReviewMasterDTO, Long> {
}

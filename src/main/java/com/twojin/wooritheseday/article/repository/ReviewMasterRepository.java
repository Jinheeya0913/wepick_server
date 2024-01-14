package com.twojin.wooritheseday.article.repository;

import com.twojin.wooritheseday.article.entity.ReviewMasterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewMasterRepository extends JpaRepository<ReviewMasterDTO, Long> {

//    Optional<List<ReviewMasterDTO>> findAllByByProductClassOrderByRegistDtDesc(String productClass);

    Optional<List<ReviewMasterDTO>> findAllByUseAtOrderByRegistDtDesc(boolean useAt);
}

package com.twojin.wooritheseday.file.repository;

import com.twojin.wooritheseday.file.dto.ReviewImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewImgInfoRepository extends JpaRepository<ReviewImgEntity,Long> {

    Optional<ReviewImgEntity> findByFileName(String fileName);


}

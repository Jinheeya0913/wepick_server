package com.twojin.wooritheseday.file.repository;

import com.twojin.wooritheseday.file.dto.ProfileImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImgInfoRepository extends JpaRepository<ProfileImgEntity, Long> {


}

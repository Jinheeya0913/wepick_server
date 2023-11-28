package com.twojin.wooritheseday.file.repository;

import com.twojin.wooritheseday.file.dto.ProfileImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileImgInfoRepository extends JpaRepository<ProfileImgEntity, Long> {

    public Optional<ProfileImgEntity> findByFileName(String fileName);

    public Optional<ProfileImgEntity> findByUserId(String userId);


}

package com.twojin.wooritheseday.auth.repository;

import com.twojin.wooritheseday.auth.entity.RefreshTokenInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<RefreshTokenInfoEntity, Long> {

    RefreshTokenInfoEntity findByUserId(String userId);

//    @Query("UPDATE RefreshTokenInfoEntity SET refreshToken=:refreshToken where userId =:userId")
//    RefreshTokenInfoEntity updateRefreshToken(RefreshTokenInfoEntity refreshEntity);
}

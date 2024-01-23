package com.twojin.wooritheseday.user.repository;

import com.twojin.wooritheseday.user.entity.MyFavoriteDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavoriteRepository extends JpaRepository<MyFavoriteDTO,Long> {

}

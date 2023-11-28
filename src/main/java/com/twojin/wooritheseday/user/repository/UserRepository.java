package com.twojin.wooritheseday.user.repository;


import com.twojin.wooritheseday.user.entity.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDTO,Long> {

    public Optional<UserDTO> findByUserId(String userId);
    public Optional<UserDTO> findByUserNmAndUserPhoneNum(String userNm, String userPhoneNum);

    public Optional<UserDTO> findByUserIdAndUserPw(String userId, String userPw);


}

package com.twojin.wooritheseday.user.repository;

import com.twojin.wooritheseday.user.entity.MyReviewLikeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserReviewLikeRepository extends JpaRepository<MyReviewLikeDTO,Long> {

    Optional<List<MyReviewLikeDTO>> findAllByUserId(String userId);

    Optional<MyReviewLikeDTO> findByUserIdAndReviewArticleCd(String userId, Long reviewArticleCd);
}

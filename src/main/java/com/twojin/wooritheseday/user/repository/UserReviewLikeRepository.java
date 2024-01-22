package com.twojin.wooritheseday.user.repository;

import com.twojin.wooritheseday.user.entity.MyReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserReviewLikeRepository extends JpaRepository<MyReviewLike,Long> {

    Optional<List<MyReviewLike>> findAllByUserId(String userId);

    Optional<MyReviewLike> findByUserIdAndReviewArticleCd(String userId, Long reviewArticleCd);
}

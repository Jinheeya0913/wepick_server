package com.twojin.wooritheseday.article.repository;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewHallRepository extends JpaRepository<ReviewHallDTO, Long> {




}
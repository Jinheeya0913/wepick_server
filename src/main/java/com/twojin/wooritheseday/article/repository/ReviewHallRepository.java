package com.twojin.wooritheseday.article.repository;

import com.twojin.wooritheseday.article.entity.ReviewHallDTO;
import com.twojin.wooritheseday.product.entity.PlaceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewHallRepository extends JpaRepository<ReviewHallDTO, Long> {

    Optional<ReviewHallDTO> findByUserIdAndAndPlaceInfo_placeCd(String userId, Long placeCd);

    Optional<List<ReviewHallDTO>> findByUseAtOrderByRegistDtDesc(boolean useAt);

    Optional<ReviewHallDTO> findByReviewArticleCd(Long reviewArticleCd);


}

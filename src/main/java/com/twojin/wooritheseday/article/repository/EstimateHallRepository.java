package com.twojin.wooritheseday.article.repository;

import com.twojin.wooritheseday.article.entity.EstimateHallDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstimateHallRepository extends JpaRepository<EstimateHallDTO, Long> {

    public Optional<EstimateHallDTO> findByWriterIdAndPlaceInfo_placeCd(String writerId, Long placeCd);

}

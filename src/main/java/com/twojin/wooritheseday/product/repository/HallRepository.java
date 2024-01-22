package com.twojin.wooritheseday.product.repository;

import com.twojin.wooritheseday.product.entity.HallDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<HallDTO, Long> {

    Optional<List<HallDTO>> findAllByPlaceDTO_PlaceCdAndUseAt(Long placeCd, boolean useAt);

}

package com.twojin.wooritheseday.product.repository;

import com.twojin.wooritheseday.product.entity.PlaceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceDTO, Long> {

    Optional<List<PlaceDTO>> findAllByUseAt(boolean useAt);


}

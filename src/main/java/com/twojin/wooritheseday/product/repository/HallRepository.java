package com.twojin.wooritheseday.product.repository;

import com.twojin.wooritheseday.product.entity.HallDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HallRepository extends JpaRepository<HallDTO, Long> {

    Optional<List<HallDTO>> findAllByUseAt(boolean useAt);

}

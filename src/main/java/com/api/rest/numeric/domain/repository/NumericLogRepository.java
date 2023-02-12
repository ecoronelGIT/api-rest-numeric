package com.api.rest.numeric.domain.repository;

import com.api.rest.numeric.domain.model.NumericLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NumericLogRepository extends JpaRepository<NumericLogEntity, Long>,
    PagingAndSortingRepository<NumericLogEntity, Long> {


}

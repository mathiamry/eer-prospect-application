package com.cbao.eerprospect.repository;

import com.cbao.eerprospect.domain.IncomePeriodicity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IncomePeriodicity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomePeriodicityRepository extends JpaRepository<IncomePeriodicity, Long> {}

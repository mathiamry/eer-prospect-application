package com.cbao.eerprospect.repository;

import com.cbao.eerprospect.domain.IncomeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IncomeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {}

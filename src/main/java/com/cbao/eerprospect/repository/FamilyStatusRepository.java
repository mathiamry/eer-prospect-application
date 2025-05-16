package com.cbao.eerprospect.repository;

import com.cbao.eerprospect.domain.FamilyStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FamilyStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyStatusRepository extends JpaRepository<FamilyStatus, Long> {}

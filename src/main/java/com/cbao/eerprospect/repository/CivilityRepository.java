package com.cbao.eerprospect.repository;

import com.cbao.eerprospect.domain.Civility;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Civility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CivilityRepository extends JpaRepository<Civility, Long> {}

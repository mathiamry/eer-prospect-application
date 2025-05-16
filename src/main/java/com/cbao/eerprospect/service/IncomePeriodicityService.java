package com.cbao.eerprospect.service;

import com.cbao.eerprospect.domain.IncomePeriodicity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cbao.eerprospect.domain.IncomePeriodicity}.
 */
public interface IncomePeriodicityService {
    /**
     * Save a incomePeriodicity.
     *
     * @param incomePeriodicity the entity to save.
     * @return the persisted entity.
     */
    IncomePeriodicity save(IncomePeriodicity incomePeriodicity);

    /**
     * Updates a incomePeriodicity.
     *
     * @param incomePeriodicity the entity to update.
     * @return the persisted entity.
     */
    IncomePeriodicity update(IncomePeriodicity incomePeriodicity);

    /**
     * Partially updates a incomePeriodicity.
     *
     * @param incomePeriodicity the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IncomePeriodicity> partialUpdate(IncomePeriodicity incomePeriodicity);

    /**
     * Get all the incomePeriodicities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncomePeriodicity> findAll(Pageable pageable);

    /**
     * Get the "id" incomePeriodicity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncomePeriodicity> findOne(Long id);

    /**
     * Delete the "id" incomePeriodicity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

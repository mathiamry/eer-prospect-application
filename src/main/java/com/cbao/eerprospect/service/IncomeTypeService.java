package com.cbao.eerprospect.service;

import com.cbao.eerprospect.domain.IncomeType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cbao.eerprospect.domain.IncomeType}.
 */
public interface IncomeTypeService {
    /**
     * Save a incomeType.
     *
     * @param incomeType the entity to save.
     * @return the persisted entity.
     */
    IncomeType save(IncomeType incomeType);

    /**
     * Updates a incomeType.
     *
     * @param incomeType the entity to update.
     * @return the persisted entity.
     */
    IncomeType update(IncomeType incomeType);

    /**
     * Partially updates a incomeType.
     *
     * @param incomeType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IncomeType> partialUpdate(IncomeType incomeType);

    /**
     * Get all the incomeTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncomeType> findAll(Pageable pageable);

    /**
     * Get the "id" incomeType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncomeType> findOne(Long id);

    /**
     * Delete the "id" incomeType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

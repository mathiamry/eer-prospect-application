package com.cbao.eerprospect.service;

import com.cbao.eerprospect.domain.FamilyStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cbao.eerprospect.domain.FamilyStatus}.
 */
public interface FamilyStatusService {
    /**
     * Save a familyStatus.
     *
     * @param familyStatus the entity to save.
     * @return the persisted entity.
     */
    FamilyStatus save(FamilyStatus familyStatus);

    /**
     * Updates a familyStatus.
     *
     * @param familyStatus the entity to update.
     * @return the persisted entity.
     */
    FamilyStatus update(FamilyStatus familyStatus);

    /**
     * Partially updates a familyStatus.
     *
     * @param familyStatus the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FamilyStatus> partialUpdate(FamilyStatus familyStatus);

    /**
     * Get all the familyStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FamilyStatus> findAll(Pageable pageable);

    /**
     * Get the "id" familyStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FamilyStatus> findOne(Long id);

    /**
     * Delete the "id" familyStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package com.cbao.eerprospect.service;

import com.cbao.eerprospect.domain.Civility;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cbao.eerprospect.domain.Civility}.
 */
public interface CivilityService {
    /**
     * Save a civility.
     *
     * @param civility the entity to save.
     * @return the persisted entity.
     */
    Civility save(Civility civility);

    /**
     * Updates a civility.
     *
     * @param civility the entity to update.
     * @return the persisted entity.
     */
    Civility update(Civility civility);

    /**
     * Partially updates a civility.
     *
     * @param civility the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Civility> partialUpdate(Civility civility);

    /**
     * Get all the civilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Civility> findAll(Pageable pageable);

    /**
     * Get the "id" civility.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Civility> findOne(Long id);

    /**
     * Delete the "id" civility.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

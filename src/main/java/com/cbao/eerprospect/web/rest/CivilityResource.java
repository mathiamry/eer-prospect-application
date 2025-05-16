package com.cbao.eerprospect.web.rest;

import com.cbao.eerprospect.domain.Civility;
import com.cbao.eerprospect.repository.CivilityRepository;
import com.cbao.eerprospect.service.CivilityService;
import com.cbao.eerprospect.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbao.eerprospect.domain.Civility}.
 */
@RestController
@RequestMapping("/api/civilities")
public class CivilityResource {

    private static final Logger LOG = LoggerFactory.getLogger(CivilityResource.class);

    private static final String ENTITY_NAME = "civility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CivilityService civilityService;

    private final CivilityRepository civilityRepository;

    public CivilityResource(CivilityService civilityService, CivilityRepository civilityRepository) {
        this.civilityService = civilityService;
        this.civilityRepository = civilityRepository;
    }

    /**
     * {@code POST  /civilities} : Create a new civility.
     *
     * @param civility the civility to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new civility, or with status {@code 400 (Bad Request)} if the civility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Civility> createCivility(@RequestBody Civility civility) throws URISyntaxException {
        LOG.debug("REST request to save Civility : {}", civility);
        if (civility.getId() != null) {
            throw new BadRequestAlertException("A new civility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        civility = civilityService.save(civility);
        return ResponseEntity.created(new URI("/api/civilities/" + civility.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, civility.getId().toString()))
            .body(civility);
    }

    /**
     * {@code PUT  /civilities/:id} : Updates an existing civility.
     *
     * @param id the id of the civility to save.
     * @param civility the civility to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated civility,
     * or with status {@code 400 (Bad Request)} if the civility is not valid,
     * or with status {@code 500 (Internal Server Error)} if the civility couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Civility> updateCivility(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Civility civility
    ) throws URISyntaxException {
        LOG.debug("REST request to update Civility : {}, {}", id, civility);
        if (civility.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, civility.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!civilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        civility = civilityService.update(civility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, civility.getId().toString()))
            .body(civility);
    }

    /**
     * {@code PATCH  /civilities/:id} : Partial updates given fields of an existing civility, field will ignore if it is null
     *
     * @param id the id of the civility to save.
     * @param civility the civility to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated civility,
     * or with status {@code 400 (Bad Request)} if the civility is not valid,
     * or with status {@code 404 (Not Found)} if the civility is not found,
     * or with status {@code 500 (Internal Server Error)} if the civility couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Civility> partialUpdateCivility(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Civility civility
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Civility partially : {}, {}", id, civility);
        if (civility.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, civility.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!civilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Civility> result = civilityService.partialUpdate(civility);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, civility.getId().toString())
        );
    }

    /**
     * {@code GET  /civilities} : get all the civilities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of civilities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Civility>> getAllCivilities(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Civilities");
        Page<Civility> page = civilityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /civilities/:id} : get the "id" civility.
     *
     * @param id the id of the civility to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the civility, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Civility> getCivility(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Civility : {}", id);
        Optional<Civility> civility = civilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(civility);
    }

    /**
     * {@code DELETE  /civilities/:id} : delete the "id" civility.
     *
     * @param id the id of the civility to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCivility(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Civility : {}", id);
        civilityService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

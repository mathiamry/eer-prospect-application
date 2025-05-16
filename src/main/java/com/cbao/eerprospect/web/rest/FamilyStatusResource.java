package com.cbao.eerprospect.web.rest;

import com.cbao.eerprospect.domain.FamilyStatus;
import com.cbao.eerprospect.repository.FamilyStatusRepository;
import com.cbao.eerprospect.service.FamilyStatusService;
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
 * REST controller for managing {@link com.cbao.eerprospect.domain.FamilyStatus}.
 */
@RestController
@RequestMapping("/api/family-statuses")
public class FamilyStatusResource {

    private static final Logger LOG = LoggerFactory.getLogger(FamilyStatusResource.class);

    private static final String ENTITY_NAME = "familyStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyStatusService familyStatusService;

    private final FamilyStatusRepository familyStatusRepository;

    public FamilyStatusResource(FamilyStatusService familyStatusService, FamilyStatusRepository familyStatusRepository) {
        this.familyStatusService = familyStatusService;
        this.familyStatusRepository = familyStatusRepository;
    }

    /**
     * {@code POST  /family-statuses} : Create a new familyStatus.
     *
     * @param familyStatus the familyStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyStatus, or with status {@code 400 (Bad Request)} if the familyStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FamilyStatus> createFamilyStatus(@RequestBody FamilyStatus familyStatus) throws URISyntaxException {
        LOG.debug("REST request to save FamilyStatus : {}", familyStatus);
        if (familyStatus.getId() != null) {
            throw new BadRequestAlertException("A new familyStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        familyStatus = familyStatusService.save(familyStatus);
        return ResponseEntity.created(new URI("/api/family-statuses/" + familyStatus.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, familyStatus.getId().toString()))
            .body(familyStatus);
    }

    /**
     * {@code PUT  /family-statuses/:id} : Updates an existing familyStatus.
     *
     * @param id the id of the familyStatus to save.
     * @param familyStatus the familyStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyStatus,
     * or with status {@code 400 (Bad Request)} if the familyStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FamilyStatus> updateFamilyStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FamilyStatus familyStatus
    ) throws URISyntaxException {
        LOG.debug("REST request to update FamilyStatus : {}, {}", id, familyStatus);
        if (familyStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familyStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familyStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        familyStatus = familyStatusService.update(familyStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyStatus.getId().toString()))
            .body(familyStatus);
    }

    /**
     * {@code PATCH  /family-statuses/:id} : Partial updates given fields of an existing familyStatus, field will ignore if it is null
     *
     * @param id the id of the familyStatus to save.
     * @param familyStatus the familyStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyStatus,
     * or with status {@code 400 (Bad Request)} if the familyStatus is not valid,
     * or with status {@code 404 (Not Found)} if the familyStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the familyStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FamilyStatus> partialUpdateFamilyStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FamilyStatus familyStatus
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FamilyStatus partially : {}, {}", id, familyStatus);
        if (familyStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, familyStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!familyStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FamilyStatus> result = familyStatusService.partialUpdate(familyStatus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /family-statuses} : get all the familyStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familyStatuses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FamilyStatus>> getAllFamilyStatuses(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of FamilyStatuses");
        Page<FamilyStatus> page = familyStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /family-statuses/:id} : get the "id" familyStatus.
     *
     * @param id the id of the familyStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FamilyStatus> getFamilyStatus(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FamilyStatus : {}", id);
        Optional<FamilyStatus> familyStatus = familyStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familyStatus);
    }

    /**
     * {@code DELETE  /family-statuses/:id} : delete the "id" familyStatus.
     *
     * @param id the id of the familyStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamilyStatus(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FamilyStatus : {}", id);
        familyStatusService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

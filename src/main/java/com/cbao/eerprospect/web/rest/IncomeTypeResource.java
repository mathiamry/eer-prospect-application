package com.cbao.eerprospect.web.rest;

import com.cbao.eerprospect.domain.IncomeType;
import com.cbao.eerprospect.repository.IncomeTypeRepository;
import com.cbao.eerprospect.service.IncomeTypeService;
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
 * REST controller for managing {@link com.cbao.eerprospect.domain.IncomeType}.
 */
@RestController
@RequestMapping("/api/income-types")
public class IncomeTypeResource {

    private static final Logger LOG = LoggerFactory.getLogger(IncomeTypeResource.class);

    private static final String ENTITY_NAME = "incomeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomeTypeService incomeTypeService;

    private final IncomeTypeRepository incomeTypeRepository;

    public IncomeTypeResource(IncomeTypeService incomeTypeService, IncomeTypeRepository incomeTypeRepository) {
        this.incomeTypeService = incomeTypeService;
        this.incomeTypeRepository = incomeTypeRepository;
    }

    /**
     * {@code POST  /income-types} : Create a new incomeType.
     *
     * @param incomeType the incomeType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incomeType, or with status {@code 400 (Bad Request)} if the incomeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IncomeType> createIncomeType(@RequestBody IncomeType incomeType) throws URISyntaxException {
        LOG.debug("REST request to save IncomeType : {}", incomeType);
        if (incomeType.getId() != null) {
            throw new BadRequestAlertException("A new incomeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        incomeType = incomeTypeService.save(incomeType);
        return ResponseEntity.created(new URI("/api/income-types/" + incomeType.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, incomeType.getId().toString()))
            .body(incomeType);
    }

    /**
     * {@code PUT  /income-types/:id} : Updates an existing incomeType.
     *
     * @param id the id of the incomeType to save.
     * @param incomeType the incomeType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomeType,
     * or with status {@code 400 (Bad Request)} if the incomeType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incomeType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IncomeType> updateIncomeType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IncomeType incomeType
    ) throws URISyntaxException {
        LOG.debug("REST request to update IncomeType : {}, {}", id, incomeType);
        if (incomeType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, incomeType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!incomeTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        incomeType = incomeTypeService.update(incomeType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incomeType.getId().toString()))
            .body(incomeType);
    }

    /**
     * {@code PATCH  /income-types/:id} : Partial updates given fields of an existing incomeType, field will ignore if it is null
     *
     * @param id the id of the incomeType to save.
     * @param incomeType the incomeType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomeType,
     * or with status {@code 400 (Bad Request)} if the incomeType is not valid,
     * or with status {@code 404 (Not Found)} if the incomeType is not found,
     * or with status {@code 500 (Internal Server Error)} if the incomeType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IncomeType> partialUpdateIncomeType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IncomeType incomeType
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update IncomeType partially : {}, {}", id, incomeType);
        if (incomeType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, incomeType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!incomeTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IncomeType> result = incomeTypeService.partialUpdate(incomeType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incomeType.getId().toString())
        );
    }

    /**
     * {@code GET  /income-types} : get all the incomeTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomeTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<IncomeType>> getAllIncomeTypes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of IncomeTypes");
        Page<IncomeType> page = incomeTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /income-types/:id} : get the "id" incomeType.
     *
     * @param id the id of the incomeType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incomeType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IncomeType> getIncomeType(@PathVariable("id") Long id) {
        LOG.debug("REST request to get IncomeType : {}", id);
        Optional<IncomeType> incomeType = incomeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incomeType);
    }

    /**
     * {@code DELETE  /income-types/:id} : delete the "id" incomeType.
     *
     * @param id the id of the incomeType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncomeType(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete IncomeType : {}", id);
        incomeTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

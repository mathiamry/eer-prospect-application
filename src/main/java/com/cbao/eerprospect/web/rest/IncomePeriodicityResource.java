package com.cbao.eerprospect.web.rest;

import com.cbao.eerprospect.domain.IncomePeriodicity;
import com.cbao.eerprospect.repository.IncomePeriodicityRepository;
import com.cbao.eerprospect.service.IncomePeriodicityService;
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
 * REST controller for managing {@link com.cbao.eerprospect.domain.IncomePeriodicity}.
 */
@RestController
@RequestMapping("/api/income-periodicities")
public class IncomePeriodicityResource {

    private static final Logger LOG = LoggerFactory.getLogger(IncomePeriodicityResource.class);

    private static final String ENTITY_NAME = "incomePeriodicity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomePeriodicityService incomePeriodicityService;

    private final IncomePeriodicityRepository incomePeriodicityRepository;

    public IncomePeriodicityResource(
        IncomePeriodicityService incomePeriodicityService,
        IncomePeriodicityRepository incomePeriodicityRepository
    ) {
        this.incomePeriodicityService = incomePeriodicityService;
        this.incomePeriodicityRepository = incomePeriodicityRepository;
    }

    /**
     * {@code POST  /income-periodicities} : Create a new incomePeriodicity.
     *
     * @param incomePeriodicity the incomePeriodicity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incomePeriodicity, or with status {@code 400 (Bad Request)} if the incomePeriodicity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IncomePeriodicity> createIncomePeriodicity(@RequestBody IncomePeriodicity incomePeriodicity)
        throws URISyntaxException {
        LOG.debug("REST request to save IncomePeriodicity : {}", incomePeriodicity);
        if (incomePeriodicity.getId() != null) {
            throw new BadRequestAlertException("A new incomePeriodicity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        incomePeriodicity = incomePeriodicityService.save(incomePeriodicity);
        return ResponseEntity.created(new URI("/api/income-periodicities/" + incomePeriodicity.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, incomePeriodicity.getId().toString()))
            .body(incomePeriodicity);
    }

    /**
     * {@code PUT  /income-periodicities/:id} : Updates an existing incomePeriodicity.
     *
     * @param id the id of the incomePeriodicity to save.
     * @param incomePeriodicity the incomePeriodicity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomePeriodicity,
     * or with status {@code 400 (Bad Request)} if the incomePeriodicity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incomePeriodicity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IncomePeriodicity> updateIncomePeriodicity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IncomePeriodicity incomePeriodicity
    ) throws URISyntaxException {
        LOG.debug("REST request to update IncomePeriodicity : {}, {}", id, incomePeriodicity);
        if (incomePeriodicity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, incomePeriodicity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!incomePeriodicityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        incomePeriodicity = incomePeriodicityService.update(incomePeriodicity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incomePeriodicity.getId().toString()))
            .body(incomePeriodicity);
    }

    /**
     * {@code PATCH  /income-periodicities/:id} : Partial updates given fields of an existing incomePeriodicity, field will ignore if it is null
     *
     * @param id the id of the incomePeriodicity to save.
     * @param incomePeriodicity the incomePeriodicity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomePeriodicity,
     * or with status {@code 400 (Bad Request)} if the incomePeriodicity is not valid,
     * or with status {@code 404 (Not Found)} if the incomePeriodicity is not found,
     * or with status {@code 500 (Internal Server Error)} if the incomePeriodicity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IncomePeriodicity> partialUpdateIncomePeriodicity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IncomePeriodicity incomePeriodicity
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update IncomePeriodicity partially : {}, {}", id, incomePeriodicity);
        if (incomePeriodicity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, incomePeriodicity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!incomePeriodicityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IncomePeriodicity> result = incomePeriodicityService.partialUpdate(incomePeriodicity);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incomePeriodicity.getId().toString())
        );
    }

    /**
     * {@code GET  /income-periodicities} : get all the incomePeriodicities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomePeriodicities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<IncomePeriodicity>> getAllIncomePeriodicities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of IncomePeriodicities");
        Page<IncomePeriodicity> page = incomePeriodicityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /income-periodicities/:id} : get the "id" incomePeriodicity.
     *
     * @param id the id of the incomePeriodicity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incomePeriodicity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IncomePeriodicity> getIncomePeriodicity(@PathVariable("id") Long id) {
        LOG.debug("REST request to get IncomePeriodicity : {}", id);
        Optional<IncomePeriodicity> incomePeriodicity = incomePeriodicityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incomePeriodicity);
    }

    /**
     * {@code DELETE  /income-periodicities/:id} : delete the "id" incomePeriodicity.
     *
     * @param id the id of the incomePeriodicity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncomePeriodicity(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete IncomePeriodicity : {}", id);
        incomePeriodicityService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

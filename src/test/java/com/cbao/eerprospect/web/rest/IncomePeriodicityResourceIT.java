package com.cbao.eerprospect.web.rest;

import static com.cbao.eerprospect.domain.IncomePeriodicityAsserts.*;
import static com.cbao.eerprospect.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbao.eerprospect.IntegrationTest;
import com.cbao.eerprospect.domain.IncomePeriodicity;
import com.cbao.eerprospect.repository.IncomePeriodicityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IncomePeriodicityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IncomePeriodicityResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/income-periodicities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IncomePeriodicityRepository incomePeriodicityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncomePeriodicityMockMvc;

    private IncomePeriodicity incomePeriodicity;

    private IncomePeriodicity insertedIncomePeriodicity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomePeriodicity createEntity() {
        return new IncomePeriodicity().code(DEFAULT_CODE).label(DEFAULT_LABEL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomePeriodicity createUpdatedEntity() {
        return new IncomePeriodicity().code(UPDATED_CODE).label(UPDATED_LABEL);
    }

    @BeforeEach
    void initTest() {
        incomePeriodicity = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedIncomePeriodicity != null) {
            incomePeriodicityRepository.delete(insertedIncomePeriodicity);
            insertedIncomePeriodicity = null;
        }
    }

    @Test
    @Transactional
    void createIncomePeriodicity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IncomePeriodicity
        var returnedIncomePeriodicity = om.readValue(
            restIncomePeriodicityMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incomePeriodicity)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IncomePeriodicity.class
        );

        // Validate the IncomePeriodicity in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIncomePeriodicityUpdatableFieldsEquals(returnedIncomePeriodicity, getPersistedIncomePeriodicity(returnedIncomePeriodicity));

        insertedIncomePeriodicity = returnedIncomePeriodicity;
    }

    @Test
    @Transactional
    void createIncomePeriodicityWithExistingId() throws Exception {
        // Create the IncomePeriodicity with an existing ID
        incomePeriodicity.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomePeriodicityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incomePeriodicity)))
            .andExpect(status().isBadRequest());

        // Validate the IncomePeriodicity in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIncomePeriodicities() throws Exception {
        // Initialize the database
        insertedIncomePeriodicity = incomePeriodicityRepository.saveAndFlush(incomePeriodicity);

        // Get all the incomePeriodicityList
        restIncomePeriodicityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomePeriodicity.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getIncomePeriodicity() throws Exception {
        // Initialize the database
        insertedIncomePeriodicity = incomePeriodicityRepository.saveAndFlush(incomePeriodicity);

        // Get the incomePeriodicity
        restIncomePeriodicityMockMvc
            .perform(get(ENTITY_API_URL_ID, incomePeriodicity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incomePeriodicity.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingIncomePeriodicity() throws Exception {
        // Get the incomePeriodicity
        restIncomePeriodicityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIncomePeriodicity() throws Exception {
        // Initialize the database
        insertedIncomePeriodicity = incomePeriodicityRepository.saveAndFlush(incomePeriodicity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the incomePeriodicity
        IncomePeriodicity updatedIncomePeriodicity = incomePeriodicityRepository.findById(incomePeriodicity.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIncomePeriodicity are not directly saved in db
        em.detach(updatedIncomePeriodicity);
        updatedIncomePeriodicity.code(UPDATED_CODE).label(UPDATED_LABEL);

        restIncomePeriodicityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIncomePeriodicity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIncomePeriodicity))
            )
            .andExpect(status().isOk());

        // Validate the IncomePeriodicity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIncomePeriodicityToMatchAllProperties(updatedIncomePeriodicity);
    }

    @Test
    @Transactional
    void putNonExistingIncomePeriodicity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomePeriodicity.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomePeriodicityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, incomePeriodicity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(incomePeriodicity))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomePeriodicity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIncomePeriodicity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomePeriodicity.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomePeriodicityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(incomePeriodicity))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomePeriodicity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIncomePeriodicity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomePeriodicity.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomePeriodicityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incomePeriodicity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IncomePeriodicity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIncomePeriodicityWithPatch() throws Exception {
        // Initialize the database
        insertedIncomePeriodicity = incomePeriodicityRepository.saveAndFlush(incomePeriodicity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the incomePeriodicity using partial update
        IncomePeriodicity partialUpdatedIncomePeriodicity = new IncomePeriodicity();
        partialUpdatedIncomePeriodicity.setId(incomePeriodicity.getId());

        partialUpdatedIncomePeriodicity.code(UPDATED_CODE).label(UPDATED_LABEL);

        restIncomePeriodicityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncomePeriodicity.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIncomePeriodicity))
            )
            .andExpect(status().isOk());

        // Validate the IncomePeriodicity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIncomePeriodicityUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIncomePeriodicity, incomePeriodicity),
            getPersistedIncomePeriodicity(incomePeriodicity)
        );
    }

    @Test
    @Transactional
    void fullUpdateIncomePeriodicityWithPatch() throws Exception {
        // Initialize the database
        insertedIncomePeriodicity = incomePeriodicityRepository.saveAndFlush(incomePeriodicity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the incomePeriodicity using partial update
        IncomePeriodicity partialUpdatedIncomePeriodicity = new IncomePeriodicity();
        partialUpdatedIncomePeriodicity.setId(incomePeriodicity.getId());

        partialUpdatedIncomePeriodicity.code(UPDATED_CODE).label(UPDATED_LABEL);

        restIncomePeriodicityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncomePeriodicity.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIncomePeriodicity))
            )
            .andExpect(status().isOk());

        // Validate the IncomePeriodicity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIncomePeriodicityUpdatableFieldsEquals(
            partialUpdatedIncomePeriodicity,
            getPersistedIncomePeriodicity(partialUpdatedIncomePeriodicity)
        );
    }

    @Test
    @Transactional
    void patchNonExistingIncomePeriodicity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomePeriodicity.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomePeriodicityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, incomePeriodicity.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(incomePeriodicity))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomePeriodicity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIncomePeriodicity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomePeriodicity.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomePeriodicityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(incomePeriodicity))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomePeriodicity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIncomePeriodicity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomePeriodicity.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomePeriodicityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(incomePeriodicity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IncomePeriodicity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIncomePeriodicity() throws Exception {
        // Initialize the database
        insertedIncomePeriodicity = incomePeriodicityRepository.saveAndFlush(incomePeriodicity);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the incomePeriodicity
        restIncomePeriodicityMockMvc
            .perform(delete(ENTITY_API_URL_ID, incomePeriodicity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return incomePeriodicityRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected IncomePeriodicity getPersistedIncomePeriodicity(IncomePeriodicity incomePeriodicity) {
        return incomePeriodicityRepository.findById(incomePeriodicity.getId()).orElseThrow();
    }

    protected void assertPersistedIncomePeriodicityToMatchAllProperties(IncomePeriodicity expectedIncomePeriodicity) {
        assertIncomePeriodicityAllPropertiesEquals(expectedIncomePeriodicity, getPersistedIncomePeriodicity(expectedIncomePeriodicity));
    }

    protected void assertPersistedIncomePeriodicityToMatchUpdatableProperties(IncomePeriodicity expectedIncomePeriodicity) {
        assertIncomePeriodicityAllUpdatablePropertiesEquals(
            expectedIncomePeriodicity,
            getPersistedIncomePeriodicity(expectedIncomePeriodicity)
        );
    }
}

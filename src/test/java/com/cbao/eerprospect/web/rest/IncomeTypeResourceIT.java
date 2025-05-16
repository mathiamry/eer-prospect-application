package com.cbao.eerprospect.web.rest;

import static com.cbao.eerprospect.domain.IncomeTypeAsserts.*;
import static com.cbao.eerprospect.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbao.eerprospect.IntegrationTest;
import com.cbao.eerprospect.domain.IncomeType;
import com.cbao.eerprospect.repository.IncomeTypeRepository;
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
 * Integration tests for the {@link IncomeTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IncomeTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/income-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncomeTypeMockMvc;

    private IncomeType incomeType;

    private IncomeType insertedIncomeType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomeType createEntity() {
        return new IncomeType().code(DEFAULT_CODE).label(DEFAULT_LABEL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomeType createUpdatedEntity() {
        return new IncomeType().code(UPDATED_CODE).label(UPDATED_LABEL);
    }

    @BeforeEach
    void initTest() {
        incomeType = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedIncomeType != null) {
            incomeTypeRepository.delete(insertedIncomeType);
            insertedIncomeType = null;
        }
    }

    @Test
    @Transactional
    void createIncomeType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IncomeType
        var returnedIncomeType = om.readValue(
            restIncomeTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incomeType)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IncomeType.class
        );

        // Validate the IncomeType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIncomeTypeUpdatableFieldsEquals(returnedIncomeType, getPersistedIncomeType(returnedIncomeType));

        insertedIncomeType = returnedIncomeType;
    }

    @Test
    @Transactional
    void createIncomeTypeWithExistingId() throws Exception {
        // Create the IncomeType with an existing ID
        incomeType.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomeTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incomeType)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIncomeTypes() throws Exception {
        // Initialize the database
        insertedIncomeType = incomeTypeRepository.saveAndFlush(incomeType);

        // Get all the incomeTypeList
        restIncomeTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getIncomeType() throws Exception {
        // Initialize the database
        insertedIncomeType = incomeTypeRepository.saveAndFlush(incomeType);

        // Get the incomeType
        restIncomeTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, incomeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incomeType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingIncomeType() throws Exception {
        // Get the incomeType
        restIncomeTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIncomeType() throws Exception {
        // Initialize the database
        insertedIncomeType = incomeTypeRepository.saveAndFlush(incomeType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the incomeType
        IncomeType updatedIncomeType = incomeTypeRepository.findById(incomeType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIncomeType are not directly saved in db
        em.detach(updatedIncomeType);
        updatedIncomeType.code(UPDATED_CODE).label(UPDATED_LABEL);

        restIncomeTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIncomeType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIncomeType))
            )
            .andExpect(status().isOk());

        // Validate the IncomeType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIncomeTypeToMatchAllProperties(updatedIncomeType);
    }

    @Test
    @Transactional
    void putNonExistingIncomeType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomeType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomeTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, incomeType.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incomeType))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomeType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIncomeType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomeType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomeTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(incomeType))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomeType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIncomeType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomeType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomeTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(incomeType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IncomeType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIncomeTypeWithPatch() throws Exception {
        // Initialize the database
        insertedIncomeType = incomeTypeRepository.saveAndFlush(incomeType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the incomeType using partial update
        IncomeType partialUpdatedIncomeType = new IncomeType();
        partialUpdatedIncomeType.setId(incomeType.getId());

        partialUpdatedIncomeType.code(UPDATED_CODE).label(UPDATED_LABEL);

        restIncomeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncomeType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIncomeType))
            )
            .andExpect(status().isOk());

        // Validate the IncomeType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIncomeTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIncomeType, incomeType),
            getPersistedIncomeType(incomeType)
        );
    }

    @Test
    @Transactional
    void fullUpdateIncomeTypeWithPatch() throws Exception {
        // Initialize the database
        insertedIncomeType = incomeTypeRepository.saveAndFlush(incomeType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the incomeType using partial update
        IncomeType partialUpdatedIncomeType = new IncomeType();
        partialUpdatedIncomeType.setId(incomeType.getId());

        partialUpdatedIncomeType.code(UPDATED_CODE).label(UPDATED_LABEL);

        restIncomeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncomeType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIncomeType))
            )
            .andExpect(status().isOk());

        // Validate the IncomeType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIncomeTypeUpdatableFieldsEquals(partialUpdatedIncomeType, getPersistedIncomeType(partialUpdatedIncomeType));
    }

    @Test
    @Transactional
    void patchNonExistingIncomeType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomeType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, incomeType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(incomeType))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomeType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIncomeType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomeType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomeTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(incomeType))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomeType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIncomeType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        incomeType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomeTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(incomeType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IncomeType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIncomeType() throws Exception {
        // Initialize the database
        insertedIncomeType = incomeTypeRepository.saveAndFlush(incomeType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the incomeType
        restIncomeTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, incomeType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return incomeTypeRepository.count();
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

    protected IncomeType getPersistedIncomeType(IncomeType incomeType) {
        return incomeTypeRepository.findById(incomeType.getId()).orElseThrow();
    }

    protected void assertPersistedIncomeTypeToMatchAllProperties(IncomeType expectedIncomeType) {
        assertIncomeTypeAllPropertiesEquals(expectedIncomeType, getPersistedIncomeType(expectedIncomeType));
    }

    protected void assertPersistedIncomeTypeToMatchUpdatableProperties(IncomeType expectedIncomeType) {
        assertIncomeTypeAllUpdatablePropertiesEquals(expectedIncomeType, getPersistedIncomeType(expectedIncomeType));
    }
}

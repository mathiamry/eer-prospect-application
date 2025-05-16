package com.cbao.eerprospect.web.rest;

import static com.cbao.eerprospect.domain.FamilyStatusAsserts.*;
import static com.cbao.eerprospect.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbao.eerprospect.IntegrationTest;
import com.cbao.eerprospect.domain.FamilyStatus;
import com.cbao.eerprospect.repository.FamilyStatusRepository;
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
 * Integration tests for the {@link FamilyStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FamilyStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/family-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FamilyStatusRepository familyStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilyStatusMockMvc;

    private FamilyStatus familyStatus;

    private FamilyStatus insertedFamilyStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyStatus createEntity() {
        return new FamilyStatus().code(DEFAULT_CODE).label(DEFAULT_LABEL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyStatus createUpdatedEntity() {
        return new FamilyStatus().code(UPDATED_CODE).label(UPDATED_LABEL);
    }

    @BeforeEach
    void initTest() {
        familyStatus = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFamilyStatus != null) {
            familyStatusRepository.delete(insertedFamilyStatus);
            insertedFamilyStatus = null;
        }
    }

    @Test
    @Transactional
    void createFamilyStatus() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FamilyStatus
        var returnedFamilyStatus = om.readValue(
            restFamilyStatusMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(familyStatus)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FamilyStatus.class
        );

        // Validate the FamilyStatus in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFamilyStatusUpdatableFieldsEquals(returnedFamilyStatus, getPersistedFamilyStatus(returnedFamilyStatus));

        insertedFamilyStatus = returnedFamilyStatus;
    }

    @Test
    @Transactional
    void createFamilyStatusWithExistingId() throws Exception {
        // Create the FamilyStatus with an existing ID
        familyStatus.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(familyStatus)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFamilyStatuses() throws Exception {
        // Initialize the database
        insertedFamilyStatus = familyStatusRepository.saveAndFlush(familyStatus);

        // Get all the familyStatusList
        restFamilyStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getFamilyStatus() throws Exception {
        // Initialize the database
        insertedFamilyStatus = familyStatusRepository.saveAndFlush(familyStatus);

        // Get the familyStatus
        restFamilyStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, familyStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(familyStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingFamilyStatus() throws Exception {
        // Get the familyStatus
        restFamilyStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFamilyStatus() throws Exception {
        // Initialize the database
        insertedFamilyStatus = familyStatusRepository.saveAndFlush(familyStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the familyStatus
        FamilyStatus updatedFamilyStatus = familyStatusRepository.findById(familyStatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFamilyStatus are not directly saved in db
        em.detach(updatedFamilyStatus);
        updatedFamilyStatus.code(UPDATED_CODE).label(UPDATED_LABEL);

        restFamilyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFamilyStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFamilyStatus))
            )
            .andExpect(status().isOk());

        // Validate the FamilyStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFamilyStatusToMatchAllProperties(updatedFamilyStatus);
    }

    @Test
    @Transactional
    void putNonExistingFamilyStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyStatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, familyStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(familyStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFamilyStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyStatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(familyStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFamilyStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyStatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(familyStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFamilyStatusWithPatch() throws Exception {
        // Initialize the database
        insertedFamilyStatus = familyStatusRepository.saveAndFlush(familyStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the familyStatus using partial update
        FamilyStatus partialUpdatedFamilyStatus = new FamilyStatus();
        partialUpdatedFamilyStatus.setId(familyStatus.getId());

        partialUpdatedFamilyStatus.label(UPDATED_LABEL);

        restFamilyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFamilyStatus))
            )
            .andExpect(status().isOk());

        // Validate the FamilyStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFamilyStatusUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFamilyStatus, familyStatus),
            getPersistedFamilyStatus(familyStatus)
        );
    }

    @Test
    @Transactional
    void fullUpdateFamilyStatusWithPatch() throws Exception {
        // Initialize the database
        insertedFamilyStatus = familyStatusRepository.saveAndFlush(familyStatus);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the familyStatus using partial update
        FamilyStatus partialUpdatedFamilyStatus = new FamilyStatus();
        partialUpdatedFamilyStatus.setId(familyStatus.getId());

        partialUpdatedFamilyStatus.code(UPDATED_CODE).label(UPDATED_LABEL);

        restFamilyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFamilyStatus))
            )
            .andExpect(status().isOk());

        // Validate the FamilyStatus in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFamilyStatusUpdatableFieldsEquals(partialUpdatedFamilyStatus, getPersistedFamilyStatus(partialUpdatedFamilyStatus));
    }

    @Test
    @Transactional
    void patchNonExistingFamilyStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyStatus.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, familyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(familyStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFamilyStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyStatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(familyStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFamilyStatus() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        familyStatus.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyStatusMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(familyStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyStatus in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFamilyStatus() throws Exception {
        // Initialize the database
        insertedFamilyStatus = familyStatusRepository.saveAndFlush(familyStatus);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the familyStatus
        restFamilyStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, familyStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return familyStatusRepository.count();
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

    protected FamilyStatus getPersistedFamilyStatus(FamilyStatus familyStatus) {
        return familyStatusRepository.findById(familyStatus.getId()).orElseThrow();
    }

    protected void assertPersistedFamilyStatusToMatchAllProperties(FamilyStatus expectedFamilyStatus) {
        assertFamilyStatusAllPropertiesEquals(expectedFamilyStatus, getPersistedFamilyStatus(expectedFamilyStatus));
    }

    protected void assertPersistedFamilyStatusToMatchUpdatableProperties(FamilyStatus expectedFamilyStatus) {
        assertFamilyStatusAllUpdatablePropertiesEquals(expectedFamilyStatus, getPersistedFamilyStatus(expectedFamilyStatus));
    }
}

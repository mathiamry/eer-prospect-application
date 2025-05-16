package com.cbao.eerprospect.web.rest;

import static com.cbao.eerprospect.domain.CivilityAsserts.*;
import static com.cbao.eerprospect.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbao.eerprospect.IntegrationTest;
import com.cbao.eerprospect.domain.Civility;
import com.cbao.eerprospect.repository.CivilityRepository;
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
 * Integration tests for the {@link CivilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CivilityResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/civilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CivilityRepository civilityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCivilityMockMvc;

    private Civility civility;

    private Civility insertedCivility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Civility createEntity() {
        return new Civility().code(DEFAULT_CODE).label(DEFAULT_LABEL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Civility createUpdatedEntity() {
        return new Civility().code(UPDATED_CODE).label(UPDATED_LABEL);
    }

    @BeforeEach
    void initTest() {
        civility = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCivility != null) {
            civilityRepository.delete(insertedCivility);
            insertedCivility = null;
        }
    }

    @Test
    @Transactional
    void createCivility() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Civility
        var returnedCivility = om.readValue(
            restCivilityMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(civility)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Civility.class
        );

        // Validate the Civility in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCivilityUpdatableFieldsEquals(returnedCivility, getPersistedCivility(returnedCivility));

        insertedCivility = returnedCivility;
    }

    @Test
    @Transactional
    void createCivilityWithExistingId() throws Exception {
        // Create the Civility with an existing ID
        civility.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCivilityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(civility)))
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCivilities() throws Exception {
        // Initialize the database
        insertedCivility = civilityRepository.saveAndFlush(civility);

        // Get all the civilityList
        restCivilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(civility.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getCivility() throws Exception {
        // Initialize the database
        insertedCivility = civilityRepository.saveAndFlush(civility);

        // Get the civility
        restCivilityMockMvc
            .perform(get(ENTITY_API_URL_ID, civility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(civility.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingCivility() throws Exception {
        // Get the civility
        restCivilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCivility() throws Exception {
        // Initialize the database
        insertedCivility = civilityRepository.saveAndFlush(civility);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the civility
        Civility updatedCivility = civilityRepository.findById(civility.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCivility are not directly saved in db
        em.detach(updatedCivility);
        updatedCivility.code(UPDATED_CODE).label(UPDATED_LABEL);

        restCivilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCivility.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCivility))
            )
            .andExpect(status().isOk());

        // Validate the Civility in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCivilityToMatchAllProperties(updatedCivility);
    }

    @Test
    @Transactional
    void putNonExistingCivility() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        civility.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, civility.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(civility))
            )
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCivility() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        civility.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(civility))
            )
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCivility() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        civility.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(civility)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Civility in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCivilityWithPatch() throws Exception {
        // Initialize the database
        insertedCivility = civilityRepository.saveAndFlush(civility);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the civility using partial update
        Civility partialUpdatedCivility = new Civility();
        partialUpdatedCivility.setId(civility.getId());

        partialUpdatedCivility.code(UPDATED_CODE);

        restCivilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCivility.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCivility))
            )
            .andExpect(status().isOk());

        // Validate the Civility in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCivilityUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCivility, civility), getPersistedCivility(civility));
    }

    @Test
    @Transactional
    void fullUpdateCivilityWithPatch() throws Exception {
        // Initialize the database
        insertedCivility = civilityRepository.saveAndFlush(civility);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the civility using partial update
        Civility partialUpdatedCivility = new Civility();
        partialUpdatedCivility.setId(civility.getId());

        partialUpdatedCivility.code(UPDATED_CODE).label(UPDATED_LABEL);

        restCivilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCivility.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCivility))
            )
            .andExpect(status().isOk());

        // Validate the Civility in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCivilityUpdatableFieldsEquals(partialUpdatedCivility, getPersistedCivility(partialUpdatedCivility));
    }

    @Test
    @Transactional
    void patchNonExistingCivility() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        civility.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, civility.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(civility))
            )
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCivility() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        civility.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(civility))
            )
            .andExpect(status().isBadRequest());

        // Validate the Civility in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCivility() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        civility.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCivilityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(civility)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Civility in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCivility() throws Exception {
        // Initialize the database
        insertedCivility = civilityRepository.saveAndFlush(civility);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the civility
        restCivilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, civility.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return civilityRepository.count();
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

    protected Civility getPersistedCivility(Civility civility) {
        return civilityRepository.findById(civility.getId()).orElseThrow();
    }

    protected void assertPersistedCivilityToMatchAllProperties(Civility expectedCivility) {
        assertCivilityAllPropertiesEquals(expectedCivility, getPersistedCivility(expectedCivility));
    }

    protected void assertPersistedCivilityToMatchUpdatableProperties(Civility expectedCivility) {
        assertCivilityAllUpdatablePropertiesEquals(expectedCivility, getPersistedCivility(expectedCivility));
    }
}

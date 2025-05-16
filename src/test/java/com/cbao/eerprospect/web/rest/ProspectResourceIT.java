package com.cbao.eerprospect.web.rest;

import static com.cbao.eerprospect.domain.ProspectAsserts.*;
import static com.cbao.eerprospect.web.rest.TestUtil.createUpdateProxyForBean;
import static com.cbao.eerprospect.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cbao.eerprospect.IntegrationTest;
import com.cbao.eerprospect.domain.Prospect;
import com.cbao.eerprospect.repository.ProspectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ProspectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProspectResourceIT {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_CITY_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WIFE_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WIFE_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WIFE_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WIFE_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY_STATUS_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_STATUS_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_OF_RESIDENCE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_RESIDENCE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PAPER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ID_PAPER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PAPER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_PAPER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PAPER_DELIVERY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ID_PAPER_DELIVERY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PAPER_DELIVERY_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_ID_PAPER_DELIVERY_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_PAPER_VALIDITY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ID_PAPER_VALIDITY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_INCOME_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INCOME_AMOUNT = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/prospects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProspectRepository prospectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProspectMockMvc;

    private Prospect prospect;

    private Prospect insertedProspect;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prospect createEntity() {
        return new Prospect()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .cityOfBirth(DEFAULT_CITY_OF_BIRTH)
            .countryOfBirth(DEFAULT_COUNTRY_OF_BIRTH)
            .nationality(DEFAULT_NATIONALITY)
            .motherLastName(DEFAULT_MOTHER_LAST_NAME)
            .motherFirstName(DEFAULT_MOTHER_FIRST_NAME)
            .wifeLastName(DEFAULT_WIFE_LAST_NAME)
            .wifeFirstName(DEFAULT_WIFE_FIRST_NAME)
            .familyStatusLabel(DEFAULT_FAMILY_STATUS_LABEL)
            .countryOfResidence(DEFAULT_COUNTRY_OF_RESIDENCE)
            .city(DEFAULT_CITY)
            .addressLine(DEFAULT_ADDRESS_LINE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .idPaperType(DEFAULT_ID_PAPER_TYPE)
            .idPaperNumber(DEFAULT_ID_PAPER_NUMBER)
            .idPaperDeliveryDate(DEFAULT_ID_PAPER_DELIVERY_DATE)
            .idPaperDeliveryPlace(DEFAULT_ID_PAPER_DELIVERY_PLACE)
            .idPaperValidityDate(DEFAULT_ID_PAPER_VALIDITY_DATE)
            .professionCategory(DEFAULT_PROFESSION_CATEGORY)
            .profession(DEFAULT_PROFESSION)
            .employer(DEFAULT_EMPLOYER)
            .incomeAmount(DEFAULT_INCOME_AMOUNT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prospect createUpdatedEntity() {
        return new Prospect()
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .cityOfBirth(UPDATED_CITY_OF_BIRTH)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .nationality(UPDATED_NATIONALITY)
            .motherLastName(UPDATED_MOTHER_LAST_NAME)
            .motherFirstName(UPDATED_MOTHER_FIRST_NAME)
            .wifeLastName(UPDATED_WIFE_LAST_NAME)
            .wifeFirstName(UPDATED_WIFE_FIRST_NAME)
            .familyStatusLabel(UPDATED_FAMILY_STATUS_LABEL)
            .countryOfResidence(UPDATED_COUNTRY_OF_RESIDENCE)
            .city(UPDATED_CITY)
            .addressLine(UPDATED_ADDRESS_LINE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .idPaperType(UPDATED_ID_PAPER_TYPE)
            .idPaperNumber(UPDATED_ID_PAPER_NUMBER)
            .idPaperDeliveryDate(UPDATED_ID_PAPER_DELIVERY_DATE)
            .idPaperDeliveryPlace(UPDATED_ID_PAPER_DELIVERY_PLACE)
            .idPaperValidityDate(UPDATED_ID_PAPER_VALIDITY_DATE)
            .professionCategory(UPDATED_PROFESSION_CATEGORY)
            .profession(UPDATED_PROFESSION)
            .employer(UPDATED_EMPLOYER)
            .incomeAmount(UPDATED_INCOME_AMOUNT);
    }

    @BeforeEach
    void initTest() {
        prospect = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedProspect != null) {
            prospectRepository.delete(insertedProspect);
            insertedProspect = null;
        }
    }

    @Test
    @Transactional
    void createProspect() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Prospect
        var returnedProspect = om.readValue(
            restProspectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prospect)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Prospect.class
        );

        // Validate the Prospect in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProspectUpdatableFieldsEquals(returnedProspect, getPersistedProspect(returnedProspect));

        insertedProspect = returnedProspect;
    }

    @Test
    @Transactional
    void createProspectWithExistingId() throws Exception {
        // Create the Prospect with an existing ID
        prospect.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProspectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prospect)))
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProspects() throws Exception {
        // Initialize the database
        insertedProspect = prospectRepository.saveAndFlush(prospect);

        // Get all the prospectList
        restProspectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prospect.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].cityOfBirth").value(hasItem(DEFAULT_CITY_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].countryOfBirth").value(hasItem(DEFAULT_COUNTRY_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].motherLastName").value(hasItem(DEFAULT_MOTHER_LAST_NAME)))
            .andExpect(jsonPath("$.[*].motherFirstName").value(hasItem(DEFAULT_MOTHER_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].wifeLastName").value(hasItem(DEFAULT_WIFE_LAST_NAME)))
            .andExpect(jsonPath("$.[*].wifeFirstName").value(hasItem(DEFAULT_WIFE_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].familyStatusLabel").value(hasItem(DEFAULT_FAMILY_STATUS_LABEL)))
            .andExpect(jsonPath("$.[*].countryOfResidence").value(hasItem(DEFAULT_COUNTRY_OF_RESIDENCE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].addressLine").value(hasItem(DEFAULT_ADDRESS_LINE)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].idPaperType").value(hasItem(DEFAULT_ID_PAPER_TYPE)))
            .andExpect(jsonPath("$.[*].idPaperNumber").value(hasItem(DEFAULT_ID_PAPER_NUMBER)))
            .andExpect(jsonPath("$.[*].idPaperDeliveryDate").value(hasItem(DEFAULT_ID_PAPER_DELIVERY_DATE)))
            .andExpect(jsonPath("$.[*].idPaperDeliveryPlace").value(hasItem(DEFAULT_ID_PAPER_DELIVERY_PLACE)))
            .andExpect(jsonPath("$.[*].idPaperValidityDate").value(hasItem(DEFAULT_ID_PAPER_VALIDITY_DATE)))
            .andExpect(jsonPath("$.[*].professionCategory").value(hasItem(DEFAULT_PROFESSION_CATEGORY)))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].employer").value(hasItem(DEFAULT_EMPLOYER)))
            .andExpect(jsonPath("$.[*].incomeAmount").value(hasItem(sameNumber(DEFAULT_INCOME_AMOUNT))));
    }

    @Test
    @Transactional
    void getProspect() throws Exception {
        // Initialize the database
        insertedProspect = prospectRepository.saveAndFlush(prospect);

        // Get the prospect
        restProspectMockMvc
            .perform(get(ENTITY_API_URL_ID, prospect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prospect.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH))
            .andExpect(jsonPath("$.cityOfBirth").value(DEFAULT_CITY_OF_BIRTH))
            .andExpect(jsonPath("$.countryOfBirth").value(DEFAULT_COUNTRY_OF_BIRTH))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.motherLastName").value(DEFAULT_MOTHER_LAST_NAME))
            .andExpect(jsonPath("$.motherFirstName").value(DEFAULT_MOTHER_FIRST_NAME))
            .andExpect(jsonPath("$.wifeLastName").value(DEFAULT_WIFE_LAST_NAME))
            .andExpect(jsonPath("$.wifeFirstName").value(DEFAULT_WIFE_FIRST_NAME))
            .andExpect(jsonPath("$.familyStatusLabel").value(DEFAULT_FAMILY_STATUS_LABEL))
            .andExpect(jsonPath("$.countryOfResidence").value(DEFAULT_COUNTRY_OF_RESIDENCE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.addressLine").value(DEFAULT_ADDRESS_LINE))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.idPaperType").value(DEFAULT_ID_PAPER_TYPE))
            .andExpect(jsonPath("$.idPaperNumber").value(DEFAULT_ID_PAPER_NUMBER))
            .andExpect(jsonPath("$.idPaperDeliveryDate").value(DEFAULT_ID_PAPER_DELIVERY_DATE))
            .andExpect(jsonPath("$.idPaperDeliveryPlace").value(DEFAULT_ID_PAPER_DELIVERY_PLACE))
            .andExpect(jsonPath("$.idPaperValidityDate").value(DEFAULT_ID_PAPER_VALIDITY_DATE))
            .andExpect(jsonPath("$.professionCategory").value(DEFAULT_PROFESSION_CATEGORY))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.employer").value(DEFAULT_EMPLOYER))
            .andExpect(jsonPath("$.incomeAmount").value(sameNumber(DEFAULT_INCOME_AMOUNT)));
    }

    @Test
    @Transactional
    void getNonExistingProspect() throws Exception {
        // Get the prospect
        restProspectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProspect() throws Exception {
        // Initialize the database
        insertedProspect = prospectRepository.saveAndFlush(prospect);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prospect
        Prospect updatedProspect = prospectRepository.findById(prospect.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProspect are not directly saved in db
        em.detach(updatedProspect);
        updatedProspect
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .cityOfBirth(UPDATED_CITY_OF_BIRTH)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .nationality(UPDATED_NATIONALITY)
            .motherLastName(UPDATED_MOTHER_LAST_NAME)
            .motherFirstName(UPDATED_MOTHER_FIRST_NAME)
            .wifeLastName(UPDATED_WIFE_LAST_NAME)
            .wifeFirstName(UPDATED_WIFE_FIRST_NAME)
            .familyStatusLabel(UPDATED_FAMILY_STATUS_LABEL)
            .countryOfResidence(UPDATED_COUNTRY_OF_RESIDENCE)
            .city(UPDATED_CITY)
            .addressLine(UPDATED_ADDRESS_LINE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .idPaperType(UPDATED_ID_PAPER_TYPE)
            .idPaperNumber(UPDATED_ID_PAPER_NUMBER)
            .idPaperDeliveryDate(UPDATED_ID_PAPER_DELIVERY_DATE)
            .idPaperDeliveryPlace(UPDATED_ID_PAPER_DELIVERY_PLACE)
            .idPaperValidityDate(UPDATED_ID_PAPER_VALIDITY_DATE)
            .professionCategory(UPDATED_PROFESSION_CATEGORY)
            .profession(UPDATED_PROFESSION)
            .employer(UPDATED_EMPLOYER)
            .incomeAmount(UPDATED_INCOME_AMOUNT);

        restProspectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProspect.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProspect))
            )
            .andExpect(status().isOk());

        // Validate the Prospect in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProspectToMatchAllProperties(updatedProspect);
    }

    @Test
    @Transactional
    void putNonExistingProspect() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prospect.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProspectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prospect.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prospect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProspect() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prospect.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProspectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prospect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProspect() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prospect.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProspectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prospect)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prospect in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProspectWithPatch() throws Exception {
        // Initialize the database
        insertedProspect = prospectRepository.saveAndFlush(prospect);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prospect using partial update
        Prospect partialUpdatedProspect = new Prospect();
        partialUpdatedProspect.setId(prospect.getId());

        partialUpdatedProspect
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .cityOfBirth(UPDATED_CITY_OF_BIRTH)
            .nationality(UPDATED_NATIONALITY)
            .wifeFirstName(UPDATED_WIFE_FIRST_NAME)
            .familyStatusLabel(UPDATED_FAMILY_STATUS_LABEL)
            .countryOfResidence(UPDATED_COUNTRY_OF_RESIDENCE)
            .city(UPDATED_CITY)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .idPaperType(UPDATED_ID_PAPER_TYPE)
            .idPaperValidityDate(UPDATED_ID_PAPER_VALIDITY_DATE)
            .employer(UPDATED_EMPLOYER)
            .incomeAmount(UPDATED_INCOME_AMOUNT);

        restProspectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProspect.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProspect))
            )
            .andExpect(status().isOk());

        // Validate the Prospect in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProspectUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProspect, prospect), getPersistedProspect(prospect));
    }

    @Test
    @Transactional
    void fullUpdateProspectWithPatch() throws Exception {
        // Initialize the database
        insertedProspect = prospectRepository.saveAndFlush(prospect);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prospect using partial update
        Prospect partialUpdatedProspect = new Prospect();
        partialUpdatedProspect.setId(prospect.getId());

        partialUpdatedProspect
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .cityOfBirth(UPDATED_CITY_OF_BIRTH)
            .countryOfBirth(UPDATED_COUNTRY_OF_BIRTH)
            .nationality(UPDATED_NATIONALITY)
            .motherLastName(UPDATED_MOTHER_LAST_NAME)
            .motherFirstName(UPDATED_MOTHER_FIRST_NAME)
            .wifeLastName(UPDATED_WIFE_LAST_NAME)
            .wifeFirstName(UPDATED_WIFE_FIRST_NAME)
            .familyStatusLabel(UPDATED_FAMILY_STATUS_LABEL)
            .countryOfResidence(UPDATED_COUNTRY_OF_RESIDENCE)
            .city(UPDATED_CITY)
            .addressLine(UPDATED_ADDRESS_LINE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .idPaperType(UPDATED_ID_PAPER_TYPE)
            .idPaperNumber(UPDATED_ID_PAPER_NUMBER)
            .idPaperDeliveryDate(UPDATED_ID_PAPER_DELIVERY_DATE)
            .idPaperDeliveryPlace(UPDATED_ID_PAPER_DELIVERY_PLACE)
            .idPaperValidityDate(UPDATED_ID_PAPER_VALIDITY_DATE)
            .professionCategory(UPDATED_PROFESSION_CATEGORY)
            .profession(UPDATED_PROFESSION)
            .employer(UPDATED_EMPLOYER)
            .incomeAmount(UPDATED_INCOME_AMOUNT);

        restProspectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProspect.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProspect))
            )
            .andExpect(status().isOk());

        // Validate the Prospect in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProspectUpdatableFieldsEquals(partialUpdatedProspect, getPersistedProspect(partialUpdatedProspect));
    }

    @Test
    @Transactional
    void patchNonExistingProspect() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prospect.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProspectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prospect.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prospect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProspect() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prospect.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProspectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prospect))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prospect in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProspect() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prospect.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProspectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prospect)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prospect in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProspect() throws Exception {
        // Initialize the database
        insertedProspect = prospectRepository.saveAndFlush(prospect);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prospect
        restProspectMockMvc
            .perform(delete(ENTITY_API_URL_ID, prospect.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prospectRepository.count();
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

    protected Prospect getPersistedProspect(Prospect prospect) {
        return prospectRepository.findById(prospect.getId()).orElseThrow();
    }

    protected void assertPersistedProspectToMatchAllProperties(Prospect expectedProspect) {
        assertProspectAllPropertiesEquals(expectedProspect, getPersistedProspect(expectedProspect));
    }

    protected void assertPersistedProspectToMatchUpdatableProperties(Prospect expectedProspect) {
        assertProspectAllUpdatablePropertiesEquals(expectedProspect, getPersistedProspect(expectedProspect));
    }
}

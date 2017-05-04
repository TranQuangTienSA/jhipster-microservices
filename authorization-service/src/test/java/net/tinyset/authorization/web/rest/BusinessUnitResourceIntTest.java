package net.tinyset.authorization.web.rest;

import net.tinyset.authorization.AuthorizationApp;

import net.tinyset.authorization.config.SecurityBeanOverrideConfiguration;

import net.tinyset.authorization.domain.BusinessUnit;
import net.tinyset.authorization.domain.BusinessUnit;
import net.tinyset.authorization.repository.BusinessUnitRepository;
import net.tinyset.authorization.service.BusinessUnitService;
import net.tinyset.authorization.service.dto.BusinessUnitDTO;
import net.tinyset.authorization.service.mapper.BusinessUnitMapper;
import net.tinyset.authorization.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessUnitResource REST controller.
 *
 * @see BusinessUnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AuthorizationApp.class, SecurityBeanOverrideConfiguration.class})
public class BusinessUnitResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DIVISION = "AAAAAAAAAA";
    private static final String UPDATED_DIVISION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private BusinessUnitMapper businessUnitMapper;

    @Autowired
    private BusinessUnitService businessUnitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusinessUnitMockMvc;

    private BusinessUnit businessUnit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusinessUnitResource businessUnitResource = new BusinessUnitResource(businessUnitService);
        this.restBusinessUnitMockMvc = MockMvcBuilders.standaloneSetup(businessUnitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessUnit createEntity(EntityManager em) {
        BusinessUnit businessUnit = new BusinessUnit()
            .name(DEFAULT_NAME)
            .division(DEFAULT_DIVISION)
            .website(DEFAULT_WEBSITE)
            .mainPhone(DEFAULT_MAIN_PHONE)
            .otherPhone(DEFAULT_OTHER_PHONE)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL);
        // Add required entity
        BusinessUnit parent = BusinessUnitResourceIntTest.createEntity(em);
        em.persist(parent);
        em.flush();
        businessUnit.setParent(parent);
        return businessUnit;
    }

    @Before
    public void initTest() {
        businessUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessUnit() throws Exception {
        int databaseSizeBeforeCreate = businessUnitRepository.findAll().size();

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.businessUnitToBusinessUnitDTO(businessUnit);
        restBusinessUnitMockMvc.perform(post("/api/business-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBusinessUnit.getDivision()).isEqualTo(DEFAULT_DIVISION);
        assertThat(testBusinessUnit.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testBusinessUnit.getMainPhone()).isEqualTo(DEFAULT_MAIN_PHONE);
        assertThat(testBusinessUnit.getOtherPhone()).isEqualTo(DEFAULT_OTHER_PHONE);
        assertThat(testBusinessUnit.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testBusinessUnit.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createBusinessUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessUnitRepository.findAll().size();

        // Create the BusinessUnit with an existing ID
        businessUnit.setId(1L);
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.businessUnitToBusinessUnitDTO(businessUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessUnitMockMvc.perform(post("/api/business-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessUnitRepository.findAll().size();
        // set the field null
        businessUnit.setName(null);

        // Create the BusinessUnit, which fails.
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.businessUnitToBusinessUnitDTO(businessUnit);

        restBusinessUnitMockMvc.perform(post("/api/business-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO)))
            .andExpect(status().isBadRequest());

        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinessUnits() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        // Get all the businessUnitList
        restBusinessUnitMockMvc.perform(get("/api/business-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].division").value(hasItem(DEFAULT_DIVISION.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].mainPhone").value(hasItem(DEFAULT_MAIN_PHONE.toString())))
            .andExpect(jsonPath("$.[*].otherPhone").value(hasItem(DEFAULT_OTHER_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getBusinessUnit() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);

        // Get the businessUnit
        restBusinessUnitMockMvc.perform(get("/api/business-units/{id}", businessUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.division").value(DEFAULT_DIVISION.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.mainPhone").value(DEFAULT_MAIN_PHONE.toString()))
            .andExpect(jsonPath("$.otherPhone").value(DEFAULT_OTHER_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessUnit() throws Exception {
        // Get the businessUnit
        restBusinessUnitMockMvc.perform(get("/api/business-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessUnit() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();

        // Update the businessUnit
        BusinessUnit updatedBusinessUnit = businessUnitRepository.findOne(businessUnit.getId());
        updatedBusinessUnit
            .name(UPDATED_NAME)
            .division(UPDATED_DIVISION)
            .website(UPDATED_WEBSITE)
            .mainPhone(UPDATED_MAIN_PHONE)
            .otherPhone(UPDATED_OTHER_PHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL);
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.businessUnitToBusinessUnitDTO(updatedBusinessUnit);

        restBusinessUnitMockMvc.perform(put("/api/business-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate);
        BusinessUnit testBusinessUnit = businessUnitList.get(businessUnitList.size() - 1);
        assertThat(testBusinessUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessUnit.getDivision()).isEqualTo(UPDATED_DIVISION);
        assertThat(testBusinessUnit.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testBusinessUnit.getMainPhone()).isEqualTo(UPDATED_MAIN_PHONE);
        assertThat(testBusinessUnit.getOtherPhone()).isEqualTo(UPDATED_OTHER_PHONE);
        assertThat(testBusinessUnit.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testBusinessUnit.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessUnit() throws Exception {
        int databaseSizeBeforeUpdate = businessUnitRepository.findAll().size();

        // Create the BusinessUnit
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.businessUnitToBusinessUnitDTO(businessUnit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBusinessUnitMockMvc.perform(put("/api/business-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessUnit in the database
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBusinessUnit() throws Exception {
        // Initialize the database
        businessUnitRepository.saveAndFlush(businessUnit);
        int databaseSizeBeforeDelete = businessUnitRepository.findAll().size();

        // Get the businessUnit
        restBusinessUnitMockMvc.perform(delete("/api/business-units/{id}", businessUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessUnit> businessUnitList = businessUnitRepository.findAll();
        assertThat(businessUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessUnit.class);
    }
}

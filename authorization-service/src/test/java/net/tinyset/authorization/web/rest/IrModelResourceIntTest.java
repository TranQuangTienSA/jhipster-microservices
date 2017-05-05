package net.tinyset.authorization.web.rest;

import net.tinyset.authorization.AuthorizationApp;

import net.tinyset.authorization.config.SecurityBeanOverrideConfiguration;

import net.tinyset.authorization.domain.IrModel;
import net.tinyset.authorization.repository.IrModelRepository;
import net.tinyset.authorization.service.IrModelService;
import net.tinyset.authorization.service.dto.IrModelDTO;
import net.tinyset.authorization.service.mapper.IrModelMapper;
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
 * Test class for the IrModelResource REST controller.
 *
 * @see IrModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AuthorizationApp.class, SecurityBeanOverrideConfiguration.class})
public class IrModelResourceIntTest {

    private static final String DEFAULT_MODEL = "23";
    private static final String UPDATED_MODEL = "_q";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private IrModelRepository irModelRepository;

    @Autowired
    private IrModelMapper irModelMapper;

    @Autowired
    private IrModelService irModelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIrModelMockMvc;

    private IrModel irModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IrModelResource irModelResource = new IrModelResource(irModelService);
        this.restIrModelMockMvc = MockMvcBuilders.standaloneSetup(irModelResource)
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
    public static IrModel createEntity(EntityManager em) {
        IrModel irModel = new IrModel()
            .model(DEFAULT_MODEL)
            .name(DEFAULT_NAME)
            .state(DEFAULT_STATE)
            .info(DEFAULT_INFO);
        return irModel;
    }

    @Before
    public void initTest() {
        irModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createIrModel() throws Exception {
        int databaseSizeBeforeCreate = irModelRepository.findAll().size();

        // Create the IrModel
        IrModelDTO irModelDTO = irModelMapper.irModelToIrModelDTO(irModel);
        restIrModelMockMvc.perform(post("/api/ir-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(irModelDTO)))
            .andExpect(status().isCreated());

        // Validate the IrModel in the database
        List<IrModel> irModelList = irModelRepository.findAll();
        assertThat(irModelList).hasSize(databaseSizeBeforeCreate + 1);
        IrModel testIrModel = irModelList.get(irModelList.size() - 1);
        assertThat(testIrModel.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testIrModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIrModel.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testIrModel.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createIrModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = irModelRepository.findAll().size();

        // Create the IrModel with an existing ID
        irModel.setId(1L);
        IrModelDTO irModelDTO = irModelMapper.irModelToIrModelDTO(irModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIrModelMockMvc.perform(post("/api/ir-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(irModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IrModel> irModelList = irModelRepository.findAll();
        assertThat(irModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = irModelRepository.findAll().size();
        // set the field null
        irModel.setModel(null);

        // Create the IrModel, which fails.
        IrModelDTO irModelDTO = irModelMapper.irModelToIrModelDTO(irModel);

        restIrModelMockMvc.perform(post("/api/ir-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(irModelDTO)))
            .andExpect(status().isBadRequest());

        List<IrModel> irModelList = irModelRepository.findAll();
        assertThat(irModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = irModelRepository.findAll().size();
        // set the field null
        irModel.setName(null);

        // Create the IrModel, which fails.
        IrModelDTO irModelDTO = irModelMapper.irModelToIrModelDTO(irModel);

        restIrModelMockMvc.perform(post("/api/ir-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(irModelDTO)))
            .andExpect(status().isBadRequest());

        List<IrModel> irModelList = irModelRepository.findAll();
        assertThat(irModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIrModels() throws Exception {
        // Initialize the database
        irModelRepository.saveAndFlush(irModel);

        // Get all the irModelList
        restIrModelMockMvc.perform(get("/api/ir-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(irModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())));
    }

    @Test
    @Transactional
    public void getIrModel() throws Exception {
        // Initialize the database
        irModelRepository.saveAndFlush(irModel);

        // Get the irModel
        restIrModelMockMvc.perform(get("/api/ir-models/{id}", irModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(irModel.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIrModel() throws Exception {
        // Get the irModel
        restIrModelMockMvc.perform(get("/api/ir-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIrModel() throws Exception {
        // Initialize the database
        irModelRepository.saveAndFlush(irModel);
        int databaseSizeBeforeUpdate = irModelRepository.findAll().size();

        // Update the irModel
        IrModel updatedIrModel = irModelRepository.findOne(irModel.getId());
        updatedIrModel
            .model(UPDATED_MODEL)
            .name(UPDATED_NAME)
            .state(UPDATED_STATE)
            .info(UPDATED_INFO);
        IrModelDTO irModelDTO = irModelMapper.irModelToIrModelDTO(updatedIrModel);

        restIrModelMockMvc.perform(put("/api/ir-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(irModelDTO)))
            .andExpect(status().isOk());

        // Validate the IrModel in the database
        List<IrModel> irModelList = irModelRepository.findAll();
        assertThat(irModelList).hasSize(databaseSizeBeforeUpdate);
        IrModel testIrModel = irModelList.get(irModelList.size() - 1);
        assertThat(testIrModel.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testIrModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIrModel.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testIrModel.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingIrModel() throws Exception {
        int databaseSizeBeforeUpdate = irModelRepository.findAll().size();

        // Create the IrModel
        IrModelDTO irModelDTO = irModelMapper.irModelToIrModelDTO(irModel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIrModelMockMvc.perform(put("/api/ir-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(irModelDTO)))
            .andExpect(status().isCreated());

        // Validate the IrModel in the database
        List<IrModel> irModelList = irModelRepository.findAll();
        assertThat(irModelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIrModel() throws Exception {
        // Initialize the database
        irModelRepository.saveAndFlush(irModel);
        int databaseSizeBeforeDelete = irModelRepository.findAll().size();

        // Get the irModel
        restIrModelMockMvc.perform(delete("/api/ir-models/{id}", irModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IrModel> irModelList = irModelRepository.findAll();
        assertThat(irModelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IrModel.class);
    }
}

package truequep.web.rest;

import truequep.TruequepApp;

import truequep.domain.Comuna;
import truequep.repository.ComunaRepository;
import truequep.service.ComunaService;
import truequep.service.dto.ComunaDTO;
import truequep.service.mapper.ComunaMapper;
import truequep.web.rest.errors.ExceptionTranslator;

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

import static truequep.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ComunaResource REST controller.
 *
 * @see ComunaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruequepApp.class)
public class ComunaResourceIntTest {

    private static final String DEFAULT_NOMBRE_COMUNA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMUNA = "BBBBBBBBBB";

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private ComunaMapper comunaMapper;

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComunaMockMvc;

    private Comuna comuna;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComunaResource comunaResource = new ComunaResource(comunaService);
        this.restComunaMockMvc = MockMvcBuilders.standaloneSetup(comunaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comuna createEntity(EntityManager em) {
        Comuna comuna = new Comuna()
            .nombreComuna(DEFAULT_NOMBRE_COMUNA);
        return comuna;
    }

    @Before
    public void initTest() {
        comuna = createEntity(em);
    }

    @Test
    @Transactional
    public void createComuna() throws Exception {
        int databaseSizeBeforeCreate = comunaRepository.findAll().size();

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);
        restComunaMockMvc.perform(post("/api/comunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunaDTO)))
            .andExpect(status().isCreated());

        // Validate the Comuna in the database
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeCreate + 1);
        Comuna testComuna = comunaList.get(comunaList.size() - 1);
        assertThat(testComuna.getNombreComuna()).isEqualTo(DEFAULT_NOMBRE_COMUNA);
    }

    @Test
    @Transactional
    public void createComunaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comunaRepository.findAll().size();

        // Create the Comuna with an existing ID
        comuna.setId(1L);
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComunaMockMvc.perform(post("/api/comunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comuna in the database
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreComunaIsRequired() throws Exception {
        int databaseSizeBeforeTest = comunaRepository.findAll().size();
        // set the field null
        comuna.setNombreComuna(null);

        // Create the Comuna, which fails.
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        restComunaMockMvc.perform(post("/api/comunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunaDTO)))
            .andExpect(status().isBadRequest());

        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComunas() throws Exception {
        // Initialize the database
        comunaRepository.saveAndFlush(comuna);

        // Get all the comunaList
        restComunaMockMvc.perform(get("/api/comunas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comuna.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreComuna").value(hasItem(DEFAULT_NOMBRE_COMUNA.toString())));
    }

    @Test
    @Transactional
    public void getComuna() throws Exception {
        // Initialize the database
        comunaRepository.saveAndFlush(comuna);

        // Get the comuna
        restComunaMockMvc.perform(get("/api/comunas/{id}", comuna.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comuna.getId().intValue()))
            .andExpect(jsonPath("$.nombreComuna").value(DEFAULT_NOMBRE_COMUNA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComuna() throws Exception {
        // Get the comuna
        restComunaMockMvc.perform(get("/api/comunas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComuna() throws Exception {
        // Initialize the database
        comunaRepository.saveAndFlush(comuna);
        int databaseSizeBeforeUpdate = comunaRepository.findAll().size();

        // Update the comuna
        Comuna updatedComuna = comunaRepository.findOne(comuna.getId());
        // Disconnect from session so that the updates on updatedComuna are not directly saved in db
        em.detach(updatedComuna);
        updatedComuna
            .nombreComuna(UPDATED_NOMBRE_COMUNA);
        ComunaDTO comunaDTO = comunaMapper.toDto(updatedComuna);

        restComunaMockMvc.perform(put("/api/comunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunaDTO)))
            .andExpect(status().isOk());

        // Validate the Comuna in the database
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeUpdate);
        Comuna testComuna = comunaList.get(comunaList.size() - 1);
        assertThat(testComuna.getNombreComuna()).isEqualTo(UPDATED_NOMBRE_COMUNA);
    }

    @Test
    @Transactional
    public void updateNonExistingComuna() throws Exception {
        int databaseSizeBeforeUpdate = comunaRepository.findAll().size();

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComunaMockMvc.perform(put("/api/comunas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunaDTO)))
            .andExpect(status().isCreated());

        // Validate the Comuna in the database
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComuna() throws Exception {
        // Initialize the database
        comunaRepository.saveAndFlush(comuna);
        int databaseSizeBeforeDelete = comunaRepository.findAll().size();

        // Get the comuna
        restComunaMockMvc.perform(delete("/api/comunas/{id}", comuna.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comuna> comunaList = comunaRepository.findAll();
        assertThat(comunaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comuna.class);
        Comuna comuna1 = new Comuna();
        comuna1.setId(1L);
        Comuna comuna2 = new Comuna();
        comuna2.setId(comuna1.getId());
        assertThat(comuna1).isEqualTo(comuna2);
        comuna2.setId(2L);
        assertThat(comuna1).isNotEqualTo(comuna2);
        comuna1.setId(null);
        assertThat(comuna1).isNotEqualTo(comuna2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComunaDTO.class);
        ComunaDTO comunaDTO1 = new ComunaDTO();
        comunaDTO1.setId(1L);
        ComunaDTO comunaDTO2 = new ComunaDTO();
        assertThat(comunaDTO1).isNotEqualTo(comunaDTO2);
        comunaDTO2.setId(comunaDTO1.getId());
        assertThat(comunaDTO1).isEqualTo(comunaDTO2);
        comunaDTO2.setId(2L);
        assertThat(comunaDTO1).isNotEqualTo(comunaDTO2);
        comunaDTO1.setId(null);
        assertThat(comunaDTO1).isNotEqualTo(comunaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(comunaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(comunaMapper.fromId(null)).isNull();
    }
}

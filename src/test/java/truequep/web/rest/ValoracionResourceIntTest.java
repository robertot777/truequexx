package truequep.web.rest;

import truequep.TruequepApp;

import truequep.domain.Valoracion;
import truequep.repository.ValoracionRepository;
import truequep.service.ValoracionService;
import truequep.service.dto.ValoracionDTO;
import truequep.service.mapper.ValoracionMapper;
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

import truequep.domain.enumeration.TipoValoracion;
/**
 * Test class for the ValoracionResource REST controller.
 *
 * @see ValoracionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruequepApp.class)
public class ValoracionResourceIntTest {

    private static final TipoValoracion DEFAULT_TIPO_VALORACION = TipoValoracion.EXELENTE;
    private static final TipoValoracion UPDATED_TIPO_VALORACION = TipoValoracion.BUENA;

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Autowired
    private ValoracionMapper valoracionMapper;

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValoracionMockMvc;

    private Valoracion valoracion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValoracionResource valoracionResource = new ValoracionResource(valoracionService);
        this.restValoracionMockMvc = MockMvcBuilders.standaloneSetup(valoracionResource)
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
    public static Valoracion createEntity(EntityManager em) {
        Valoracion valoracion = new Valoracion()
            .tipoValoracion(DEFAULT_TIPO_VALORACION);
        return valoracion;
    }

    @Before
    public void initTest() {
        valoracion = createEntity(em);
    }

    @Test
    @Transactional
    public void createValoracion() throws Exception {
        int databaseSizeBeforeCreate = valoracionRepository.findAll().size();

        // Create the Valoracion
        ValoracionDTO valoracionDTO = valoracionMapper.toDto(valoracion);
        restValoracionMockMvc.perform(post("/api/valoracions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionDTO)))
            .andExpect(status().isCreated());

        // Validate the Valoracion in the database
        List<Valoracion> valoracionList = valoracionRepository.findAll();
        assertThat(valoracionList).hasSize(databaseSizeBeforeCreate + 1);
        Valoracion testValoracion = valoracionList.get(valoracionList.size() - 1);
        assertThat(testValoracion.getTipoValoracion()).isEqualTo(DEFAULT_TIPO_VALORACION);
    }

    @Test
    @Transactional
    public void createValoracionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valoracionRepository.findAll().size();

        // Create the Valoracion with an existing ID
        valoracion.setId(1L);
        ValoracionDTO valoracionDTO = valoracionMapper.toDto(valoracion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValoracionMockMvc.perform(post("/api/valoracions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Valoracion in the database
        List<Valoracion> valoracionList = valoracionRepository.findAll();
        assertThat(valoracionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllValoracions() throws Exception {
        // Initialize the database
        valoracionRepository.saveAndFlush(valoracion);

        // Get all the valoracionList
        restValoracionMockMvc.perform(get("/api/valoracions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valoracion.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoValoracion").value(hasItem(DEFAULT_TIPO_VALORACION.toString())));
    }

    @Test
    @Transactional
    public void getValoracion() throws Exception {
        // Initialize the database
        valoracionRepository.saveAndFlush(valoracion);

        // Get the valoracion
        restValoracionMockMvc.perform(get("/api/valoracions/{id}", valoracion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valoracion.getId().intValue()))
            .andExpect(jsonPath("$.tipoValoracion").value(DEFAULT_TIPO_VALORACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingValoracion() throws Exception {
        // Get the valoracion
        restValoracionMockMvc.perform(get("/api/valoracions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValoracion() throws Exception {
        // Initialize the database
        valoracionRepository.saveAndFlush(valoracion);
        int databaseSizeBeforeUpdate = valoracionRepository.findAll().size();

        // Update the valoracion
        Valoracion updatedValoracion = valoracionRepository.findOne(valoracion.getId());
        // Disconnect from session so that the updates on updatedValoracion are not directly saved in db
        em.detach(updatedValoracion);
        updatedValoracion
            .tipoValoracion(UPDATED_TIPO_VALORACION);
        ValoracionDTO valoracionDTO = valoracionMapper.toDto(updatedValoracion);

        restValoracionMockMvc.perform(put("/api/valoracions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionDTO)))
            .andExpect(status().isOk());

        // Validate the Valoracion in the database
        List<Valoracion> valoracionList = valoracionRepository.findAll();
        assertThat(valoracionList).hasSize(databaseSizeBeforeUpdate);
        Valoracion testValoracion = valoracionList.get(valoracionList.size() - 1);
        assertThat(testValoracion.getTipoValoracion()).isEqualTo(UPDATED_TIPO_VALORACION);
    }

    @Test
    @Transactional
    public void updateNonExistingValoracion() throws Exception {
        int databaseSizeBeforeUpdate = valoracionRepository.findAll().size();

        // Create the Valoracion
        ValoracionDTO valoracionDTO = valoracionMapper.toDto(valoracion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValoracionMockMvc.perform(put("/api/valoracions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionDTO)))
            .andExpect(status().isCreated());

        // Validate the Valoracion in the database
        List<Valoracion> valoracionList = valoracionRepository.findAll();
        assertThat(valoracionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValoracion() throws Exception {
        // Initialize the database
        valoracionRepository.saveAndFlush(valoracion);
        int databaseSizeBeforeDelete = valoracionRepository.findAll().size();

        // Get the valoracion
        restValoracionMockMvc.perform(delete("/api/valoracions/{id}", valoracion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Valoracion> valoracionList = valoracionRepository.findAll();
        assertThat(valoracionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Valoracion.class);
        Valoracion valoracion1 = new Valoracion();
        valoracion1.setId(1L);
        Valoracion valoracion2 = new Valoracion();
        valoracion2.setId(valoracion1.getId());
        assertThat(valoracion1).isEqualTo(valoracion2);
        valoracion2.setId(2L);
        assertThat(valoracion1).isNotEqualTo(valoracion2);
        valoracion1.setId(null);
        assertThat(valoracion1).isNotEqualTo(valoracion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValoracionDTO.class);
        ValoracionDTO valoracionDTO1 = new ValoracionDTO();
        valoracionDTO1.setId(1L);
        ValoracionDTO valoracionDTO2 = new ValoracionDTO();
        assertThat(valoracionDTO1).isNotEqualTo(valoracionDTO2);
        valoracionDTO2.setId(valoracionDTO1.getId());
        assertThat(valoracionDTO1).isEqualTo(valoracionDTO2);
        valoracionDTO2.setId(2L);
        assertThat(valoracionDTO1).isNotEqualTo(valoracionDTO2);
        valoracionDTO1.setId(null);
        assertThat(valoracionDTO1).isNotEqualTo(valoracionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(valoracionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(valoracionMapper.fromId(null)).isNull();
    }
}

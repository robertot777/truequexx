package truequep.web.rest;

import truequep.TruequepApp;

import truequep.domain.RespuestaOferta;
import truequep.repository.RespuestaOfertaRepository;
import truequep.service.RespuestaOfertaService;
import truequep.service.dto.RespuestaOfertaDTO;
import truequep.service.mapper.RespuestaOfertaMapper;
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

import truequep.domain.enumeration.OfertaRespuesta;
/**
 * Test class for the RespuestaOfertaResource REST controller.
 *
 * @see RespuestaOfertaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruequepApp.class)
public class RespuestaOfertaResourceIntTest {

    private static final OfertaRespuesta DEFAULT_ACEPTAR = OfertaRespuesta.ACEPTA;
    private static final OfertaRespuesta UPDATED_ACEPTAR = OfertaRespuesta.NOACEPTA;

    @Autowired
    private RespuestaOfertaRepository respuestaOfertaRepository;

    @Autowired
    private RespuestaOfertaMapper respuestaOfertaMapper;

    @Autowired
    private RespuestaOfertaService respuestaOfertaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRespuestaOfertaMockMvc;

    private RespuestaOferta respuestaOferta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RespuestaOfertaResource respuestaOfertaResource = new RespuestaOfertaResource(respuestaOfertaService);
        this.restRespuestaOfertaMockMvc = MockMvcBuilders.standaloneSetup(respuestaOfertaResource)
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
    public static RespuestaOferta createEntity(EntityManager em) {
        RespuestaOferta respuestaOferta = new RespuestaOferta()
            .aceptar(DEFAULT_ACEPTAR);
        return respuestaOferta;
    }

    @Before
    public void initTest() {
        respuestaOferta = createEntity(em);
    }

    @Test
    @Transactional
    public void createRespuestaOferta() throws Exception {
        int databaseSizeBeforeCreate = respuestaOfertaRepository.findAll().size();

        // Create the RespuestaOferta
        RespuestaOfertaDTO respuestaOfertaDTO = respuestaOfertaMapper.toDto(respuestaOferta);
        restRespuestaOfertaMockMvc.perform(post("/api/respuesta-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuestaOfertaDTO)))
            .andExpect(status().isCreated());

        // Validate the RespuestaOferta in the database
        List<RespuestaOferta> respuestaOfertaList = respuestaOfertaRepository.findAll();
        assertThat(respuestaOfertaList).hasSize(databaseSizeBeforeCreate + 1);
        RespuestaOferta testRespuestaOferta = respuestaOfertaList.get(respuestaOfertaList.size() - 1);
        assertThat(testRespuestaOferta.getAceptar()).isEqualTo(DEFAULT_ACEPTAR);
    }

    @Test
    @Transactional
    public void createRespuestaOfertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = respuestaOfertaRepository.findAll().size();

        // Create the RespuestaOferta with an existing ID
        respuestaOferta.setId(1L);
        RespuestaOfertaDTO respuestaOfertaDTO = respuestaOfertaMapper.toDto(respuestaOferta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRespuestaOfertaMockMvc.perform(post("/api/respuesta-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuestaOfertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RespuestaOferta in the database
        List<RespuestaOferta> respuestaOfertaList = respuestaOfertaRepository.findAll();
        assertThat(respuestaOfertaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAceptarIsRequired() throws Exception {
        int databaseSizeBeforeTest = respuestaOfertaRepository.findAll().size();
        // set the field null
        respuestaOferta.setAceptar(null);

        // Create the RespuestaOferta, which fails.
        RespuestaOfertaDTO respuestaOfertaDTO = respuestaOfertaMapper.toDto(respuestaOferta);

        restRespuestaOfertaMockMvc.perform(post("/api/respuesta-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuestaOfertaDTO)))
            .andExpect(status().isBadRequest());

        List<RespuestaOferta> respuestaOfertaList = respuestaOfertaRepository.findAll();
        assertThat(respuestaOfertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRespuestaOfertas() throws Exception {
        // Initialize the database
        respuestaOfertaRepository.saveAndFlush(respuestaOferta);

        // Get all the respuestaOfertaList
        restRespuestaOfertaMockMvc.perform(get("/api/respuesta-ofertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(respuestaOferta.getId().intValue())))
            .andExpect(jsonPath("$.[*].aceptar").value(hasItem(DEFAULT_ACEPTAR.toString())));
    }

    @Test
    @Transactional
    public void getRespuestaOferta() throws Exception {
        // Initialize the database
        respuestaOfertaRepository.saveAndFlush(respuestaOferta);

        // Get the respuestaOferta
        restRespuestaOfertaMockMvc.perform(get("/api/respuesta-ofertas/{id}", respuestaOferta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(respuestaOferta.getId().intValue()))
            .andExpect(jsonPath("$.aceptar").value(DEFAULT_ACEPTAR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRespuestaOferta() throws Exception {
        // Get the respuestaOferta
        restRespuestaOfertaMockMvc.perform(get("/api/respuesta-ofertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRespuestaOferta() throws Exception {
        // Initialize the database
        respuestaOfertaRepository.saveAndFlush(respuestaOferta);
        int databaseSizeBeforeUpdate = respuestaOfertaRepository.findAll().size();

        // Update the respuestaOferta
        RespuestaOferta updatedRespuestaOferta = respuestaOfertaRepository.findOne(respuestaOferta.getId());
        // Disconnect from session so that the updates on updatedRespuestaOferta are not directly saved in db
        em.detach(updatedRespuestaOferta);
        updatedRespuestaOferta
            .aceptar(UPDATED_ACEPTAR);
        RespuestaOfertaDTO respuestaOfertaDTO = respuestaOfertaMapper.toDto(updatedRespuestaOferta);

        restRespuestaOfertaMockMvc.perform(put("/api/respuesta-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuestaOfertaDTO)))
            .andExpect(status().isOk());

        // Validate the RespuestaOferta in the database
        List<RespuestaOferta> respuestaOfertaList = respuestaOfertaRepository.findAll();
        assertThat(respuestaOfertaList).hasSize(databaseSizeBeforeUpdate);
        RespuestaOferta testRespuestaOferta = respuestaOfertaList.get(respuestaOfertaList.size() - 1);
        assertThat(testRespuestaOferta.getAceptar()).isEqualTo(UPDATED_ACEPTAR);
    }

    @Test
    @Transactional
    public void updateNonExistingRespuestaOferta() throws Exception {
        int databaseSizeBeforeUpdate = respuestaOfertaRepository.findAll().size();

        // Create the RespuestaOferta
        RespuestaOfertaDTO respuestaOfertaDTO = respuestaOfertaMapper.toDto(respuestaOferta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRespuestaOfertaMockMvc.perform(put("/api/respuesta-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuestaOfertaDTO)))
            .andExpect(status().isCreated());

        // Validate the RespuestaOferta in the database
        List<RespuestaOferta> respuestaOfertaList = respuestaOfertaRepository.findAll();
        assertThat(respuestaOfertaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRespuestaOferta() throws Exception {
        // Initialize the database
        respuestaOfertaRepository.saveAndFlush(respuestaOferta);
        int databaseSizeBeforeDelete = respuestaOfertaRepository.findAll().size();

        // Get the respuestaOferta
        restRespuestaOfertaMockMvc.perform(delete("/api/respuesta-ofertas/{id}", respuestaOferta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RespuestaOferta> respuestaOfertaList = respuestaOfertaRepository.findAll();
        assertThat(respuestaOfertaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RespuestaOferta.class);
        RespuestaOferta respuestaOferta1 = new RespuestaOferta();
        respuestaOferta1.setId(1L);
        RespuestaOferta respuestaOferta2 = new RespuestaOferta();
        respuestaOferta2.setId(respuestaOferta1.getId());
        assertThat(respuestaOferta1).isEqualTo(respuestaOferta2);
        respuestaOferta2.setId(2L);
        assertThat(respuestaOferta1).isNotEqualTo(respuestaOferta2);
        respuestaOferta1.setId(null);
        assertThat(respuestaOferta1).isNotEqualTo(respuestaOferta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RespuestaOfertaDTO.class);
        RespuestaOfertaDTO respuestaOfertaDTO1 = new RespuestaOfertaDTO();
        respuestaOfertaDTO1.setId(1L);
        RespuestaOfertaDTO respuestaOfertaDTO2 = new RespuestaOfertaDTO();
        assertThat(respuestaOfertaDTO1).isNotEqualTo(respuestaOfertaDTO2);
        respuestaOfertaDTO2.setId(respuestaOfertaDTO1.getId());
        assertThat(respuestaOfertaDTO1).isEqualTo(respuestaOfertaDTO2);
        respuestaOfertaDTO2.setId(2L);
        assertThat(respuestaOfertaDTO1).isNotEqualTo(respuestaOfertaDTO2);
        respuestaOfertaDTO1.setId(null);
        assertThat(respuestaOfertaDTO1).isNotEqualTo(respuestaOfertaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(respuestaOfertaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(respuestaOfertaMapper.fromId(null)).isNull();
    }
}

package truequep.web.rest;

import truequep.TruequepApp;

import truequep.domain.Oferta;
import truequep.repository.OfertaRepository;
import truequep.service.OfertaService;
import truequep.service.dto.OfertaDTO;
import truequep.service.mapper.OfertaMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static truequep.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import truequep.domain.enumeration.TipoObjeto;
/**
 * Test class for the OfertaResource REST controller.
 *
 * @see OfertaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruequepApp.class)
public class OfertaResourceIntTest {

    private static final String DEFAULT_GENERAR_OFERTA = "AAAAAAAAAA";
    private static final String UPDATED_GENERAR_OFERTA = "BBBBBBBBBB";

    private static final TipoObjeto DEFAULT_TIPO_OBJETO = TipoObjeto.ALIMENTACIONBEBIDAS;
    private static final TipoObjeto UPDATED_TIPO_OBJETO = TipoObjeto.ARTEANTIGUEDADES;

    private static final byte[] DEFAULT_GENERA_OFERTA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GENERA_OFERTA = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_GENERA_OFERTA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GENERA_OFERTA_CONTENT_TYPE = "image/png";

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private OfertaMapper ofertaMapper;

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOfertaMockMvc;

    private Oferta oferta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfertaResource ofertaResource = new OfertaResource(ofertaService);
        this.restOfertaMockMvc = MockMvcBuilders.standaloneSetup(ofertaResource)
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
    public static Oferta createEntity(EntityManager em) {
        Oferta oferta = new Oferta()
            .generarOferta(DEFAULT_GENERAR_OFERTA)
            .tipoObjeto(DEFAULT_TIPO_OBJETO)
            .generaOferta(DEFAULT_GENERA_OFERTA)
            .generaOfertaContentType(DEFAULT_GENERA_OFERTA_CONTENT_TYPE);
        return oferta;
    }

    @Before
    public void initTest() {
        oferta = createEntity(em);
    }

    @Test
    @Transactional
    public void createOferta() throws Exception {
        int databaseSizeBeforeCreate = ofertaRepository.findAll().size();

        // Create the Oferta
        OfertaDTO ofertaDTO = ofertaMapper.toDto(oferta);
        restOfertaMockMvc.perform(post("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaDTO)))
            .andExpect(status().isCreated());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeCreate + 1);
        Oferta testOferta = ofertaList.get(ofertaList.size() - 1);
        assertThat(testOferta.getGenerarOferta()).isEqualTo(DEFAULT_GENERAR_OFERTA);
        assertThat(testOferta.getTipoObjeto()).isEqualTo(DEFAULT_TIPO_OBJETO);
        assertThat(testOferta.getGeneraOferta()).isEqualTo(DEFAULT_GENERA_OFERTA);
        assertThat(testOferta.getGeneraOfertaContentType()).isEqualTo(DEFAULT_GENERA_OFERTA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createOfertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ofertaRepository.findAll().size();

        // Create the Oferta with an existing ID
        oferta.setId(1L);
        OfertaDTO ofertaDTO = ofertaMapper.toDto(oferta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfertaMockMvc.perform(post("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGenerarOfertaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ofertaRepository.findAll().size();
        // set the field null
        oferta.setGenerarOferta(null);

        // Create the Oferta, which fails.
        OfertaDTO ofertaDTO = ofertaMapper.toDto(oferta);

        restOfertaMockMvc.perform(post("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaDTO)))
            .andExpect(status().isBadRequest());

        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneraOfertaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ofertaRepository.findAll().size();
        // set the field null
        oferta.setGeneraOferta(null);

        // Create the Oferta, which fails.
        OfertaDTO ofertaDTO = ofertaMapper.toDto(oferta);

        restOfertaMockMvc.perform(post("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaDTO)))
            .andExpect(status().isBadRequest());

        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOfertas() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);

        // Get all the ofertaList
        restOfertaMockMvc.perform(get("/api/ofertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oferta.getId().intValue())))
            .andExpect(jsonPath("$.[*].generarOferta").value(hasItem(DEFAULT_GENERAR_OFERTA.toString())))
            .andExpect(jsonPath("$.[*].tipoObjeto").value(hasItem(DEFAULT_TIPO_OBJETO.toString())))
            .andExpect(jsonPath("$.[*].generaOfertaContentType").value(hasItem(DEFAULT_GENERA_OFERTA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].generaOferta").value(hasItem(Base64Utils.encodeToString(DEFAULT_GENERA_OFERTA))));
    }

    @Test
    @Transactional
    public void getOferta() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);

        // Get the oferta
        restOfertaMockMvc.perform(get("/api/ofertas/{id}", oferta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oferta.getId().intValue()))
            .andExpect(jsonPath("$.generarOferta").value(DEFAULT_GENERAR_OFERTA.toString()))
            .andExpect(jsonPath("$.tipoObjeto").value(DEFAULT_TIPO_OBJETO.toString()))
            .andExpect(jsonPath("$.generaOfertaContentType").value(DEFAULT_GENERA_OFERTA_CONTENT_TYPE))
            .andExpect(jsonPath("$.generaOferta").value(Base64Utils.encodeToString(DEFAULT_GENERA_OFERTA)));
    }

    @Test
    @Transactional
    public void getNonExistingOferta() throws Exception {
        // Get the oferta
        restOfertaMockMvc.perform(get("/api/ofertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOferta() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);
        int databaseSizeBeforeUpdate = ofertaRepository.findAll().size();

        // Update the oferta
        Oferta updatedOferta = ofertaRepository.findOne(oferta.getId());
        // Disconnect from session so that the updates on updatedOferta are not directly saved in db
        em.detach(updatedOferta);
        updatedOferta
            .generarOferta(UPDATED_GENERAR_OFERTA)
            .tipoObjeto(UPDATED_TIPO_OBJETO)
            .generaOferta(UPDATED_GENERA_OFERTA)
            .generaOfertaContentType(UPDATED_GENERA_OFERTA_CONTENT_TYPE);
        OfertaDTO ofertaDTO = ofertaMapper.toDto(updatedOferta);

        restOfertaMockMvc.perform(put("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaDTO)))
            .andExpect(status().isOk());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeUpdate);
        Oferta testOferta = ofertaList.get(ofertaList.size() - 1);
        assertThat(testOferta.getGenerarOferta()).isEqualTo(UPDATED_GENERAR_OFERTA);
        assertThat(testOferta.getTipoObjeto()).isEqualTo(UPDATED_TIPO_OBJETO);
        assertThat(testOferta.getGeneraOferta()).isEqualTo(UPDATED_GENERA_OFERTA);
        assertThat(testOferta.getGeneraOfertaContentType()).isEqualTo(UPDATED_GENERA_OFERTA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingOferta() throws Exception {
        int databaseSizeBeforeUpdate = ofertaRepository.findAll().size();

        // Create the Oferta
        OfertaDTO ofertaDTO = ofertaMapper.toDto(oferta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOfertaMockMvc.perform(put("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ofertaDTO)))
            .andExpect(status().isCreated());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOferta() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);
        int databaseSizeBeforeDelete = ofertaRepository.findAll().size();

        // Get the oferta
        restOfertaMockMvc.perform(delete("/api/ofertas/{id}", oferta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Oferta.class);
        Oferta oferta1 = new Oferta();
        oferta1.setId(1L);
        Oferta oferta2 = new Oferta();
        oferta2.setId(oferta1.getId());
        assertThat(oferta1).isEqualTo(oferta2);
        oferta2.setId(2L);
        assertThat(oferta1).isNotEqualTo(oferta2);
        oferta1.setId(null);
        assertThat(oferta1).isNotEqualTo(oferta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfertaDTO.class);
        OfertaDTO ofertaDTO1 = new OfertaDTO();
        ofertaDTO1.setId(1L);
        OfertaDTO ofertaDTO2 = new OfertaDTO();
        assertThat(ofertaDTO1).isNotEqualTo(ofertaDTO2);
        ofertaDTO2.setId(ofertaDTO1.getId());
        assertThat(ofertaDTO1).isEqualTo(ofertaDTO2);
        ofertaDTO2.setId(2L);
        assertThat(ofertaDTO1).isNotEqualTo(ofertaDTO2);
        ofertaDTO1.setId(null);
        assertThat(ofertaDTO1).isNotEqualTo(ofertaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ofertaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ofertaMapper.fromId(null)).isNull();
    }
}

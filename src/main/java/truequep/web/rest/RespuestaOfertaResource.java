package truequep.web.rest;

import com.codahale.metrics.annotation.Timed;
import truequep.service.RespuestaOfertaService;
import truequep.web.rest.errors.BadRequestAlertException;
import truequep.web.rest.util.HeaderUtil;
import truequep.service.dto.RespuestaOfertaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RespuestaOferta.
 */
@RestController
@RequestMapping("/api")
public class RespuestaOfertaResource {

    private final Logger log = LoggerFactory.getLogger(RespuestaOfertaResource.class);

    private static final String ENTITY_NAME = "respuestaOferta";

    private final RespuestaOfertaService respuestaOfertaService;

    public RespuestaOfertaResource(RespuestaOfertaService respuestaOfertaService) {
        this.respuestaOfertaService = respuestaOfertaService;
    }

    /**
     * POST  /respuesta-ofertas : Create a new respuestaOferta.
     *
     * @param respuestaOfertaDTO the respuestaOfertaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new respuestaOfertaDTO, or with status 400 (Bad Request) if the respuestaOferta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/respuesta-ofertas")
    @Timed
    public ResponseEntity<RespuestaOfertaDTO> createRespuestaOferta(@Valid @RequestBody RespuestaOfertaDTO respuestaOfertaDTO) throws URISyntaxException {
        log.debug("REST request to save RespuestaOferta : {}", respuestaOfertaDTO);
        if (respuestaOfertaDTO.getId() != null) {
            throw new BadRequestAlertException("A new respuestaOferta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RespuestaOfertaDTO result = respuestaOfertaService.save(respuestaOfertaDTO);
        return ResponseEntity.created(new URI("/api/respuesta-ofertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /respuesta-ofertas : Updates an existing respuestaOferta.
     *
     * @param respuestaOfertaDTO the respuestaOfertaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated respuestaOfertaDTO,
     * or with status 400 (Bad Request) if the respuestaOfertaDTO is not valid,
     * or with status 500 (Internal Server Error) if the respuestaOfertaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/respuesta-ofertas")
    @Timed
    public ResponseEntity<RespuestaOfertaDTO> updateRespuestaOferta(@Valid @RequestBody RespuestaOfertaDTO respuestaOfertaDTO) throws URISyntaxException {
        log.debug("REST request to update RespuestaOferta : {}", respuestaOfertaDTO);
        if (respuestaOfertaDTO.getId() == null) {
            return createRespuestaOferta(respuestaOfertaDTO);
        }
        RespuestaOfertaDTO result = respuestaOfertaService.save(respuestaOfertaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, respuestaOfertaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /respuesta-ofertas : get all the respuestaOfertas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of respuestaOfertas in body
     */
    @GetMapping("/respuesta-ofertas")
    @Timed
    public List<RespuestaOfertaDTO> getAllRespuestaOfertas() {
        log.debug("REST request to get all RespuestaOfertas");
        return respuestaOfertaService.findAll();
        }

    /**
     * GET  /respuesta-ofertas/:id : get the "id" respuestaOferta.
     *
     * @param id the id of the respuestaOfertaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the respuestaOfertaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/respuesta-ofertas/{id}")
    @Timed
    public ResponseEntity<RespuestaOfertaDTO> getRespuestaOferta(@PathVariable Long id) {
        log.debug("REST request to get RespuestaOferta : {}", id);
        RespuestaOfertaDTO respuestaOfertaDTO = respuestaOfertaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(respuestaOfertaDTO));
    }

    /**
     * DELETE  /respuesta-ofertas/:id : delete the "id" respuestaOferta.
     *
     * @param id the id of the respuestaOfertaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/respuesta-ofertas/{id}")
    @Timed
    public ResponseEntity<Void> deleteRespuestaOferta(@PathVariable Long id) {
        log.debug("REST request to delete RespuestaOferta : {}", id);
        respuestaOfertaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

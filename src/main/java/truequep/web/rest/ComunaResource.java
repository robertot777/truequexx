package truequep.web.rest;

import com.codahale.metrics.annotation.Timed;
import truequep.service.ComunaService;
import truequep.web.rest.errors.BadRequestAlertException;
import truequep.web.rest.util.HeaderUtil;
import truequep.service.dto.ComunaDTO;
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
 * REST controller for managing Comuna.
 */
@RestController
@RequestMapping("/api")
public class ComunaResource {

    private final Logger log = LoggerFactory.getLogger(ComunaResource.class);

    private static final String ENTITY_NAME = "comuna";

    private final ComunaService comunaService;

    public ComunaResource(ComunaService comunaService) {
        this.comunaService = comunaService;
    }

    /**
     * POST  /comunas : Create a new comuna.
     *
     * @param comunaDTO the comunaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comunaDTO, or with status 400 (Bad Request) if the comuna has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comunas")
    @Timed
    public ResponseEntity<ComunaDTO> createComuna(@Valid @RequestBody ComunaDTO comunaDTO) throws URISyntaxException {
        log.debug("REST request to save Comuna : {}", comunaDTO);
        if (comunaDTO.getId() != null) {
            throw new BadRequestAlertException("A new comuna cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComunaDTO result = comunaService.save(comunaDTO);
        return ResponseEntity.created(new URI("/api/comunas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comunas : Updates an existing comuna.
     *
     * @param comunaDTO the comunaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comunaDTO,
     * or with status 400 (Bad Request) if the comunaDTO is not valid,
     * or with status 500 (Internal Server Error) if the comunaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comunas")
    @Timed
    public ResponseEntity<ComunaDTO> updateComuna(@Valid @RequestBody ComunaDTO comunaDTO) throws URISyntaxException {
        log.debug("REST request to update Comuna : {}", comunaDTO);
        if (comunaDTO.getId() == null) {
            return createComuna(comunaDTO);
        }
        ComunaDTO result = comunaService.save(comunaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comunaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comunas : get all the comunas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comunas in body
     */
    @GetMapping("/comunas")
    @Timed
    public List<ComunaDTO> getAllComunas() {
        log.debug("REST request to get all Comunas");
        return comunaService.findAll();
        }

    /**
     * GET  /comunas/:id : get the "id" comuna.
     *
     * @param id the id of the comunaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comunaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comunas/{id}")
    @Timed
    public ResponseEntity<ComunaDTO> getComuna(@PathVariable Long id) {
        log.debug("REST request to get Comuna : {}", id);
        ComunaDTO comunaDTO = comunaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comunaDTO));
    }

    /**
     * DELETE  /comunas/:id : delete the "id" comuna.
     *
     * @param id the id of the comunaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comunas/{id}")
    @Timed
    public ResponseEntity<Void> deleteComuna(@PathVariable Long id) {
        log.debug("REST request to delete Comuna : {}", id);
        comunaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

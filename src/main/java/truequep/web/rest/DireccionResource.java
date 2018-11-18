package truequep.web.rest;

import com.codahale.metrics.annotation.Timed;
import truequep.service.DireccionService;
import truequep.web.rest.errors.BadRequestAlertException;
import truequep.web.rest.util.HeaderUtil;
import truequep.service.dto.DireccionDTO;
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
 * REST controller for managing Direccion.
 */
@RestController
@RequestMapping("/api")
public class DireccionResource {

    private final Logger log = LoggerFactory.getLogger(DireccionResource.class);

    private static final String ENTITY_NAME = "direccion";

    private final DireccionService direccionService;

    public DireccionResource(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    /**
     * POST  /direccions : Create a new direccion.
     *
     * @param direccionDTO the direccionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new direccionDTO, or with status 400 (Bad Request) if the direccion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/direccions")
    @Timed
    public ResponseEntity<DireccionDTO> createDireccion(@Valid @RequestBody DireccionDTO direccionDTO) throws URISyntaxException {
        log.debug("REST request to save Direccion : {}", direccionDTO);
        if (direccionDTO.getId() != null) {
            throw new BadRequestAlertException("A new direccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DireccionDTO result = direccionService.save(direccionDTO);
        return ResponseEntity.created(new URI("/api/direccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /direccions : Updates an existing direccion.
     *
     * @param direccionDTO the direccionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated direccionDTO,
     * or with status 400 (Bad Request) if the direccionDTO is not valid,
     * or with status 500 (Internal Server Error) if the direccionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/direccions")
    @Timed
    public ResponseEntity<DireccionDTO> updateDireccion(@Valid @RequestBody DireccionDTO direccionDTO) throws URISyntaxException {
        log.debug("REST request to update Direccion : {}", direccionDTO);
        if (direccionDTO.getId() == null) {
            return createDireccion(direccionDTO);
        }
        DireccionDTO result = direccionService.save(direccionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, direccionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /direccions : get all the direccions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of direccions in body
     */
    @GetMapping("/direccions")
    @Timed
    public List<DireccionDTO> getAllDireccions() {
        log.debug("REST request to get all Direccions");
        return direccionService.findAll();
        }

    /**
     * GET  /direccions/:id : get the "id" direccion.
     *
     * @param id the id of the direccionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the direccionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/direccions/{id}")
    @Timed
    public ResponseEntity<DireccionDTO> getDireccion(@PathVariable Long id) {
        log.debug("REST request to get Direccion : {}", id);
        DireccionDTO direccionDTO = direccionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(direccionDTO));
    }

    /**
     * DELETE  /direccions/:id : delete the "id" direccion.
     *
     * @param id the id of the direccionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/direccions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDireccion(@PathVariable Long id) {
        log.debug("REST request to delete Direccion : {}", id);
        direccionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

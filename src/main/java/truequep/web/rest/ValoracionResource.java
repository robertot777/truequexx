package truequep.web.rest;

import com.codahale.metrics.annotation.Timed;
import truequep.service.ValoracionService;
import truequep.web.rest.errors.BadRequestAlertException;
import truequep.web.rest.util.HeaderUtil;
import truequep.service.dto.ValoracionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Valoracion.
 */
@RestController
@RequestMapping("/api")
public class ValoracionResource {

    private final Logger log = LoggerFactory.getLogger(ValoracionResource.class);

    private static final String ENTITY_NAME = "valoracion";

    private final ValoracionService valoracionService;

    public ValoracionResource(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    /**
     * POST  /valoracions : Create a new valoracion.
     *
     * @param valoracionDTO the valoracionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valoracionDTO, or with status 400 (Bad Request) if the valoracion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valoracions")
    @Timed
    public ResponseEntity<ValoracionDTO> createValoracion(@RequestBody ValoracionDTO valoracionDTO) throws URISyntaxException {
        log.debug("REST request to save Valoracion : {}", valoracionDTO);
        if (valoracionDTO.getId() != null) {
            throw new BadRequestAlertException("A new valoracion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValoracionDTO result = valoracionService.save(valoracionDTO);
        return ResponseEntity.created(new URI("/api/valoracions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valoracions : Updates an existing valoracion.
     *
     * @param valoracionDTO the valoracionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valoracionDTO,
     * or with status 400 (Bad Request) if the valoracionDTO is not valid,
     * or with status 500 (Internal Server Error) if the valoracionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valoracions")
    @Timed
    public ResponseEntity<ValoracionDTO> updateValoracion(@RequestBody ValoracionDTO valoracionDTO) throws URISyntaxException {
        log.debug("REST request to update Valoracion : {}", valoracionDTO);
        if (valoracionDTO.getId() == null) {
            return createValoracion(valoracionDTO);
        }
        ValoracionDTO result = valoracionService.save(valoracionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valoracionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valoracions : get all the valoracions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valoracions in body
     */
    @GetMapping("/valoracions")
    @Timed
    public List<ValoracionDTO> getAllValoracions() {
        log.debug("REST request to get all Valoracions");
        return valoracionService.findAll();
        }

    /**
     * GET  /valoracions/:id : get the "id" valoracion.
     *
     * @param id the id of the valoracionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valoracionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/valoracions/{id}")
    @Timed
    public ResponseEntity<ValoracionDTO> getValoracion(@PathVariable Long id) {
        log.debug("REST request to get Valoracion : {}", id);
        ValoracionDTO valoracionDTO = valoracionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valoracionDTO));
    }

    /**
     * DELETE  /valoracions/:id : delete the "id" valoracion.
     *
     * @param id the id of the valoracionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valoracions/{id}")
    @Timed
    public ResponseEntity<Void> deleteValoracion(@PathVariable Long id) {
        log.debug("REST request to delete Valoracion : {}", id);
        valoracionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

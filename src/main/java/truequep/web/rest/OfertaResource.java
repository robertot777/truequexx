package truequep.web.rest;

import com.codahale.metrics.annotation.Timed;
import truequep.service.OfertaService;
import truequep.web.rest.errors.BadRequestAlertException;
import truequep.web.rest.util.HeaderUtil;
import truequep.service.dto.OfertaDTO;
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
 * REST controller for managing Oferta.
 */
@RestController
@RequestMapping("/api")
public class OfertaResource {

    private final Logger log = LoggerFactory.getLogger(OfertaResource.class);

    private static final String ENTITY_NAME = "oferta";

    private final OfertaService ofertaService;

    public OfertaResource(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    /**
     * POST  /ofertas : Create a new oferta.
     *
     * @param ofertaDTO the ofertaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ofertaDTO, or with status 400 (Bad Request) if the oferta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ofertas")
    @Timed
    public ResponseEntity<OfertaDTO> createOferta(@Valid @RequestBody OfertaDTO ofertaDTO) throws URISyntaxException {
        log.debug("REST request to save Oferta : {}", ofertaDTO);
        if (ofertaDTO.getId() != null) {
            throw new BadRequestAlertException("A new oferta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfertaDTO result = ofertaService.save(ofertaDTO);
        return ResponseEntity.created(new URI("/api/ofertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ofertas : Updates an existing oferta.
     *
     * @param ofertaDTO the ofertaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ofertaDTO,
     * or with status 400 (Bad Request) if the ofertaDTO is not valid,
     * or with status 500 (Internal Server Error) if the ofertaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ofertas")
    @Timed
    public ResponseEntity<OfertaDTO> updateOferta(@Valid @RequestBody OfertaDTO ofertaDTO) throws URISyntaxException {
        log.debug("REST request to update Oferta : {}", ofertaDTO);
        if (ofertaDTO.getId() == null) {
            return createOferta(ofertaDTO);
        }
        OfertaDTO result = ofertaService.save(ofertaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ofertaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ofertas : get all the ofertas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ofertas in body
     */
    @GetMapping("/ofertas")
    @Timed
    public List<OfertaDTO> getAllOfertas() {
        log.debug("REST request to get all Ofertas");
        return ofertaService.findAll();
        }

    /**
     * GET  /ofertas/:id : get the "id" oferta.
     *
     * @param id the id of the ofertaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ofertaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ofertas/{id}")
    @Timed
    public ResponseEntity<OfertaDTO> getOferta(@PathVariable Long id) {
        log.debug("REST request to get Oferta : {}", id);
        OfertaDTO ofertaDTO = ofertaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ofertaDTO));
    }

    /**
     * DELETE  /ofertas/:id : delete the "id" oferta.
     *
     * @param id the id of the ofertaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ofertas/{id}")
    @Timed
    public ResponseEntity<Void> deleteOferta(@PathVariable Long id) {
        log.debug("REST request to delete Oferta : {}", id);
        ofertaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

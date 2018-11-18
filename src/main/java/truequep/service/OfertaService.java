package truequep.service;

import truequep.service.dto.OfertaDTO;
import java.util.List;

/**
 * Service Interface for managing Oferta.
 */
public interface OfertaService {

    /**
     * Save a oferta.
     *
     * @param ofertaDTO the entity to save
     * @return the persisted entity
     */
    OfertaDTO save(OfertaDTO ofertaDTO);

    /**
     * Get all the ofertas.
     *
     * @return the list of entities
     */
    List<OfertaDTO> findAll();

    /**
     * Get the "id" oferta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OfertaDTO findOne(Long id);

    /**
     * Delete the "id" oferta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

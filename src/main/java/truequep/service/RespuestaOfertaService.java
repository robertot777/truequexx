package truequep.service;

import truequep.service.dto.RespuestaOfertaDTO;
import java.util.List;

/**
 * Service Interface for managing RespuestaOferta.
 */
public interface RespuestaOfertaService {

    /**
     * Save a respuestaOferta.
     *
     * @param respuestaOfertaDTO the entity to save
     * @return the persisted entity
     */
    RespuestaOfertaDTO save(RespuestaOfertaDTO respuestaOfertaDTO);

    /**
     * Get all the respuestaOfertas.
     *
     * @return the list of entities
     */
    List<RespuestaOfertaDTO> findAll();

    /**
     * Get the "id" respuestaOferta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RespuestaOfertaDTO findOne(Long id);

    /**
     * Delete the "id" respuestaOferta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package truequep.service;

import truequep.service.dto.DireccionDTO;
import java.util.List;

/**
 * Service Interface for managing Direccion.
 */
public interface DireccionService {

    /**
     * Save a direccion.
     *
     * @param direccionDTO the entity to save
     * @return the persisted entity
     */
    DireccionDTO save(DireccionDTO direccionDTO);

    /**
     * Get all the direccions.
     *
     * @return the list of entities
     */
    List<DireccionDTO> findAll();

    /**
     * Get the "id" direccion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DireccionDTO findOne(Long id);

    /**
     * Delete the "id" direccion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

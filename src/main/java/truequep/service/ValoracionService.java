package truequep.service;

import truequep.service.dto.ValoracionDTO;
import java.util.List;

/**
 * Service Interface for managing Valoracion.
 */
public interface ValoracionService {

    /**
     * Save a valoracion.
     *
     * @param valoracionDTO the entity to save
     * @return the persisted entity
     */
    ValoracionDTO save(ValoracionDTO valoracionDTO);

    /**
     * Get all the valoracions.
     *
     * @return the list of entities
     */
    List<ValoracionDTO> findAll();

    /**
     * Get the "id" valoracion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ValoracionDTO findOne(Long id);

    /**
     * Delete the "id" valoracion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

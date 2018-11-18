package truequep.service;

import truequep.service.dto.ComunaDTO;
import java.util.List;

/**
 * Service Interface for managing Comuna.
 */
public interface ComunaService {

    /**
     * Save a comuna.
     *
     * @param comunaDTO the entity to save
     * @return the persisted entity
     */
    ComunaDTO save(ComunaDTO comunaDTO);

    /**
     * Get all the comunas.
     *
     * @return the list of entities
     */
    List<ComunaDTO> findAll();

    /**
     * Get the "id" comuna.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ComunaDTO findOne(Long id);

    /**
     * Delete the "id" comuna.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

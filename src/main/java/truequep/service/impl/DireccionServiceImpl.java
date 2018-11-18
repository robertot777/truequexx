package truequep.service.impl;

import truequep.service.DireccionService;
import truequep.domain.Direccion;
import truequep.repository.DireccionRepository;
import truequep.service.dto.DireccionDTO;
import truequep.service.mapper.DireccionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Direccion.
 */
@Service
@Transactional
public class DireccionServiceImpl implements DireccionService{

    private final Logger log = LoggerFactory.getLogger(DireccionServiceImpl.class);

    private final DireccionRepository direccionRepository;

    private final DireccionMapper direccionMapper;

    public DireccionServiceImpl(DireccionRepository direccionRepository, DireccionMapper direccionMapper) {
        this.direccionRepository = direccionRepository;
        this.direccionMapper = direccionMapper;
    }

    /**
     * Save a direccion.
     *
     * @param direccionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DireccionDTO save(DireccionDTO direccionDTO) {
        log.debug("Request to save Direccion : {}", direccionDTO);
        Direccion direccion = direccionMapper.toEntity(direccionDTO);
        direccion = direccionRepository.save(direccion);
        return direccionMapper.toDto(direccion);
    }

    /**
     * Get all the direccions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DireccionDTO> findAll() {
        log.debug("Request to get all Direccions");
        return direccionRepository.findAll().stream()
            .map(direccionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one direccion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DireccionDTO findOne(Long id) {
        log.debug("Request to get Direccion : {}", id);
        Direccion direccion = direccionRepository.findOne(id);
        return direccionMapper.toDto(direccion);
    }

    /**
     * Delete the direccion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Direccion : {}", id);
        direccionRepository.delete(id);
    }
}

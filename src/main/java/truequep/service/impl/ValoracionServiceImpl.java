package truequep.service.impl;

import truequep.service.ValoracionService;
import truequep.domain.Valoracion;
import truequep.repository.ValoracionRepository;
import truequep.service.dto.ValoracionDTO;
import truequep.service.mapper.ValoracionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Valoracion.
 */
@Service
@Transactional
public class ValoracionServiceImpl implements ValoracionService{

    private final Logger log = LoggerFactory.getLogger(ValoracionServiceImpl.class);

    private final ValoracionRepository valoracionRepository;

    private final ValoracionMapper valoracionMapper;

    public ValoracionServiceImpl(ValoracionRepository valoracionRepository, ValoracionMapper valoracionMapper) {
        this.valoracionRepository = valoracionRepository;
        this.valoracionMapper = valoracionMapper;
    }

    /**
     * Save a valoracion.
     *
     * @param valoracionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ValoracionDTO save(ValoracionDTO valoracionDTO) {
        log.debug("Request to save Valoracion : {}", valoracionDTO);
        Valoracion valoracion = valoracionMapper.toEntity(valoracionDTO);
        valoracion = valoracionRepository.save(valoracion);
        return valoracionMapper.toDto(valoracion);
    }

    /**
     * Get all the valoracions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ValoracionDTO> findAll() {
        log.debug("Request to get all Valoracions");
        return valoracionRepository.findAll().stream()
            .map(valoracionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one valoracion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ValoracionDTO findOne(Long id) {
        log.debug("Request to get Valoracion : {}", id);
        Valoracion valoracion = valoracionRepository.findOne(id);
        return valoracionMapper.toDto(valoracion);
    }

    /**
     * Delete the valoracion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Valoracion : {}", id);
        valoracionRepository.delete(id);
    }
}

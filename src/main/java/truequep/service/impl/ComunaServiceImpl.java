package truequep.service.impl;

import truequep.service.ComunaService;
import truequep.domain.Comuna;
import truequep.repository.ComunaRepository;
import truequep.service.dto.ComunaDTO;
import truequep.service.mapper.ComunaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Comuna.
 */
@Service
@Transactional
public class ComunaServiceImpl implements ComunaService{

    private final Logger log = LoggerFactory.getLogger(ComunaServiceImpl.class);

    private final ComunaRepository comunaRepository;

    private final ComunaMapper comunaMapper;

    public ComunaServiceImpl(ComunaRepository comunaRepository, ComunaMapper comunaMapper) {
        this.comunaRepository = comunaRepository;
        this.comunaMapper = comunaMapper;
    }

    /**
     * Save a comuna.
     *
     * @param comunaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ComunaDTO save(ComunaDTO comunaDTO) {
        log.debug("Request to save Comuna : {}", comunaDTO);
        Comuna comuna = comunaMapper.toEntity(comunaDTO);
        comuna = comunaRepository.save(comuna);
        return comunaMapper.toDto(comuna);
    }

    /**
     * Get all the comunas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ComunaDTO> findAll() {
        log.debug("Request to get all Comunas");
        return comunaRepository.findAll().stream()
            .map(comunaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one comuna by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ComunaDTO findOne(Long id) {
        log.debug("Request to get Comuna : {}", id);
        Comuna comuna = comunaRepository.findOne(id);
        return comunaMapper.toDto(comuna);
    }

    /**
     * Delete the comuna by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comuna : {}", id);
        comunaRepository.delete(id);
    }
}

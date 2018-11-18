package truequep.service.impl;

import truequep.service.RespuestaOfertaService;
import truequep.domain.RespuestaOferta;
import truequep.repository.RespuestaOfertaRepository;
import truequep.service.dto.RespuestaOfertaDTO;
import truequep.service.mapper.RespuestaOfertaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RespuestaOferta.
 */
@Service
@Transactional
public class RespuestaOfertaServiceImpl implements RespuestaOfertaService{

    private final Logger log = LoggerFactory.getLogger(RespuestaOfertaServiceImpl.class);

    private final RespuestaOfertaRepository respuestaOfertaRepository;

    private final RespuestaOfertaMapper respuestaOfertaMapper;

    public RespuestaOfertaServiceImpl(RespuestaOfertaRepository respuestaOfertaRepository, RespuestaOfertaMapper respuestaOfertaMapper) {
        this.respuestaOfertaRepository = respuestaOfertaRepository;
        this.respuestaOfertaMapper = respuestaOfertaMapper;
    }

    /**
     * Save a respuestaOferta.
     *
     * @param respuestaOfertaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RespuestaOfertaDTO save(RespuestaOfertaDTO respuestaOfertaDTO) {
        log.debug("Request to save RespuestaOferta : {}", respuestaOfertaDTO);
        RespuestaOferta respuestaOferta = respuestaOfertaMapper.toEntity(respuestaOfertaDTO);
        respuestaOferta = respuestaOfertaRepository.save(respuestaOferta);
        return respuestaOfertaMapper.toDto(respuestaOferta);
    }

    /**
     * Get all the respuestaOfertas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RespuestaOfertaDTO> findAll() {
        log.debug("Request to get all RespuestaOfertas");
        return respuestaOfertaRepository.findAllWithEagerRelationships().stream()
            .map(respuestaOfertaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one respuestaOferta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RespuestaOfertaDTO findOne(Long id) {
        log.debug("Request to get RespuestaOferta : {}", id);
        RespuestaOferta respuestaOferta = respuestaOfertaRepository.findOneWithEagerRelationships(id);
        return respuestaOfertaMapper.toDto(respuestaOferta);
    }

    /**
     * Delete the respuestaOferta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RespuestaOferta : {}", id);
        respuestaOfertaRepository.delete(id);
    }
}

package truequep.service.impl;

import truequep.service.OfertaService;
import truequep.domain.Oferta;
import truequep.repository.OfertaRepository;
import truequep.service.dto.OfertaDTO;
import truequep.service.mapper.OfertaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Oferta.
 */
@Service
@Transactional
public class OfertaServiceImpl implements OfertaService{

    private final Logger log = LoggerFactory.getLogger(OfertaServiceImpl.class);

    private final OfertaRepository ofertaRepository;

    private final OfertaMapper ofertaMapper;

    public OfertaServiceImpl(OfertaRepository ofertaRepository, OfertaMapper ofertaMapper) {
        this.ofertaRepository = ofertaRepository;
        this.ofertaMapper = ofertaMapper;
    }

    /**
     * Save a oferta.
     *
     * @param ofertaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OfertaDTO save(OfertaDTO ofertaDTO) {
        log.debug("Request to save Oferta : {}", ofertaDTO);
        Oferta oferta = ofertaMapper.toEntity(ofertaDTO);
        oferta = ofertaRepository.save(oferta);
        return ofertaMapper.toDto(oferta);
    }

    /**
     * Get all the ofertas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OfertaDTO> findAll() {
        log.debug("Request to get all Ofertas");
        return ofertaRepository.findAll().stream()
            .map(ofertaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one oferta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OfertaDTO findOne(Long id) {
        log.debug("Request to get Oferta : {}", id);
        Oferta oferta = ofertaRepository.findOne(id);
        return ofertaMapper.toDto(oferta);
    }

    /**
     * Delete the oferta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Oferta : {}", id);
        ofertaRepository.delete(id);
    }
}

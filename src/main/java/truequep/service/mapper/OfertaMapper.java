package truequep.service.mapper;

import truequep.domain.*;
import truequep.service.dto.OfertaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Oferta and its DTO OfertaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface OfertaMapper extends EntityMapper<OfertaDTO, Oferta> {

    @Mapping(source = "cliente.id", target = "clienteId")
    OfertaDTO toDto(Oferta oferta); 

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(target = "estadoOfertas", ignore = true)
    Oferta toEntity(OfertaDTO ofertaDTO);

    default Oferta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Oferta oferta = new Oferta();
        oferta.setId(id);
        return oferta;
    }
}

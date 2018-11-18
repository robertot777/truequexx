package truequep.service.mapper;

import truequep.domain.*;
import truequep.service.dto.RespuestaOfertaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RespuestaOferta and its DTO RespuestaOfertaDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, OfertaMapper.class})
public interface RespuestaOfertaMapper extends EntityMapper<RespuestaOfertaDTO, RespuestaOferta> {

    @Mapping(source = "generaRespusta.id", target = "generaRespustaId")
    RespuestaOfertaDTO toDto(RespuestaOferta respuestaOferta); 

    @Mapping(source = "generaRespustaId", target = "generaRespusta")
    RespuestaOferta toEntity(RespuestaOfertaDTO respuestaOfertaDTO);

    default RespuestaOferta fromId(Long id) {
        if (id == null) {
            return null;
        }
        RespuestaOferta respuestaOferta = new RespuestaOferta();
        respuestaOferta.setId(id);
        return respuestaOferta;
    }
}

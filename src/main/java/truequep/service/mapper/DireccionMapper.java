package truequep.service.mapper;

import truequep.domain.*;
import truequep.service.dto.DireccionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Direccion and its DTO DireccionDTO.
 */
@Mapper(componentModel = "spring", uses = {ComunaMapper.class, UserMapper.class})
public interface DireccionMapper extends EntityMapper<DireccionDTO, Direccion> {

    @Mapping(source = "comuna.id", target = "comunaId")
    @Mapping(source = "tipo.id", target = "tipoId")
    DireccionDTO toDto(Direccion direccion); 

    @Mapping(source = "comunaId", target = "comuna")
    @Mapping(source = "tipoId", target = "tipo")
    Direccion toEntity(DireccionDTO direccionDTO);

    default Direccion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Direccion direccion = new Direccion();
        direccion.setId(id);
        return direccion;
    }
}

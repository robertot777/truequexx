package truequep.service.mapper;

import truequep.domain.*;
import truequep.service.dto.ValoracionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Valoracion and its DTO ValoracionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ValoracionMapper extends EntityMapper<ValoracionDTO, Valoracion> {

    @Mapping(source = "nombre.id", target = "nombreId")
    ValoracionDTO toDto(Valoracion valoracion); 

    @Mapping(source = "nombreId", target = "nombre")
    Valoracion toEntity(ValoracionDTO valoracionDTO);

    default Valoracion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Valoracion valoracion = new Valoracion();
        valoracion.setId(id);
        return valoracion;
    }
}

package truequep.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import truequep.domain.enumeration.OfertaRespuesta;

/**
 * A DTO for the RespuestaOferta entity.
 */
public class RespuestaOfertaDTO implements Serializable {

    private Long id;

    @NotNull
    private OfertaRespuesta aceptar;

    private Long generaRespustaId;

    private Set<OfertaDTO> ofertas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfertaRespuesta getAceptar() {
        return aceptar;
    }

    public void setAceptar(OfertaRespuesta aceptar) {
        this.aceptar = aceptar;
    }

    public Long getGeneraRespustaId() {
        return generaRespustaId;
    }

    public void setGeneraRespustaId(Long userId) {
        this.generaRespustaId = userId;
    }

    public Set<OfertaDTO> getOfertas() {
        return ofertas;
    }

    public void setOfertas(Set<OfertaDTO> ofertas) {
        this.ofertas = ofertas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RespuestaOfertaDTO respuestaOfertaDTO = (RespuestaOfertaDTO) o;
        if(respuestaOfertaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), respuestaOfertaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RespuestaOfertaDTO{" +
            "id=" + getId() +
            ", aceptar='" + getAceptar() + "'" +
            "}";
    }
}

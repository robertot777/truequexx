package truequep.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import truequep.domain.enumeration.TipoValoracion;

/**
 * A DTO for the Valoracion entity.
 */
public class ValoracionDTO implements Serializable {

    private Long id;

    private TipoValoracion tipoValoracion;

    private Long nombreId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoValoracion getTipoValoracion() {
        return tipoValoracion;
    }

    public void setTipoValoracion(TipoValoracion tipoValoracion) {
        this.tipoValoracion = tipoValoracion;
    }

    public Long getNombreId() {
        return nombreId;
    }

    public void setNombreId(Long userId) {
        this.nombreId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValoracionDTO valoracionDTO = (ValoracionDTO) o;
        if(valoracionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valoracionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValoracionDTO{" +
            "id=" + getId() +
            ", tipoValoracion='" + getTipoValoracion() + "'" +
            "}";
    }
}

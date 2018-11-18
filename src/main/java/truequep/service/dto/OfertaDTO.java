package truequep.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import truequep.domain.enumeration.TipoObjeto;

/**
 * A DTO for the Oferta entity.
 */
public class OfertaDTO implements Serializable {

    private Long id;

    @NotNull
    private String generarOferta;

    private TipoObjeto tipoObjeto;

    @NotNull
    @Lob
    private byte[] generaOferta;
    private String generaOfertaContentType;

    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenerarOferta() {
        return generarOferta;
    }

    public void setGenerarOferta(String generarOferta) {
        this.generarOferta = generarOferta;
    }

    public TipoObjeto getTipoObjeto() {
        return tipoObjeto;
    }

    public void setTipoObjeto(TipoObjeto tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }

    public byte[] getGeneraOferta() {
        return generaOferta;
    }

    public void setGeneraOferta(byte[] generaOferta) {
        this.generaOferta = generaOferta;
    }

    public String getGeneraOfertaContentType() {
        return generaOfertaContentType;
    }

    public void setGeneraOfertaContentType(String generaOfertaContentType) {
        this.generaOfertaContentType = generaOfertaContentType;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long userId) {
        this.clienteId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfertaDTO ofertaDTO = (OfertaDTO) o;
        if(ofertaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ofertaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfertaDTO{" +
            "id=" + getId() +
            ", generarOferta='" + getGenerarOferta() + "'" +
            ", tipoObjeto='" + getTipoObjeto() + "'" +
            ", generaOferta='" + getGeneraOferta() + "'" +
            "}";
    }
}

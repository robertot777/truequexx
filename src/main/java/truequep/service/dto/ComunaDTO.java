package truequep.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Comuna entity.
 */
public class ComunaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreComuna;

    private Long regionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreComuna) {
        this.nombreComuna = nombreComuna;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComunaDTO comunaDTO = (ComunaDTO) o;
        if(comunaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comunaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComunaDTO{" +
            "id=" + getId() +
            ", nombreComuna='" + getNombreComuna() + "'" +
            "}";
    }
}

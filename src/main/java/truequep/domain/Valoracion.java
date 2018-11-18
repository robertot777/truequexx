package truequep.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import truequep.domain.enumeration.TipoValoracion;


/**
 * A Valoracion.
 */
@Entity
@Table(name = "valoracion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Valoracion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_valoracion")
    private TipoValoracion tipoValoracion;

    @ManyToOne
    private User nombre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoValoracion getTipoValoracion() {
        return tipoValoracion;
    }

    public Valoracion tipoValoracion(TipoValoracion tipoValoracion) {
        this.tipoValoracion = tipoValoracion;
        return this;
    }

    public void setTipoValoracion(TipoValoracion tipoValoracion) {
        this.tipoValoracion = tipoValoracion;
    }

    public User getNombre() {
        return nombre;
    }

    public Valoracion nombre(User user) {
        this.nombre = user;
        return this;
    }

    public void setNombre(User user) {
        this.nombre = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Valoracion valoracion = (Valoracion) o;
        if (valoracion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valoracion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Valoracion{" +
            "id=" + getId() +
            ", tipoValoracion='" + getTipoValoracion() + "'" +
            "}";
    }
}

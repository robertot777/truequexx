package truequep.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import truequep.domain.enumeration.OfertaRespuesta;


/**
 * A RespuestaOferta.
 */
@Entity
@Table(name = "respuesta_oferta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RespuestaOferta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "aceptar", nullable = false)
    private OfertaRespuesta aceptar;

    @ManyToOne
    private User generaRespusta;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "respuesta_oferta_oferta",
               joinColumns = @JoinColumn(name="respuesta_ofertas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="ofertas_id", referencedColumnName="id"))
    private Set<Oferta> ofertas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfertaRespuesta getAceptar() {
        return aceptar;
    }

    public RespuestaOferta aceptar(OfertaRespuesta aceptar) {
        this.aceptar = aceptar;
        return this;
    }

    public void setAceptar(OfertaRespuesta aceptar) {
        this.aceptar = aceptar;
    }

    public User getGeneraRespusta() {
        return generaRespusta;
    }

    public RespuestaOferta generaRespusta(User user) {
        this.generaRespusta = user;
        return this;
    }

    public void setGeneraRespusta(User user) {
        this.generaRespusta = user;
    }

    public Set<Oferta> getOfertas() {
        return ofertas;
    }

    public RespuestaOferta ofertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
        return this;
    }

    public RespuestaOferta addOferta(Oferta oferta) {
        this.ofertas.add(oferta);
        oferta.getEstadoOfertas().add(this);
        return this;
    }

    public RespuestaOferta removeOferta(Oferta oferta) {
        this.ofertas.remove(oferta);
        oferta.getEstadoOfertas().remove(this);
        return this;
    }

    public void setOfertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
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
        RespuestaOferta respuestaOferta = (RespuestaOferta) o;
        if (respuestaOferta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), respuestaOferta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RespuestaOferta{" +
            "id=" + getId() +
            ", aceptar='" + getAceptar() + "'" +
            "}";
    }
}

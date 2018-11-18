package truequep.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import truequep.domain.enumeration.TipoObjeto;


/**
 * A Oferta.
 */
@Entity
@Table(name = "oferta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Oferta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "generar_oferta", nullable = false)
    private String generarOferta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_objeto")
    private TipoObjeto tipoObjeto;

    @NotNull
    @Lob
    @Column(name = "genera_oferta", nullable = false)
    private byte[] generaOferta;

    @Column(name = "genera_oferta_content_type", nullable = false)
    private String generaOfertaContentType;

    @ManyToOne
    private User cliente;

    @ManyToMany(mappedBy = "ofertas")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RespuestaOferta> estadoOfertas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenerarOferta() {
        return generarOferta;
    }

    public Oferta generarOferta(String generarOferta) {
        this.generarOferta = generarOferta;
        return this;
    }

    public void setGenerarOferta(String generarOferta) {
        this.generarOferta = generarOferta;
    }

    public TipoObjeto getTipoObjeto() {
        return tipoObjeto;
    }

    public Oferta tipoObjeto(TipoObjeto tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
        return this;
    }

    public void setTipoObjeto(TipoObjeto tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }

    public byte[] getGeneraOferta() {
        return generaOferta;
    }

    public Oferta generaOferta(byte[] generaOferta) {
        this.generaOferta = generaOferta;
        return this;
    }

    public void setGeneraOferta(byte[] generaOferta) {
        this.generaOferta = generaOferta;
    }

    public String getGeneraOfertaContentType() {
        return generaOfertaContentType;
    }

    public Oferta generaOfertaContentType(String generaOfertaContentType) {
        this.generaOfertaContentType = generaOfertaContentType;
        return this;
    }

    public void setGeneraOfertaContentType(String generaOfertaContentType) {
        this.generaOfertaContentType = generaOfertaContentType;
    }

    public User getCliente() {
        return cliente;
    }

    public Oferta cliente(User user) {
        this.cliente = user;
        return this;
    }

    public void setCliente(User user) {
        this.cliente = user;
    }

    public Set<RespuestaOferta> getEstadoOfertas() {
        return estadoOfertas;
    }

    public Oferta estadoOfertas(Set<RespuestaOferta> respuestaOfertas) {
        this.estadoOfertas = respuestaOfertas;
        return this;
    }

    public Oferta addEstadoOferta(RespuestaOferta respuestaOferta) {
        this.estadoOfertas.add(respuestaOferta);
        respuestaOferta.getOfertas().add(this);
        return this;
    }

    public Oferta removeEstadoOferta(RespuestaOferta respuestaOferta) {
        this.estadoOfertas.remove(respuestaOferta);
        respuestaOferta.getOfertas().remove(this);
        return this;
    }

    public void setEstadoOfertas(Set<RespuestaOferta> respuestaOfertas) {
        this.estadoOfertas = respuestaOfertas;
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
        Oferta oferta = (Oferta) o;
        if (oferta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), oferta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Oferta{" +
            "id=" + getId() +
            ", generarOferta='" + getGenerarOferta() + "'" +
            ", tipoObjeto='" + getTipoObjeto() + "'" +
            ", generaOferta='" + getGeneraOferta() + "'" +
            ", generaOfertaContentType='" + getGeneraOfertaContentType() + "'" +
            "}";
    }
}

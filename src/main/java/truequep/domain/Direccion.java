package truequep.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * Entidad de direccion de usuario
 */
@ApiModel(description = "Entidad de direccion de usuario")
@Entity
@Table(name = "direccion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "adicional")
    private String adicional;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @OneToOne
    @JoinColumn(unique = true)
    private Comuna comuna;

    @ManyToOne
    private User tipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public Direccion direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero() {
        return numero;
    }

    public Direccion numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAdicional() {
        return adicional;
    }

    public Direccion adicional(String adicional) {
        this.adicional = adicional;
        return this;
    }

    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public Direccion codigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public Direccion comuna(Comuna comuna) {
        this.comuna = comuna;
        return this;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public User getTipo() {
        return tipo;
    }

    public Direccion tipo(User user) {
        this.tipo = user;
        return this;
    }

    public void setTipo(User user) {
        this.tipo = user;
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
        Direccion direccion = (Direccion) o;
        if (direccion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), direccion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Direccion{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", numero='" + getNumero() + "'" +
            ", adicional='" + getAdicional() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            "}";
    }
}

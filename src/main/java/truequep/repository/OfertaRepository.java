package truequep.repository;

import truequep.domain.Oferta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Oferta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    @Query("select oferta from Oferta oferta where oferta.cliente.login = ?#{principal.username}")
    List<Oferta> findByClienteIsCurrentUser();

}

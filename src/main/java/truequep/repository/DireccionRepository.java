package truequep.repository;

import truequep.domain.Direccion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Direccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    @Query("select direccion from Direccion direccion where direccion.tipo.login = ?#{principal.username}")
    List<Direccion> findByTipoIsCurrentUser();

}

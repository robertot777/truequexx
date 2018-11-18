package truequep.repository;

import truequep.domain.Valoracion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Valoracion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    @Query("select valoracion from Valoracion valoracion where valoracion.nombre.login = ?#{principal.username}")
    List<Valoracion> findByNombreIsCurrentUser();

}

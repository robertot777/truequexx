package truequep.repository;

import truequep.domain.RespuestaOferta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the RespuestaOferta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RespuestaOfertaRepository extends JpaRepository<RespuestaOferta, Long> {

    @Query("select respuesta_oferta from RespuestaOferta respuesta_oferta where respuesta_oferta.generaRespusta.login = ?#{principal.username}")
    List<RespuestaOferta> findByGeneraRespustaIsCurrentUser();
    @Query("select distinct respuesta_oferta from RespuestaOferta respuesta_oferta left join fetch respuesta_oferta.ofertas")
    List<RespuestaOferta> findAllWithEagerRelationships();

    @Query("select respuesta_oferta from RespuestaOferta respuesta_oferta left join fetch respuesta_oferta.ofertas where respuesta_oferta.id =:id")
    RespuestaOferta findOneWithEagerRelationships(@Param("id") Long id);

}

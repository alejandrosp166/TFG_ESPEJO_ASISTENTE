package es.tfg.asp.repositorio;

import es.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.tfg.asp.modelo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCredenciales
        extends JpaRepository<CredencialesUsuario, Integer> {
    @Query("SELECT c FROM CredencialesUsuario c WHERE c.idUsuario = :idUsuario")
    CredencialesUsuario buscarCredencialesPorIdUsuario(@Param("idUsuario") Usuario idUsuario);
}

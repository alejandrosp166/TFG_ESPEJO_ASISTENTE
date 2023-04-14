package es.tfg.asp.repositorio;

import es.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.tfg.asp.servicio.iservice.ServiceUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCredenciales
        extends JpaRepository<CredencialesUsuario, Integer> {
    @Query("SELECT c FROM Credenciales c WHERE e.id_usuario = :idUsuario")
    CredencialesUsuario buscarCredencialesPorIdUsuario(@Param("idUsuario") int idUsuario);
}

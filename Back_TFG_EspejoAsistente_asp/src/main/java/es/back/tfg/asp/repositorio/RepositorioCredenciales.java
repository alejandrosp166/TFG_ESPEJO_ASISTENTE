package es.back.tfg.asp.repositorio;

import es.back.tfg.asp.modelo.entidades.CredencialesUsuario;
import es.back.tfg.asp.modelo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositorioCredenciales
        extends JpaRepository<CredencialesUsuario, UUID> {
    @Query("SELECT c FROM CredencialesUsuario c WHERE c.usuario=:usuario")
    public CredencialesUsuario findCredencialesByIdUsuario(@Param("usuario") Usuario usuario);
}

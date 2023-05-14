package es.back.tfg.asp.repositorio;

import es.back.tfg.asp.modelo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositorioUsuario
        extends JpaRepository<Usuario, UUID> {
    @Query("SELECT u FROM Usuario u WHERE u.username=:username")
    Usuario findUsuarioByUsername(@Param("username") String username);
}

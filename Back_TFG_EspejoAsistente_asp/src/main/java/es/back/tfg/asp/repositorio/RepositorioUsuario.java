package es.back.tfg.asp.repositorio;

import es.back.tfg.asp.modelo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario
        extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.username=:username")
    Usuario findUsuarioByUsername(@Param("username") String username);
    @Query("SELECT u FROM Usuario u WHERE u.uuid=:uuid")
    Usuario findUsuarioByUuid(@Param("uuid") String uuid);
    @Query("DELETE FROM Usuario u WHERE u.uuid=:uuid")
    void deleteUsuarioByUuid(@Param("uuid") String uuid);
}

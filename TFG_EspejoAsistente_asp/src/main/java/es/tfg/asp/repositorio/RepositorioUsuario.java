package es.tfg.asp.repositorio;

import es.tfg.asp.modelo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario
        extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    Usuario buscarPorUsername(@Param("username") String username);
}

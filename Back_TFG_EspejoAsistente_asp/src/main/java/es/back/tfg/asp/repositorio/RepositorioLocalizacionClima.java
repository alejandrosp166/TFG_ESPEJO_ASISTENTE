package es.back.tfg.asp.repositorio;

import es.back.tfg.asp.modelo.entidades.LocalizacionClima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RepositorioLocalizacionClima
        extends JpaRepository<LocalizacionClima, UUID> {
}

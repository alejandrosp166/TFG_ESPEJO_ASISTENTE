package es.back.tfg.asp.repositorio;

import es.back.tfg.asp.modelo.entidades.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositorioEquipo extends JpaRepository<Equipo, UUID> {
}

package es.tfg.asp.modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CredencialesUsuario {
    @Id
    private int idCredenciales;
}

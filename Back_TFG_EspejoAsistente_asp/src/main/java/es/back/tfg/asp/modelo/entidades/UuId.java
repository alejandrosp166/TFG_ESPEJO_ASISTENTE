package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@MappedSuperclass
@Data
public abstract class UuId {
    @Id
    @GeneratedValue
    private UUID uuid;
}

package es.back.tfg.asp.modelo.entidades;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.UUID;
@MappedSuperclass
@Data
public abstract class UuId {
    @Id
    @GeneratedValue
    protected UUID uuid;
}

package co.edu.uniandes.dse.parcialprueba.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//@Data
@Entity
public class MedicoEntity extends BaseEntity{

    @Id
    private long id;

    private String nombre;
    private String apellido;
    private String registroMedico;
}

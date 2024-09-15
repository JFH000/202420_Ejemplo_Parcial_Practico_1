package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

//@Data
@Entity
public class MedicoEntity extends BaseEntity{

    @Id
    private long id;

    private String nombre;
    private String apellido;
    private String registroMedico;

    @PodamExclude
    @ManyToMany(mappedBy = "medicos")
    private List<EspecialidadEntity> especialidades = new ArrayList<>();
}

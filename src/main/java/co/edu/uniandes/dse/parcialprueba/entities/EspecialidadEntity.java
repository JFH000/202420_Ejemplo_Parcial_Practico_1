package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

//@Data
@Entity
public class EspecialidadEntity extends BaseEntity{

    private String nombre;
    private String descripcion;

    @Id
    private long id;

    @PodamExclude
    @ManyToMany
    private List<MedicoEntity> medicos = new ArrayList<>();
}

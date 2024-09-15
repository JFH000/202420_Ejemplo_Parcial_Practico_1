package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
@Data
@Entity
public class EspecialidadEntity extends BaseEntity{

    private String nombre;
    private String descripcion;

    @PodamExclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PodamExclude
    @ManyToMany
    private List<MedicoEntity> medicos = new ArrayList<>();
}

package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({EspecialidadService.class,MedicoEspecialidadService.class})
class MedicoEspecialidadServiceTest {

    @Autowired
	private MedicoEspecialidadService medicoEspecialidadService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<EspecialidadEntity> especialidadList = new ArrayList<>();
    private List<MedicoEntity> medicoList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
	}

	private void insertData() {
		for (int i = 0; i < 3; i++) {
			EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
			entityManager.persist(especialidadEntity);
			especialidadList.add(especialidadEntity);
		}

        for (int i = 0; i < 3; i++) {
			MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
			//medicoEntity.setEspecialidades(especialidadList.subList(0, 1));
            entityManager.persist(medicoEntity);
			medicoList.add(medicoEntity);
		}
	}

    @Test
    void addEspecilidadTest() throws IllegalOperationException, EntityNotFoundException{

        MedicoEntity medico = medicoList.get(0);
		EspecialidadEntity especialidad = especialidadList.get(0);

		MedicoEntity newEntity = medicoEspecialidadService.addEspecialidad(medico.getId(), especialidad.getId());
        assertNotNull(newEntity);

		medico.getEspecialidades().add(especialidad);

		for (int i = 0; i<Math.max(medico.getEspecialidades().size(), newEntity.getEspecialidades().size()) ; i++){
			assertEquals(medico.getEspecialidades().get(i).getId(), newEntity.getEspecialidades().get(i).getId());
			assertEquals(medico.getEspecialidades().get(i).getDescripcion(), newEntity.getEspecialidades().get(i).getDescripcion());
			assertEquals(medico.getEspecialidades().get(i).getNombre(), newEntity.getEspecialidades().get(i).getNombre());
		}
        
    }

	@Test
	void addEspecialiidadIncorrectMedico(){
		assertThrows(EntityNotFoundException.class, ()->{

			EspecialidadEntity especilidad = especialidadList.get(1);
			medicoEspecialidadService.addEspecialidad(0L, especilidad.getId());
		});
	}

	@Test
	void addEspecialiidadIncorrectEspecilidad(){
		assertThrows(EntityNotFoundException.class, ()->{

			MedicoEntity medico = medicoList.get(0);
			medicoEspecialidadService.addEspecialidad(medico.getId(), 0L);
		});
	}
}

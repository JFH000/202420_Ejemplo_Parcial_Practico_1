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
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(EspecialidadService.class)
public class EspecialidadServiceTest {

    @Autowired
	private EspecialidadService especialidadService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<EspecialidadEntity> especialidadList = new ArrayList<>();

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
	}

    @Test
    void createEspecialidadCorrectaTest() throws IllegalOperationException{

        EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);

        newEntity.setDescripcion("0123456789");

        EspecialidadEntity result = especialidadService.createEspecialidad(newEntity);
        assertNotNull(result);

        EspecialidadEntity entity = especialidadService.createEspecialidad(result);

        assertEquals(result.getDescripcion(), entity.getDescripcion());
        assertEquals(result.getId(), entity.getId());
        assertEquals(result.getNombre(), entity.getNombre());
    }

    @Test
    void createEspecialidadIncorrectaTest(){
        assertThrows(IllegalOperationException.class, ()->{
            EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
            newEntity.setDescripcion("1");
            EspecialidadEntity entity = especialidadService.createEspecialidad(newEntity);
        });
    }
}

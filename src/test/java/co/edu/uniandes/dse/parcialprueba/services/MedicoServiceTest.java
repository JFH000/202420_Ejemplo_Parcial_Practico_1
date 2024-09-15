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

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoService.class)
class MedicoServiceTest {

    @Autowired
	private MedicoService medicoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

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
		entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
	}

	private void insertData() {
		for (int i = 0; i < 3; i++) {
			MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
            //medicoEntity.setRegistroMedico("RM"+Integer.toString(i));
			//medicoEntity.setId(Integer.toUnsignedLong(i));
			entityManager.persist(medicoEntity);
			medicoList.add(medicoEntity);
		}
	}

    @Test
    void createMedicoCorrectoTest() throws IllegalOperationException{
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);

        newEntity.setRegistroMedico("RM29304");

        MedicoEntity result = medicoService.createMedico(newEntity);
        assertNotNull(result);

        MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());

        assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getApellido(), entity.getApellido());
		assertEquals(newEntity.getRegistroMedico(), entity.getRegistroMedico());
		assertEquals(newEntity.getNombre(), entity.getNombre());

    }

	@Test
	void createMEdicoIncorrectoTest(){
		assertThrows(IllegalOperationException.class, ()->{
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
			medicoService.createMedico(newEntity);
		});
	}
}

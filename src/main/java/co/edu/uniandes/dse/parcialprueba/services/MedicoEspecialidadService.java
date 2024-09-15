package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public MedicoEntity addEspecialidad(Long idMedico, long idEspecialidad) throws IllegalOperationException, EntityNotFoundException{

        //Agrega la especialidad al medico
        Optional<MedicoEntity> medico = medicoRepository.findById(idMedico);
        if(medico.isEmpty())
            throw new EntityNotFoundException("No se encontro el medico");
        
        Optional<EspecialidadEntity> especialidad = especialidadRepository.findById(idEspecialidad);
        if(especialidad.isEmpty())
            throw new EntityNotFoundException("No se encontro la especialidad");

        medico.get().getEspecialidades().add(especialidad.get());

        return medico.get();
    }
}

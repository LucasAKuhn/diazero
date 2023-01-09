package com.diazero.service;

import com.diazero.entities.Incident;
import com.diazero.exception.RegraNegocioException;
import com.diazero.repositories.IncidentRepository;
import com.diazero.repository.IncidentRepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class IncidentServiceTest {

    @SpyBean
    IncidentService incidentService;

    @MockBean
    IncidentRepository incidentRepository;

    @Test
    public void mustSaveAnIncident() {

        Incident incidentToSave = IncidentRepositoryTest.createIncident();
        Mockito.doNothing().when(incidentService).validate(incidentToSave);

        Incident incidentSave = IncidentRepositoryTest.createIncident();
        incidentSave.setId(1l);
        Mockito.when(incidentRepository.save(incidentSave)).thenReturn(incidentSave);

        Incident incident = incidentService.save(incidentSave);

        Assertions.assertThat(incident.getId()).isEqualTo(incidentSave.getId());
    }

    @Test
    public void shouldNotSaveAnAddressWhenThereIsAValidationError() {

        Incident incidentToSave = IncidentRepositoryTest.createIncident();
        Mockito.doThrow( RegraNegocioException.class ).when(incidentService).validate(incidentToSave);

        Assertions.catchThrowableOfType( () -> incidentService.save(incidentToSave), RegraNegocioException.class);
        Mockito.verify(incidentRepository, Mockito.never()).save(incidentToSave);
    }

    @Test
    public void mustDeleteAnIncident() {

        Incident incident = IncidentRepositoryTest.createIncident();
        incident.setId(1l);

        incidentService.delete(incident);

        Mockito.verify(incidentRepository).delete(incident);
    }

    @Test
    public void mustFindAnIncidentById() {
        Long id = 1l;

        Incident incident = IncidentRepositoryTest.createIncident();
        incident.setId(id);

        Mockito.when( incidentRepository.findById(id)).thenReturn( Optional.of(incident));

        Optional<Incident> result = Optional.ofNullable(incidentService.findById(id));

        Assertions.assertThat(result.isPresent()).isTrue();
    }

}

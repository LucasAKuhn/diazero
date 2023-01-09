package com.diazero.repository;

import com.diazero.entities.Incident;
import com.diazero.repositories.IncidentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class IncidentRepositoryTest {

    @Autowired
    IncidentRepository incidentRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void mustSaveAnIncident() {
        Incident incident = createIncident();

        incident = incidentRepository.save(incident);

        Assertions.assertThat(incident.getId()).isNotNull();
    }

    @Test
    public void mustDeleteAnIncident() {
        Incident incident = creatAndPersistAnIncident();

        incident = entityManager.find(Incident.class, incident.getId());

        incidentRepository.delete(incident);

        Incident incidentDeleted = entityManager.find(Incident.class, incident.getId());
        Assertions.assertThat(incidentDeleted).isNull();
    }

    @Test
    public void mustUpdateAnIncident() {
        Incident incident = creatAndPersistAnIncident();

        incident.setName("teste de Update");
        incident.setDescription("Alterando descrição para teste");
        incident.setCreatedAt(LocalDateTime.parse("2023-01-09T15:17:04.9614312"));
        incident.setUpdatedAt(LocalDateTime.parse("2023-01-09T15:17:23.107895"));
        incident.setClosedAt(LocalDateTime.parse("2023-01-09T15:17:51.2961091"));

        incidentRepository.save(incident);

        Incident incidentUpdated = entityManager.find(Incident.class, incident.getId());

        Assertions.assertThat(incidentUpdated.getName()).isEqualTo("teste de Update");
        Assertions.assertThat(incidentUpdated.getDescription()).isEqualTo("Alterando descrição para teste");
        Assertions.assertThat(incidentUpdated.getCreatedAt()).isEqualTo("2023-01-09T15:17:04.9614312");
        Assertions.assertThat(incidentUpdated.getUpdatedAt()).isEqualTo("2023-01-09T15:17:23.107895");
        Assertions.assertThat(incidentUpdated.getClosedAt()).isEqualTo("2023-01-09T15:17:51.2961091");
    }

    @Test
    public void mustFindAnIncidentById() {
        Incident incident = creatAndPersistAnIncident();

        Optional<Incident> incidentFound = incidentRepository.findById(incident.getId());

        Assertions.assertThat(incidentFound.isPresent()).isTrue();
    }

    private Incident creatAndPersistAnIncident() {
        Incident incident = createIncident();
        entityManager.persist(incident);
        return incident;
    }

    public static Incident createIncident() {
        return Incident.builder()
                .name("testes unitários")
                .description("Criando incidente para testes unitários")
                .createdAt(LocalDateTime.parse("2023-01-09T14:49:53.56131"))
                .updatedAt(LocalDateTime.parse("2023-01-09T14:49:54.741529"))
                .closedAt(LocalDateTime.parse("2023-01-09T14:51:37.958637"))
                .build();
    }

}

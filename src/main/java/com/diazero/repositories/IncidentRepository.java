package com.diazero.repositories;

import com.diazero.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    @Query(nativeQuery = true, value =
        "SELECT * FROM " +
                "Incident " +
                "WHERE ID_INCIDENT " +
                "BETWEEN :IdStart " +
                "AND :IdEnd ")
    List<Incident> findIncidentBetween1and20(int IdStart, int IdEnd);

}

package com.diazero.service;

import com.diazero.entities.Incident;
import com.diazero.exception.RegraNegocioException;
import com.diazero.repositories.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;


    public Incident save(Incident incident) {
        validate(incident);
        return incidentRepository.save(incident);
    }

    public List findAll() {
        List<Incident> result = incidentRepository.findAll();
        return result;
    }

    public Incident findById(Long id) {
        Incident result = incidentRepository.findById(id).get();
        return result;
    }

    public Incident update(Incident incident) {
        validate(incident);
        return incidentRepository.save(incident);
    }

    public void delete(Incident incident) {
        incidentRepository.delete(incident);
    }


    public void validate(Incident incident) {

        if(incident.getName() == null || incident.getName().trim().equals("") ) {
            throw new RegraNegocioException("Inform a name:");
        }

        if(incident.getDescription() == null || incident.getDescription().trim().equals("") ) {
            throw new RegraNegocioException("Inform a description:");
        }
    }

}

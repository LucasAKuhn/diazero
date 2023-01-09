package com.diazero.controllers;

import com.diazero.dto.IncidentDTO;
import com.diazero.entities.Incident;
import com.diazero.exception.RegraNegocioException;
import com.diazero.repositories.IncidentRepository;
import com.diazero.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/incident")
public class IncidentController {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private IncidentService incidentService;


    @PostMapping
    public ResponseEntity save(@RequestBody IncidentDTO dto) {
        try{
            Incident incident = converter(dto);
            incident = incidentService.save((incident));
            return ResponseEntity.ok(incident);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/find-all-incidents")
    public ResponseEntity findAll() {

        return ResponseEntity.ok(incidentService.findAll());
    }

    @GetMapping(value = "/find-by-id/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<Incident> incidentOptional = incidentRepository.findById(id);
        if (!incidentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found incident");
        }

            return ResponseEntity.ok(incidentService.findById(id));
    }

    @GetMapping(value = "/find-between-one-and-twenty")
    public ResponseEntity findBetweenOneAndTwenty() {

        List<Incident> result = incidentRepository.findIncidentBetween1and20(1, 20);
        Collections.reverse(result);

       return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id,
                                            @RequestBody Incident incident) {
        Optional<Incident> incidentOptional = incidentRepository.findById(id);
        if(!incidentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found incident");
        }

        try{
            var incident1 = incidentOptional.get();

            incident1.setName(incident.getName());
            incident1.setDescription(incident.getDescription());
            incident1.setClosedAt(incident.getClosedAt());

            return ResponseEntity.status(HttpStatus.OK).body(incidentService.update(incident1));
        } catch(RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<Incident> incidentOptional = incidentRepository.findById(id);
        if(!incidentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incident not found");
        }
        incidentService.delete(incidentOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Succesfully deleted incident");
    }

    private Incident converter(IncidentDTO dto) {
        Incident incident = new Incident();

        incident.setName(dto.getName());
        incident.setDescription(dto.getDescription());
        incident.setClosedAt(dto.getClosedAt());

        return incident;
    }

}

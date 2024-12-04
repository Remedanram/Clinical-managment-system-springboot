package com.project.Clinical_System.controller;

import com.project.Clinical_System.entity.Patient;
import com.project.Clinical_System.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")

public class PatientController {
    @Autowired
    private PatientService service;
   @PostMapping()
    public String addPatient(@RequestBody Patient patient){

       service.addPatient(patient);

   return "Patient Registered Successfully";}

    @GetMapping()
    public List<Patient> getPatient(){
       return service.getPatient();
       }
    @GetMapping("/{id}")
    public Optional<Patient> getPatient(@PathVariable Long id){
        return service.getPatient(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@RequestBody Patient patient, @PathVariable Long id) {
        Optional<Patient> updatedPatient = service.updatePatient(patient, id);
        if (updatedPatient.isPresent()) {
            return ResponseEntity.ok(updatedPatient.get());  // Returns 200 OK with updated patient
        } else {
            return ResponseEntity.notFound().build();  // Returns 404 if patient is not found
        }
    }
    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable Long id){
       service.deletePatient(id);}

}

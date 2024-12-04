package com.project.Clinical_System.service;

import com.project.Clinical_System.entity.Patient;
import com.project.Clinical_System.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  PatientService {
    @Autowired
    private PatientRepository repository;
    public void addPatient(Patient patient) {
        Patient patient1=new Patient();
        patient1.setFirstName(patient.getFirstName());
        patient1.setEmail(patient.getEmail());
        patient1.setPhone(patient.getPhone());
        patient1.setLastName(patient.getLastName());
//        patient1.setDateOfBirth(patient.getDateOfBirth());
       repository.save(patient1);
    }

    public List<Patient> getPatient() {
        return repository.findAll();

    }

    public Optional<Patient> getPatient(Long id) {
        return repository.findById(id);
    }

    public Optional<Patient> updatePatient(Patient patient, Long id) {
        Optional<Patient> existingPatient = repository.findById(id);

        if (existingPatient.isPresent()) {
            Patient patientToUpdate = existingPatient.get();

            // Update the patient's details
            patientToUpdate.setFirstName(patient.getFirstName());
            patientToUpdate.setLastName(patient.getLastName());
            patientToUpdate.setEmail(patient.getEmail());
            patientToUpdate.setPhone(patient.getPhone());
            // patientToUpdate.setDateOfBirth(patient.getDateOfBirth());  // Uncomment if needed

            // Save the updated patient to the repository
            repository.save(patientToUpdate);
        } else {
            throw new RuntimeException("Patient not found with id " + id);  // Handle patient not found
        }
        return existingPatient;
    }

    public void deletePatient(Long id) {
        repository.deleteById(id);
    }
}

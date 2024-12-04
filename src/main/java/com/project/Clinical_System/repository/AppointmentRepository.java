package com.project.Clinical_System.repository;

import com.project.Clinical_System.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    // Custom query to find appointments by patient ID
    List<Appointment> findByPatientId(Long patientId);

    // Custom query to find appointments by doctor ID
    List<Appointment> findByDoctorId(Long doctorId);

    // Custom query to find appointments by status
    List<Appointment> findByStatus(String status);

    // Custom query to find appointments by appointment date
    List<Appointment> findByAppointmentDate(LocalDateTime appointmentDate);

}

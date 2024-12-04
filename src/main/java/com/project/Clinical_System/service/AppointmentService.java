package com.project.Clinical_System.service;

import com.project.Clinical_System.dto.AppointmentRequest;
import com.project.Clinical_System.entity.AppointmentStatus;
import com.project.Clinical_System.entity.Appointment;
import com.project.Clinical_System.entity.Doctor;
import com.project.Clinical_System.entity.Patient;
import com.project.Clinical_System.repository.AppointmentRepository;
import com.project.Clinical_System.repository.DoctorRepository;
import com.project.Clinical_System.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Create a new appointment
    public Appointment createAppointment(AppointmentRequest appointmentRequest) {
        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(()
 -> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDateTime.of(appointmentRequest.getAppointmentDate()
                ,appointmentRequest.getAppointmentTime()));
        appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());

        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return appointmentRepository.save(appointment);
    }
    // Get all appointments
    public List<Appointment> getAppointments(Long patientId, Long doctorId, String status) {
        if (patientId != null) {
            return appointmentRepository.findByPatientId(patientId);
        } else if (doctorId != null) {
            return appointmentRepository.findByDoctorId(doctorId);
        } else if (status != null) {
            return appointmentRepository.findByStatus(status);
        } else {
            return appointmentRepository.findAll();
        }
    }

    // Update an existing appointment
    public Appointment updateAppointment(Long id, AppointmentRequest appointmentRequest) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Update the appointment fields
        if (appointmentRequest.getAppointmentDate() != null) {
            appointment.setAppointmentDate(LocalDateTime.from(appointmentRequest.getAppointmentDate()));
        }
        if (appointmentRequest.getAppointmentTime() != null) {
            appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        }
        if (appointmentRequest.getStatus() != null) {
            appointment.setStatus(AppointmentStatus.valueOf(appointmentRequest.getStatus()));
        }

        return appointmentRepository.save(appointment);
    }

    // Cancel an appointment
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);
    }
}


package com.project.Clinical_System.controller;

import com.project.Clinical_System.dto.AppointmentRequest;
import com.project.Clinical_System.service.AppointmentService;
import com.project.Clinical_System.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Create a new appointment
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        Appointment appointment = appointmentService.createAppointment(appointmentRequest);
        return ResponseEntity.status(201).body(appointment);
    }

    // Get appointments with optional filters (patientId, doctorId, date, status)
    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String status) {

        List<Appointment> appointments = appointmentService.getAppointments(patientId, doctorId, status);
        return ResponseEntity.ok(appointments);
    }

    // Update an existing appointment
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id,
                                                         @RequestBody AppointmentRequest appointmentRequest) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentRequest);
        return ResponseEntity.ok(updatedAppointment);
    }

    // Cancel an appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
}


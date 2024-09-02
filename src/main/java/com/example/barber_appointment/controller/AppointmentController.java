package com.example.barber_appointment.controller;

import com.example.barber_appointment.business.abstracts.AppointmentService;
import com.example.barber_appointment.model.Appointment;
import com.example.barber_appointment.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // Randevu talebi gönderme
    @PostMapping("/request")
    public ResponseEntity<String> sendAppointmentRequest(@RequestBody Appointment appointment, @RequestParam User customer) {
        appointmentService.sendAppointmentRequest(appointment, customer);
        return ResponseEntity.ok("Randevu talebi başarıyla gönderildi.");
    }

    // Kullanıcının tüm randevularını alma
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointment>> getAppointments(@PathVariable Long userId) {
        User user = new User(); // Kullanıcıyı userId ile bulmanız gerekebilir
        user.setId(userId);
        List<Appointment> appointments = appointmentService.getAppointments(user);
        return ResponseEntity.ok(appointments);
    }

    // Kullanıcının aktif randevularını alma
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Appointment>> getActiveAppointments(@PathVariable Long userId) {
        User user = new User(); // Kullanıcıyı userId ile bulmanız gerekebilir
        user.setId(userId);
        List<Appointment> appointments = appointmentService.getActiveAppointments(user);
        return ResponseEntity.ok(appointments);
    }

    // Berberin günlük randevularını alma
    @GetMapping("/barber/{barberId}/daily")
    public ResponseEntity<List<Appointment>> getMyDailyAppointments(@PathVariable Long barberId) {
        User barber = new User(); // Berberi barberId ile bulmanız gerekebilir
        barber.setId(barberId);
        List<Appointment> appointments = appointmentService.getMyDailyAppointments(barber);
        return ResponseEntity.ok(appointments);
    }

    // Berberin tüm randevularını alma
    @GetMapping("/barber/{barberId}/all")
    public ResponseEntity<List<Appointment>> getMyAllAppointments(@PathVariable Long barberId) {
        User barber = new User(); // Berberi barberId ile bulmanız gerekebilir
        barber.setId(barberId);
        List<Appointment> appointments = appointmentService.getMyAllAppointments(barber);
        return ResponseEntity.ok(appointments);
    }

    // Randevuyu kabul etme
    @PostMapping("/accept")
    public ResponseEntity<String> acceptAppointmentRequest(@RequestBody Appointment appointment) {
        appointmentService.acceptAppointmentRequest(appointment);
        return ResponseEntity.ok("Randevu başarıyla onaylandı.");
    }

    // Randevuyu reddetme
    @PostMapping("/reject")
    public ResponseEntity<String> rejectAppointmentRequest(@RequestBody Appointment appointment) {
        appointmentService.rejectAppointmentRequest(appointment);
        return ResponseEntity.ok("Randevu reddedildi.");
    }

    // Tüm randevuları alma (SuperBarber)
    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Aktif randevuları alma (SuperBarber)
    @GetMapping("/active")
    public ResponseEntity<List<Appointment>> getActiveAppointments() {
        List<Appointment> appointments = appointmentService.getActiveAppointments();
        return ResponseEntity.ok(appointments);
    }


}

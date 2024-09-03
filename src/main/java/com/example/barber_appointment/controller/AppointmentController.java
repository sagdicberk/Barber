package com.example.barber_appointment.controller;

import com.example.barber_appointment.business.abstracts.AppointmentService;
import com.example.barber_appointment.business.abstracts.AuthenticationService;
import com.example.barber_appointment.business.abstracts.UserService;
import com.example.barber_appointment.model.Appointment;
import com.example.barber_appointment.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final UserService userService;

    private final AuthenticationService authenticationService;

    // Helper function
    // - Get User From token -/
    private User getUserFromToken(String token){
        String phoneNumber = authenticationService.getPhoneNumberFromToken(token);
        return userService.FindByPhoneNumber(phoneNumber);
    }


    // Randevu talebi gönderme
    @PostMapping("/request")
    public ResponseEntity<String> sendAppointmentRequest(@RequestBody Appointment appointment, @RequestParam String token) {
        User customer = getUserFromToken(token);
        appointmentService.sendAppointmentRequest(appointment, customer);
        return ResponseEntity.ok("Randevu talebi başarıyla gönderildi.");
    }

    // Kullanıcının tüm randevularını alma
    @GetMapping("/user")
    public ResponseEntity<List<Appointment>> getAppointments(@RequestParam String token) {
        User customer = getUserFromToken(token);
        List<Appointment> appointments = appointmentService.getAppointments(customer);
        return ResponseEntity.ok(appointments);
    }

    // Kullanıcının aktif randevularını alma
    @GetMapping("/user/active")
    public ResponseEntity<List<Appointment>> getActiveAppointments(@RequestParam String token) {
        User customer = getUserFromToken(token);
        List<Appointment> appointments = appointmentService.getActiveAppointments(customer);
        return ResponseEntity.ok(appointments);
    }

    // Berberin günlük randevularını alma
    @PreAuthorize("hasRole('BARBER')")
    @GetMapping("/barber/daily")
    public ResponseEntity<List<Appointment>> getMyDailyAppointments(@RequestParam String token) {
        User barber = getUserFromToken(token);
        List<Appointment> appointments = appointmentService.getMyDailyAppointments(barber);
        return ResponseEntity.ok(appointments);
    }

    // Berberin tüm randevularını alma
    @GetMapping("/barber/all")
    public ResponseEntity<List<Appointment>> getMyAllAppointments(@RequestParam String token) {
        User barber = getUserFromToken(token);
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
    @PreAuthorize("hasRole('SUPERBARBER')")
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

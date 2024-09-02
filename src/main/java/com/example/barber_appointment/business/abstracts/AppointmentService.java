package com.example.barber_appointment.business.abstracts;

import com.example.barber_appointment.model.Appointment;
import com.example.barber_appointment.model.User;

import java.util.List;

public interface AppointmentService {

    // These Functions for The Customer usage
    void sendAppointmentRequest(Appointment appointment, User customerUser);
    List<Appointment> getAppointments(User customerUser);
    List<Appointment> getActiveAppointments(User customerUser);

    // These Functions for the Barber usage
    void acceptAppointmentRequest(Appointment appointment);
    void rejectAppointmentRequest(Appointment appointment);
    List<Appointment> getMyDailyAppointments(User berberUser);
    List<Appointment> getMyAllAppointments(User berberUser);
    List<Appointment> getAllPendingAppointments(User berberUser);

    // These Functions for the SuperBarber usage
    List<Appointment> getAllAppointments();
    List<Appointment> getActiveAppointments();

}

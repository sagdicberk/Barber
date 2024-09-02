package com.example.barber_appointment.business.concretes;

import com.example.barber_appointment.business.abstracts.AppointmentService;
import com.example.barber_appointment.business.abstracts.SmsService;
import com.example.barber_appointment.model.Appointment;
import com.example.barber_appointment.model.AppointmentStatus;
import com.example.barber_appointment.model.User;
import com.example.barber_appointment.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentManager implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final SmsService smsService;

    // These Functions for The Customer usage
    @Override
    public void sendAppointmentRequest(Appointment appointment, User customerUser) {
        appointment.setCustomer(customerUser);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setDayOfAppointment(getToday());
        appointmentRepository.save(appointment);

        // we send sms notification to Barber and costumer.
        smsService.sendSms(customerUser.getPhoneNumber(), "Merhaba, sayın " + customerUser.getName() + " randevu isteiğiniz berberinize iletildi. En yakın vakitte geri dönüş yapılacaktır.");
        smsService.sendSms(appointment.getBarber().getPhoneNumber(), customerUser.getName()+ " isimli müşteri " + appointment.getAppointmentTime() + " için randevu talebinde bulundu. Müşterimizin numarası: " + customerUser.getPhoneNumber());
    }

    @Override
    public List<Appointment> getAppointments(User customerUser){

        return appointmentRepository.findByCustomer(customerUser);
    }

    @Override
    public List<Appointment> getActiveAppointments(User customerUser){

        return appointmentRepository.findByCustomerAndStatus(customerUser, AppointmentStatus.CONFIRMED);
    }

    // These Functions for the Barber usage
    @Override
    public void acceptAppointmentRequest(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);

        // sms info massage
        smsService.sendSms(appointment.getCustomer().getPhoneNumber(), appointment.getAppointmentTime() + " için randevunuz onaylanmıştır. Berberiniz: " + appointment.getBarber().getName() + " Telefon numarası: " + appointment.getBarber().getPhoneNumber());
        smsService.sendSms(appointment.getBarber().getPhoneNumber(), appointment.getAppointmentTime() + " için " + appointment.getCustomer().getName() + " isimli müşteri ile randevunuz onaylandı. Müşterinin telefon numarası: " + appointment.getCustomer().getPhoneNumber());
    }

    @Override
    public void rejectAppointmentRequest(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        // sms info message
        smsService.sendSms(appointment.getCustomer().getPhoneNumber(), appointment.getAppointmentTime() + " için randevunuz reddedilmiştir.");
        smsService.sendSms(appointment.getBarber().getPhoneNumber(), appointment.getAppointmentTime() + " için " + appointment.getCustomer().getName() + " isimli müşteri ile randevunuz reddedildi.");

    }

    @Override
    public List<Appointment> getMyDailyAppointments(User berberUser) {
        return appointmentRepository.findByBarberAndDayOfAppointmentAndStatus(berberUser, getToday(), AppointmentStatus.CONFIRMED);
    }

    @Override
    public List<Appointment> getMyAllAppointments(User berberUser) {

        return appointmentRepository.findByBarber(berberUser);
    }

    @Override
    public List<Appointment> getAllPendingAppointments(User berberUser) {
        return appointmentRepository.findByBarberAndStatus(berberUser, AppointmentStatus.PENDING);

    }

    // These Functions for the SuperBarber usage
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getActiveAppointments() {
        return appointmentRepository.findByStatus(AppointmentStatus.CONFIRMED);
    }

    // Helper Functions
    //
    // This function is getting current date
    private LocalDate getToday() {
        return LocalDate.now();
    }

    // This function is Update appointments Status. when the appointment time
    // pass 1 hour and status is confirmed, function change status to completed

    @Scheduled(cron = "0 0/30 * * * *")
    public void updateCompletedAppointments() {
        LocalDateTime now = LocalDateTime.now();
        appointmentRepository.updateStatusForCompletedAppointments(now, AppointmentStatus.COMPLETED);
    }

}

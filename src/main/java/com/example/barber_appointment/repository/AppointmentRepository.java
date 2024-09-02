package com.example.barber_appointment.repository;

import com.example.barber_appointment.model.Appointment;
import com.example.barber_appointment.model.AppointmentStatus;
import com.example.barber_appointment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByBarberAndDayOfAppointmentAndStatus(User barber, LocalDate dayOfAppointment, AppointmentStatus status);

    List<Appointment> findByBarber(User barberUser);

    List<Appointment> findByBarberAndStatus(User barber, AppointmentStatus status);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByCustomer(User customer);

    List<Appointment> findByCustomerAndStatus(User customer, AppointmentStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE Appointment a SET a.status = :status WHERE a.status = 'CONFIRMED' AND a.appointmentTime <= :now")
    void updateStatusForCompletedAppointments(LocalDateTime now, AppointmentStatus status);
}

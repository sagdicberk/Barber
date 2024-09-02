package com.example.barber_appointment.model;

public enum AppointmentStatus {
    PENDING, // Randevu talebi yapıldı, onay bekleniyor
    CONFIRMED, // Randevu onaylandı
    CANCELLED, // Randevu iptal edildi
    COMPLETED // Randevu tamamlandı
}

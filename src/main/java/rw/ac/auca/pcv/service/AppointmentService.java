package rw.ac.auca.pcv.service;

import rw.ac.auca.pcv.domain.Appointment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment saveAppointment(Appointment appointment);
    Optional<Appointment> findAppointmentById(Long id);
    List<Appointment> findAllAppointments();
    Appointment updateAppointment(Appointment appointment);
    void deleteAppointmentById(Long id);
    Appointment postponeAppointment(Long appointmentId, Date newDate);
    Appointment cancelAppointment(Long appointmentId);
}

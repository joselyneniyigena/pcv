package rw.ac.auca.pcv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.pcv.dao.AppointmentDao;
import rw.ac.auca.pcv.domain.Appointment;
import rw.ac.auca.pcv.domain.AppointmentStatus;
import rw.ac.auca.pcv.domain.DoctorSchedule;
import rw.ac.auca.pcv.service.AppointmentService;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDao appointmentDao;

    @Autowired
    public AppointmentServiceImpl(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        DoctorSchedule schedule = appointment.getDoctorSchedule();
        Date appointmentDate = appointment.getAppointmentDate();
        LocalDate localDate = appointmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Check if the day of week matches
        if (!schedule.getDayOfWeek().equals(localDate.getDayOfWeek())) {
            throw new RuntimeException("Doctor is not available on this day of the week.");
        }

        LocalTime appointmentTime = appointmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        Time scheduleTimeFrom = schedule.getTimeFrom();
        Time scheduleTimeTo = schedule.getTimeTo();

        // Check if the appointment time falls within the doctor's schedule
        if (appointmentTime.isBefore(scheduleTimeFrom.toLocalTime()) || appointmentTime.isAfter(scheduleTimeTo.toLocalTime())) {
            throw new RuntimeException("Appointment time is outside the doctor's schedule.");
        }

        // Check for overlapping appointments
        if (schedule.getAppointmentList().stream().anyMatch(
                existingAppointment -> existingAppointment.getAppointmentDate().equals(appointmentDate))) {
            throw new RuntimeException("Doctor already has an appointment at this time.");
        }

        return appointmentDao.save(appointment);
    }

    @Override
    public Optional<Appointment> findAppointmentById(Long id) {
        return Optional.ofNullable(appointmentDao.findOne(id));
    }

    @Override
    public List<Appointment> findAllAppointments() {
        return appointmentDao.findAll();
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentDao.save(appointment);
    }

    @Override
    public void deleteAppointmentById(Long id) {
        appointmentDao.delete(id);
    }

    @Override
    public Appointment postponeAppointment(Long appointmentId, Date newDate) {
        Optional<Appointment> appointmentOptional = Optional.ofNullable(appointmentDao.findOne(appointmentId));
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            DoctorSchedule schedule = appointment.getDoctorSchedule();
            LocalDate newLocalDate = newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Similar checks as in saveAppointment
            if (!schedule.getDayOfWeek().equals(newLocalDate.getDayOfWeek())) {
                throw new RuntimeException("Doctor is not available on this new day of the week.");
            }

            LocalTime newAppointmentTime = newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            if (newAppointmentTime.isBefore(schedule.getTimeFrom().toLocalTime()) || newAppointmentTime.isAfter(schedule.getTimeTo().toLocalTime())) {
                throw new RuntimeException("New appointment time is outside the doctor's schedule.");
            }

            if (schedule.getAppointmentList().stream().anyMatch(
                    existingAppointment -> existingAppointment.getAppointmentDate().equals(newDate) &&
                            !existingAppointment.getId().equals(appointmentId))) {
                throw new RuntimeException("Doctor already has an appointment at this new time.");
            }

            appointment.setAppointmentDate(newDate);
            return appointmentDao.save(appointment);
        }
        throw new RuntimeException("Appointment not found");
    }


    @Override
    public Appointment cancelAppointment(Long appointmentId) {
        Optional<Appointment> appointmentOptional = Optional.ofNullable(appointmentDao.findOne(appointmentId));
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
            return appointmentDao.save(appointment);
        }
        throw new RuntimeException("Appointment not found");
    }
}

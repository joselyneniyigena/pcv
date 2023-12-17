package rw.ac.auca.pcv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.pcv.domain.Appointment;
import rw.ac.auca.pcv.domain.AppointmentStatus;
import rw.ac.auca.pcv.domain.Patient;
import rw.ac.auca.pcv.dto.AppointmentDTO;
import rw.ac.auca.pcv.service.AppointmentService;
import rw.ac.auca.pcv.service.DoctorScheduleService;
import rw.ac.auca.pcv.service.PatientService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/appointments")
@SessionAttributes(value = {"loggedUser", "userRoles"})
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorScheduleService doctorScheduleService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.findAllAppointments());
        return "appointments";
    }

    @GetMapping("/create")
    public String showAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("appointmentDTO", new AppointmentDTO());
        model.addAttribute("schedules", doctorScheduleService.findAllDoctorSchedules());
        return "createAppointment";
    }

    @PostMapping("/create")
    public String scheduleAppointment(@ModelAttribute AppointmentDTO appointmentDTO, Model model) {
        try {
            Appointment appointment = new Appointment();

            appointment.setPatient(patientService.findPatientById(appointmentDTO.getPatientId()).orElse(null));
            appointment.setDoctorSchedule(doctorScheduleService.findDoctorScheduleById(appointmentDTO.getDoctorScheduleId()).orElse(null));
            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appointment.setDescription(appointmentDTO.getDescription());
            appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
            appointmentService.saveAppointment(appointment);
            model.addAttribute("appointmentError", Boolean.FALSE);
        } catch (Exception e) {
            model.addAttribute("appointmentError", Boolean.TRUE);
            model.addAttribute("appointmentErrorMessage", e.getMessage());
            return "createAppointment";
        }
        return "redirect:/appointments";
    }


    @GetMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return "redirect:/appointments";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.findAppointmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment Id:" + id));
        model.addAttribute("appointment", appointment);
        model.addAttribute("schedules", doctorScheduleService.findAllDoctorSchedules());
        return "createAppointment";
    }

    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable Long id, @ModelAttribute Appointment appointment, Model model) {
        appointment.setId(id);
        appointmentService.updateAppointment(appointment);
        return "redirect:/appointments";
    }

    @PostMapping("/search-patient")
    public String searchPatient(@RequestParam(required = false) String patientSearchFirstName,
                                @RequestParam(required = false) String patientSearchLastName,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date patientSearchDOB,
                                Model model) {
        List<Patient> searchResults = patientService.searchPatients(patientSearchFirstName, patientSearchLastName, patientSearchDOB);
        model.addAttribute("searchResults", searchResults != null ? searchResults : Collections.emptyList());
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("appointmentDTO", new AppointmentDTO());
        model.addAttribute("schedules", doctorScheduleService.findAllDoctorSchedules());
        return "createAppointment";
    }
}

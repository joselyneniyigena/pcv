package rw.ac.auca.pcv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.pcv.domain.Doctor;
import rw.ac.auca.pcv.domain.DoctorSchedule;
import rw.ac.auca.pcv.dto.DoctorScheduleDTO;
import rw.ac.auca.pcv.service.DoctorScheduleService;
import rw.ac.auca.pcv.service.DoctorService;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor-schedules")
@SessionAttributes(value = {"loggedUser", "userRoles"})
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService doctorScheduleService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("doctors", doctorService.findAllDoctors());
        model.addAttribute("doctorSchedule", new DoctorSchedule());
        model.addAttribute("doctorScheduleDTO", new DoctorScheduleDTO());
        List<DoctorSchedule> schedules = doctorScheduleService.findAllDoctorSchedules();
        schedules.forEach(schedule -> {
            schedule.setStatus(calculateStatus(schedule)); // Assuming you have a setStatus method
        });
        model.addAttribute("doctorSchedules", schedules);
        return "createDoctorSchedule";
    }

    @PostMapping("/create")
    public String createDoctorSchedule(@ModelAttribute DoctorScheduleDTO doctorScheduleDTO, Model model) {
        Optional<Doctor> doctorOptional = doctorService.findDoctorById(doctorScheduleDTO.getDoctorId());
        if (doctorOptional.isPresent()) {
            DoctorSchedule doctorSchedule = new DoctorSchedule();
            doctorSchedule.setDoctor(doctorOptional.get());
            doctorSchedule.setDayOfWeek(doctorScheduleDTO.getDayOfWeek());
            doctorSchedule.setTimeFrom(Time.valueOf(LocalTime.parse(doctorScheduleDTO.getTimeFrom())));
            doctorSchedule.setTimeTo(Time.valueOf(LocalTime.parse(doctorScheduleDTO.getTimeTo())));
            if (doctorSchedule.getTimeFrom() != null && doctorSchedule.getTimeTo() != null) {
                if (!doctorSchedule.getTimeFrom().before(doctorSchedule.getTimeTo())) {
                    model.addAttribute("regError", true);
                    model.addAttribute("regMsg", "Time From should be earlier than Time To");
                }
            }
            doctorScheduleService.saveDoctorSchedule(doctorSchedule);
        } else {
            model.addAttribute("regError", true);
            model.addAttribute("regMsg", "Doctor with ID " + doctorScheduleDTO.getDoctorId() + " not found");
        }
        return "createDoctorSchedule";
    }

    private String calculateStatus(DoctorSchedule schedule) {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        LocalTime now = LocalTime.now();

        if (schedule.getDayOfWeek().equals(today) && schedule.getTimeTo().toLocalTime().isAfter(now)) {
            return "Available";
        } else {
            return "Past Due";
        }
    }

}

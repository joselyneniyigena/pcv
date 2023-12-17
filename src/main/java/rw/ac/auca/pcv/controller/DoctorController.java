package rw.ac.auca.pcv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import rw.ac.auca.pcv.domain.Doctor;
import rw.ac.auca.pcv.service.DoctorService;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes(value = { "loggedUser", "userRoles" })
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctor/create")
    public String createDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "createDoctor";
    }

    @PostMapping("/doctor/create")
    public String createDoctor(@Valid Doctor doctor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("regstatus", false);
            model.addAttribute("regError", true);
            model.addAttribute("regMsg", "Error creating doctor: " + result.getAllErrors().toString());
            return "createDoctor";
        }

        try {
            doctorService.saveDoctor(doctor);
            model.addAttribute("regstatus", true);
        } catch (Exception e) {
            model.addAttribute("regError", true);
            model.addAttribute("regMsg", "Error creating doctor: " + e.getMessage());
        }

        return "redirect:/doctors";
    }

    @GetMapping("/doctors")
    public String viewAllDoctors(Model model) {
        List<Doctor> doctors = doctorService.findAllDoctors();
        model.addAttribute("doctors", doctors);
        return "doctors";
    }

}

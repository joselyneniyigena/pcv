package rw.ac.auca.pcv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.pcv.domain.Patient;
import rw.ac.auca.pcv.service.PatientService;

@Controller
@RequestMapping("/patients")
@SessionAttributes(value= {"loggedUser","userRoles"})
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.findAllPatients());
        return "patients";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "createPatient";
    }

    @PostMapping("/create")
    public String createPatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients";
    }

}

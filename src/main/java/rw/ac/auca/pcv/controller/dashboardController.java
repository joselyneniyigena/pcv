package rw.ac.auca.pcv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import rw.ac.auca.pcv.service.*;

@Controller
@SessionAttributes(value= {"loggedUser","userRoles"})
public class dashboardController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private UserService userService;
	
	
	
	@RequestMapping("dashboard")
	public String taskPage(Model model) {
		
		model.addAttribute("userList", userService.findByEnabled(true));
		model.addAttribute("doctorList", doctorService.findAllDoctors());
		model.addAttribute("PatientList", patientService.findAllPatients());
		model.addAttribute("patientSize", patientService.findAllPatients().size());

		return "dashboard";
	}
}

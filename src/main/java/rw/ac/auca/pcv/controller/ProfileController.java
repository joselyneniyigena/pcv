/**
 * 
 */
package rw.ac.auca.pcv.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import rw.ac.auca.pcv.dao.RoleDao;
import rw.ac.auca.pcv.domain.User;
import rw.ac.auca.pcv.service.UserService;


@Controller
@SessionAttributes(value= {"loggedUser","userRoles"})
public class ProfileController {

	@Autowired
	RoleDao roleDao;

	@Autowired
	UserService userService;

	@RequestMapping("/profile")
	public String userFront(Principal principal, Model model, Authentication auth) {
		try {
			User user = userService.findByUsername(principal.getName());
			model.addAttribute("user", user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "profile";
	}

	@RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
	public String update(Principal principal, @ModelAttribute("user") @Valid User user, BindingResult bindingResult,
			Model model)
			throws Exception {
		String msg = "Profile updated successfully!!";
		String parameters = "saved=true";
		try {
			if (bindingResult.hasErrors()) {
				return "profile";
			} else {
				userService.updateProfile(user);
				model.addAttribute("msg", msg);
				
			}
		} catch (Exception e) {
			
		}

		return "forward:/profile?" + parameters;
	}

}

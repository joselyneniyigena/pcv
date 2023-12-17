package rw.ac.auca.pcv.controller;

import org.springframework.stereotype.*;
import rw.ac.auca.pcv.domain.User;
import rw.ac.auca.pcv.domain.security.Role;
import rw.ac.auca.pcv.domain.security.UserRole;
import rw.ac.auca.pcv.service.RoleService;
import rw.ac.auca.pcv.service.UserService;
import rw.ac.auca.pcv.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.*;
import rw.ac.auca.pcv.domain.*;
import java.security.*;

import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import org.springframework.validation.*;
import rw.ac.auca.pcv.domain.security.*;
import java.util.*;

@Controller
@SessionAttributes(value= {"loggedUser","userRoles"})
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}

	@RequestMapping("index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		try {
			User user = new User();
			model.addAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "signup";
	}

	@RequestMapping(value = "/doctor-signup", method = RequestMethod.GET)
	public String doctorSignup(Model model) {
		try {
			User user = new User();
			model.addAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "doctor-signup";
	}


	@RequestMapping("/users")
	public String users(Model model) {
		try {
			model.addAttribute("userList", userService.findAll());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "users";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model)
			throws Exception {
		try {
			if (bindingResult.hasErrors()) {

				return "signup";

			} else if (userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists", true);
				return "signup";
			} else if (userService.checkUsernameExists(user.getUsername())) {

				model.addAttribute("usernameExist", true);

				return "signup";
			} else {
				// define user' role
				String roleName = "";
				if (userService.countUsers() == 0) {
					roleName = "ROLE_ADMIN";
					user.setEnabled(true);
				} else {
					if(user.getDocCode().isEmpty()) {
						roleName = "ROLE_USER";
						user.setEnabled(false);
					}else{
						roleName = "ROLE_DOCTOR";
						user.setEnabled(true);
					}
				}
				// create role if not exists
				Role role = new Role();
				if (null == roleService.findByName(roleName)) {
					role.setName(roleName);
					role = roleService.createRole(role);
				}else {
					role = roleService.findByName(roleName);
				}
				// save user
				Set<UserRole> userRoles = new HashSet<>();
				userRoles.add(new UserRole(user, role));
				user.setRegdate(new Date());
				userService.createUser(user, userRoles);
				return "redirect:/index";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/index";
		}
	}

	// enabling and disabling user
	@RequestMapping(value = "/user/enable", method = RequestMethod.GET)
	public String disableOrEnableUser(Principal principal, Model model, @RequestParam("userId") long userId,
			@RequestParam("enabled") boolean enabled) throws Exception {
		try {
			if (userService.findByUsername(principal.getName()).getId() != userId) {
				userService.isEnabled(userId, enabled);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/users";
	}
	
	public String addRoleToUser() {
		return "";
	}
}
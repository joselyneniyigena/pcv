
package rw.ac.auca.pcv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rw.ac.auca.pcv.service.UserService;

@Controller
public class ForgotController {
	
	@Autowired
	UserService userService;
	
    @RequestMapping("/forgot")
    public String forgot() {
    	return "forgot";
    }
    

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public String forgotPassword(@ModelAttribute("email") String email, Model model) throws Exception{
    	if(userService.checkEmailExists(email)) {
    		userService.resetPassword(email);
    		model.addAttribute("accountExists", true);
    		
    	}else {
    		model.addAttribute("accountNotExists", true);
    	}
    	return "forgot";
    }
    
   
    
}

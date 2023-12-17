/**
 * 
 */
package rw.ac.auca.pcv.util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import rw.ac.auca.pcv.domain.User;
import rw.ac.auca.pcv.service.UserService;


@Component
@SessionAttributes(value= {"loggedUser","userRoles"})
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException
			{
		
		User user = userService.findByUsername(authentication.getName());
		request.getSession().setAttribute("loggedUser", user);
		

		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			roles.add(auth.getAuthority());
        }
		request.getSession().setAttribute("userRoles", roles);
		
		redirectStrategy.sendRedirect(request, response, "/dashboard");
 
}
}
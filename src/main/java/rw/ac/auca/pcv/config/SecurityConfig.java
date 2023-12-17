package rw.ac.auca.pcv.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import rw.ac.auca.pcv.service.UserSecurityService;
import rw.ac.auca.pcv.util.MyAuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    private static final String SALT = "salt";


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/plugins/**",
            "/about/**",
            "/contact/**",
            "/error/**",
            "/console/**",
            "/api/**",
            "/signup",
            "/forgot",
            "/doctor-signup"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().
                antMatchers(PUBLIC_MATCHERS).
                permitAll().
                antMatchers("/users/**").hasAnyRole("ADMIN").
                antMatchers("/project/**").hasAnyRole("ADMIN").
                antMatchers("/createTask/**").hasAnyRole("ADMIN").
                antMatchers("/createProject/**").hasAnyRole("ADMIN").
                anyRequest().authenticated();

        http
                .csrf().disable().cors().disable()
//		.formLogin().failureUrl("/index?errors").defaultSuccessUrl("/dashboard").loginPage("/index").permitAll()
                .formLogin().successHandler(successHandler).failureUrl("/index?errors").loginPage("/index").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();

    }

    public void configureGrobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }

}

package rw.ac.auca.pcv.service.impl;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.pcv.config.SecurityUtility;
import rw.ac.auca.pcv.dao.RoleDao;
import rw.ac.auca.pcv.dao.UserDao;
import rw.ac.auca.pcv.dao.UserRoleDao;
import rw.ac.auca.pcv.domain.Doctor;
import rw.ac.auca.pcv.domain.User;
import rw.ac.auca.pcv.domain.security.Role;
import rw.ac.auca.pcv.domain.security.UserRole;
import rw.ac.auca.pcv.service.DoctorService;
import rw.ac.auca.pcv.service.UserService;
import rw.ac.auca.pcv.util.EmailService;
import rw.ac.auca.pcv.util.Mail;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	private DoctorService doctorService;

	public UserServiceImpl() {
	}

	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void isEnabled(long userId, boolean enabled) throws Exception {
		User user = userDao.findById(userId);
		if (user != null) {
			user.setEnabled(enabled);
			userDao.save(user);
		} else {
			throw new Exception("User not exists");
		}
	}

	@Transactional
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {

		User localUser = userDao.findByUsername(user.getUsername());

		if (localUser != null) {

			LOG.info("Username {} already exists. Please try another one. " + user.getUsername());

		} else {
			boolean isADoctor = Boolean.FALSE;
			Optional<Doctor> doctorOptional = Optional.ofNullable(doctorService.findDoctorByCode(user.getDocCode()));
			if(user.getDocCode()!=null && user.getDocCode().isEmpty()){
				if(!doctorOptional.isPresent()){
					throw new Exception("Code Provide Not Found");
				}
				isADoctor = Boolean.TRUE;
			}
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);

			for (UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);
			localUser = userDao.save(user);
			if(isADoctor){
				Doctor doctor = doctorOptional.get();
				doctor.setUserAccount(localUser);
				doctorService.updateDoctor(doctor);
			}
		}
		return localUser;
	}

	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public boolean checkUserExists(String username, String email) {
		if ((checkUsernameExists(username)) || (checkEmailExists(email))) {
			return true;
		}
		return false;
	}

	public boolean checkUsernameExists(String username) {
		if (findByUsername(username) != null) {
			return true;
		}
		return false;
	}

	public boolean checkEmailExists(String email) {
		if (findByEmail(email) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> userRoles(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}
		return roles;
	}

	@Override
	public boolean checkUserContainsRole(User user, String roleName) {
		Role role = roleDao.findByName(roleName);
		if (!userRoleDao.findByUserAndRole(user, role)) {
			return false;
		}
		return true;
	}

	@Transactional
	public User updateProfile(User user) throws Exception {
		User localUser = userDao.findByUsername(user.getUsername());
		if (localUser == null) {
			LOG.info("Username {} not found. " + user.getUsername());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
			localUser = userDao.save(user);
		}

		return localUser;
	}

	public List<User> findAll() {
		return (List<User>) userDao.findAll();
	}

	@Override
	public List<User> findByEnabled(boolean b) {

		return userDao.findByEnabled(b);
	}

	@Override
	public User findbyId(long id) throws Exception {
		return userDao.findById(id);
	}

	@Override
	public void resetPassword(String email) throws Exception {
		User user = userDao.findByEmail(email);

		if (user != null) {
			String password = SecurityUtility.randomPassword();
			String encryptedPassword = passwordEncoder.encode(password);
			user.setPassword(encryptedPassword);
			userDao.save(user);
			Mail mail = new Mail();
			mail.setTo(user.getEmail());
			mail.setSubject("Reseting PCV Password");
			mail.setContent("Dear " + user.getFname() + ","
					+ "Somebody (hopefully you) requested to reset your password for Task Management System(PCV) account,"
					+ "\nPlease use the following credentials to log in and edit your password.\n" + "Username:"
					+ user.getUsername() + "\nPassword:" + password + "\n\nBest Regards!\nPCV Care Team");
			emailService.sendSimpleMessage(mail);

		} else {
			throw new Exception("Account not exists");
		}
	}

	@Override
	public long countUsers() {
		return userDao.count();
	}

}
 package rw.ac.auca.pcv.dao;

import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;
import rw.ac.auca.pcv.domain.User;
import rw.ac.auca.pcv.domain.*;

import java.util.List;

import org.springframework.data.jpa.repository.*;

 @Repository
public interface UserDao extends JpaRepository<User, Long> {
	User findByUsername(String username);

	User findByEmail(String email);

	User findById(long id);

	@Query("SELECT u from User u WHERE u.id=?1")
	User findOne(long id);
	
	List<User> findByEnabled(boolean enabled);
}
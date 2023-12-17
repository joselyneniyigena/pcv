package rw.ac.auca.pcv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.*;

import org.springframework.stereotype.Repository;
import rw.ac.auca.pcv.domain.User;
import rw.ac.auca.pcv.domain.security.Role;
import rw.ac.auca.pcv.domain.security.UserRole;
import rw.ac.auca.pcv.domain.security.*;

@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Long>
{
    Iterable<UserRole> findByRole(Role userRole);
    boolean findByUserAndRole(User user, Role role);
}